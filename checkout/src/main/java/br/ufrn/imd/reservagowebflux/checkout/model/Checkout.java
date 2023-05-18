package br.ufrn.imd.reservagowebflux.checkout.model;


import br.ufrn.imd.reservagowebflux.base.model.GenericModel;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "checkout")
public class Checkout extends GenericModel<String> {
    @Id
    private String id;
    private LocalDateTime checkoutDate;

    private String userId;

    private String placeId;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDateTime expirationDate) {
        this.checkoutDate = expirationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
