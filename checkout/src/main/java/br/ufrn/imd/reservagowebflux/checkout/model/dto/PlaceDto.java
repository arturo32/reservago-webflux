package br.ufrn.imd.reservagowebflux.checkout.model.dto;


public record PlaceDto(Long id, boolean available, double stars, double valuePerDay,
					   String name, String location, String description, Integer daysAvailable,
					   Long hostId, Integer maxNumberOfGuests) {
}
