package br.ufrn.imd.reservagowebflux.checkout.model.dto;


public record CheckoutDto(Integer maxNumberOfGuests, Integer currentNumberOfGuests) {

    public boolean isFull() {
        return maxNumberOfGuests.equals(currentNumberOfGuests);
    }
}
