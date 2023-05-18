package br.ufrn.imd.reservagowebflux.admin.controller;

import br.ufrn.imd.reservagowebflux.admin.model.User;
import br.ufrn.imd.reservagowebflux.admin.model.dto.UserDto;
import br.ufrn.imd.reservagowebflux.admin.service.GenericService;
import br.ufrn.imd.reservagowebflux.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends GenericController<User, UserDto, String>{

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected GenericService<User, UserDto, String> service() {
		return this.userService;
	}
}
