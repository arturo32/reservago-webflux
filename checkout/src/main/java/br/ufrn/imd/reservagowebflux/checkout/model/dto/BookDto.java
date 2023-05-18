package br.ufrn.imd.reservagowebflux.checkout.model.dto;

import java.time.LocalDateTime;

public record BookDto(PaymentDto paymentDto, LocalDateTime checkoutDate) {}
