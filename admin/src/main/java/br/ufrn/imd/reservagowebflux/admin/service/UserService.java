package br.ufrn.imd.reservagowebflux.admin.service;

import br.ufrn.imd.reservagowebflux.admin.model.User;
import br.ufrn.imd.reservagowebflux.admin.model.dto.UserDto;
import br.ufrn.imd.reservagowebflux.admin.repository.UserRepository;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserService extends GenericService<User, UserDto, String> {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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
