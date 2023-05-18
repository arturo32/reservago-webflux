package br.ufrn.imd.reservagowebflux.payment.model;

import br.ufrn.imd.reservagowebflux.base.model.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payment")
public class Payment extends GenericModel<String> {

    @Id
    private String id;

    private CreditCard creditCard;

    public Payment() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
