package br.ufrn.imd.reservagowebflux.payment.model;

import br.ufrn.imd.reservagowebflux.base.model.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction")
public class Transaction extends GenericModel<String> {

	@Id
	private String id;
	private boolean transactionOk;
	private String placeId;
	private String userId;


	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public boolean isTransactionOk() {
		return transactionOk;
	}

	public void setTransactionOk(boolean transactionOk) {
		this.transactionOk = transactionOk;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
