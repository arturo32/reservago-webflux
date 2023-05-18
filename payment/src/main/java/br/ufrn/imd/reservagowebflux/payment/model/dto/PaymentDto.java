package br.ufrn.imd.reservagowebflux.payment.model.dto;


import br.ufrn.imd.reservagowebflux.payment.model.Payment;

public record PaymentDto(String id, CreditCardDto creditCard) {
    public PaymentDto(Payment payment) {
        this(payment.getId(), new CreditCardDto(payment.getCreditCard()));
    }

}
