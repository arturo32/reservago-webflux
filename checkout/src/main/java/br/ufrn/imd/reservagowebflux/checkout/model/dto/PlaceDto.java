package br.ufrn.imd.reservagowebflux.checkout.model.dto;


public record PlaceDto(String id, boolean available, double stars, double valuePerDay,
					   String name, String location, String description, Integer daysAvailable,
					   String hostId, Integer maxNumberOfGuests) {
}
