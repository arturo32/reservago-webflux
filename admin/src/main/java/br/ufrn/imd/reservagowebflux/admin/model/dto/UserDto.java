package br.ufrn.imd.reservagowebflux.admin.model.dto;

import br.ufrn.imd.reservagomvc.admin.model.User;

public record UserDto(Long id, String name, Integer type) {
	public UserDto(User user) {
		this(user.getId(), user.getName(), user.getType());
	}
}
