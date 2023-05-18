package br.ufrn.imd.reservagowebflux.checkout.model.dto;

public record TransactionDto(String id, Boolean transactionOk, String placeId, String userId) {}
