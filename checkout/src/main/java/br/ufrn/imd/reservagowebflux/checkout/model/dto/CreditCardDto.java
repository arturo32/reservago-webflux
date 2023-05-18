package br.ufrn.imd.reservagowebflux.checkout.model.dto;


public record CreditCardDto(Long id, Long ownerId, String number, Double balance, Integer verificationNumber) {
}
