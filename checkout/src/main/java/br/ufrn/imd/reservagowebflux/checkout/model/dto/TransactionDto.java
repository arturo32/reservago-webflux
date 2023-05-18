package br.ufrn.imd.reservagowebflux.checkout.model.dto;

public record TransactionDto(Long id, Boolean transactionOk, Long placeId, Long userId) {}
