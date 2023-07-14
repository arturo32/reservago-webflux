package com.br.ufrn.imd.reservagowebflux.processusercreation.services;


import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import com.br.ufrn.imd.reservagowebflux.processusercreation.models.User;
import com.br.ufrn.imd.reservagowebflux.processusercreation.repositories.UserRepository;
import com.br.ufrn.imd.reservagowebflux.processusercreation.models.dto.UserDto;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserService extends GenericService<User, UserDto, String> {

	private final UserRepository userRepository;

	private RLocalCachedMapReactive<String, User> userMap;

	@Autowired
	public UserService(UserRepository userRepository, RedissonReactiveClient cacheser) {
		this.userRepository = userRepository;
		LocalCachedMapOptions<String, User> mapOptions = LocalCachedMapOptions.<String, User>defaults()
				.syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.userMap = cacheser.getLocalCachedMap("/user/", new TypedJsonJacksonCodec(String.class, User.class), mapOptions);
	}

	@Override
	protected ReactiveMongoRepository<User, String> repository() {
		return this.userRepository;
	}

	@Override
	public UserDto convertToDto(User user) {
		return new UserDto(user);
	}


	@Override
	public Mono<User> convertToEntity(Mono<UserDto> userDto) {
		return userDto
				.map(dto -> {
					User user = new User();
					user.setId(dto.id());
					user.setName(dto.name());
					user.setType(dto.type());
					return user;
				})
				.subscribeOn(Schedulers.boundedElastic());
	}
}
