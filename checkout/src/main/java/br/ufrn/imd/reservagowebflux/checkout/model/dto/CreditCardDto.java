package br.ufrn.imd.reservagowebflux.checkout.model.dto;


public record CreditCardDto(String id, String ownerId, String number, Double balance, Integer verificationNumber) {
}
