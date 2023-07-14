package com.br.ufrn.imd.reservagowebflux.processusercreation.models.dto;


import com.br.ufrn.imd.reservagowebflux.processusercreation.models.User;

public record UserDto(String id, String name, Integer type) {
	public UserDto(User user) {
		this(user.getId(), user.getName(), user.getType());
	}
}
