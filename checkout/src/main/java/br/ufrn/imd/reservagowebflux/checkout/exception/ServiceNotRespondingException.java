package br.ufrn.imd.reservagowebflux.checkout.exception;

public class ServiceNotRespondingException extends RuntimeException {
	private static final long serialVersionUID = -1308366825991170435L;

	public ServiceNotRespondingException() {
	}

	public ServiceNotRespondingException(String message) {
		super(message);
	}
}
