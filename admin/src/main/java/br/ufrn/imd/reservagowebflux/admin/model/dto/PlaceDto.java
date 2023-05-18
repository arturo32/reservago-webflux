package br.ufrn.imd.reservagowebflux.admin.model.dto;


import br.ufrn.imd.reservagowebflux.admin.model.Place;

public record PlaceDto(String id, boolean available, double stars, double valuePerDay,
					   String name, String location, String description, Integer daysAvailable,
					   String hostId, Integer maxNumberOfGuests) {
	public PlaceDto(Place place) {
		this(place.getId(), place.isAvailable(), place.getStars(), place.getValuePerDay(), place.getName(),
				place.getLocation(), place.getDescription(), place.getDaysAvailable(),
				place.getHost().getId(), place.getMaxNumberOfGuests());
	}
}
