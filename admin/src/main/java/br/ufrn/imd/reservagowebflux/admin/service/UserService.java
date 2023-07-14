package br.ufrn.imd.reservagowebflux.admin.service;

import br.ufrn.imd.reservagowebflux.admin.model.User;
import br.ufrn.imd.reservagowebflux.admin.model.dto.UserDto;
import br.ufrn.imd.reservagowebflux.admin.repository.UserRepository;
import br.ufrn.imd.reservagowebflux.base.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LocalCachedMapOptions.ReconnectionStrategy;
import org.redisson.api.LocalCachedMapOptions.SyncStrategy;
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
				.syncStrategy(SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.userMap = cacheser.getLocalCachedMap("/user/", new TypedJsonJacksonCodec(String.class, User.class), mapOptions);
	}


	@Override
	public Mono<User> findById(String id) {
		return this.userMap.get(id)
				.switchIfEmpty(
						this.repository().findById(id)
								.switchIfEmpty(Mono.error(new EntityNotFoundException("Entity of id " + String.valueOf(id) + " not found.")))
								.subscribeOn(Schedulers.boundedElastic())
								.flatMap(c -> this.userMap.fastPut(id, c).thenReturn(c))
				);
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
