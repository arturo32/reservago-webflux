package br.ufrn.imd.reservagowebflux.base.exception;

public class EntityNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -1308366825991170415L;

	public EntityNotFoundException() {
	}

	public EntityNotFoundException(String message) {
		super(message);
	}
}
