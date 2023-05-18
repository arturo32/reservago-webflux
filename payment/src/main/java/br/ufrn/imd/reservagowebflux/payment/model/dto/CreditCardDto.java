package br.ufrn.imd.reservagowebflux.payment.model.dto;

import br.ufrn.imd.reservagowebflux.payment.model.CreditCard;

public record CreditCardDto(String id, String ownerId, String number, Double balance, Integer verificationNumber) {
    public CreditCardDto(CreditCard creditCard) {
        this(creditCard.getId(), creditCard.getOwnerId(), creditCard.getNumber(), creditCard.getBalance(), creditCard.getVerificationNumber());
    }
}
