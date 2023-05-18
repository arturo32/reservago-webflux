package br.ufrn.imd.reservagowebflux.payment.model.dto;


import br.ufrn.imd.reservagowebflux.payment.model.Transaction;

public record TransactionDto(String id, Boolean transactionOk, String placeId, String userId) {
	public TransactionDto(Transaction transaction) {
		this(transaction.getId(), transaction.isTransactionOk(), transaction.getPlaceId(), transaction.getUserId());
	}
}
