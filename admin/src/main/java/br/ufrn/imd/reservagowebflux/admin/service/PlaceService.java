package br.ufrn.imd.reservagowebflux.admin.service;

import br.ufrn.imd.reservagowebflux.admin.model.Place;
import br.ufrn.imd.reservagowebflux.admin.model.User;
import br.ufrn.imd.reservagowebflux.admin.model.dto.PlaceDto;
import br.ufrn.imd.reservagowebflux.admin.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PlaceService extends GenericService<Place, PlaceDto, String> {
	private final PlaceRepository placeRepository;
	private final UserService userService;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, UserService userService) {
		this.placeRepository = placeRepository;
		this.userService = userService;
	}
	@Override
	protected ReactiveMongoRepository<Place, String> repository() {
		return this.placeRepository;
	}

	@Override
	public PlaceDto convertToDto(Place entity) {
		return new PlaceDto(entity);
	}

	@Override
	public Mono<Place> convertToEntity(Mono<PlaceDto> placeDto) {
		return placeDto
				.flatMap(p -> this.userService.findById(p.hostId()))
				.zipWith(placeDto, (user, dto) -> {
					Place place = new Place();
					place.setHost(user);
					place.setId(dto.id());
					place.setAvailable(dto.available());
					place.setStars(dto.stars());
					place.setValuePerDay(dto.valuePerDay());
					place.setName(dto.name());
					place.setLocation(dto.location());
					place.setDescription(dto.description());
					place.setDaysAvailable(dto.daysAvailable());
					place.setMaxNumberOfGuests(dto.maxNumberOfGuests());
					return place;
				});
	}



}
