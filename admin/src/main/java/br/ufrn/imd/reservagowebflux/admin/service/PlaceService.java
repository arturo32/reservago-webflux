package br.ufrn.imd.reservagowebflux.admin.service;

import br.ufrn.imd.reservagowebflux.admin.model.Place;
import br.ufrn.imd.reservagowebflux.admin.model.dto.PlaceDto;
import br.ufrn.imd.reservagowebflux.admin.repository.PlaceRepository;
import br.ufrn.imd.reservagowebflux.base.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PlaceService extends GenericService<Place, PlaceDto, String> {
	private final PlaceRepository placeRepository;
	private final UserService userService;

	private RMapReactive<String, Place> placeMap;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, UserService userService, RedissonReactiveClient cacheser) {
		this.placeRepository = placeRepository;
		this.userService = userService;
		this.placeMap = cacheser.getMap("/place/", new TypedJsonJacksonCodec(String.class, Place.class));
	}
	@Override
	protected ReactiveMongoRepository<Place, String> repository() {
		return this.placeRepository;
	}

	@Override
	public PlaceDto convertToDto(Place place) {
		return new PlaceDto(place);
	}

	@Override
	public Flux<Place> findAll() {
		return this.repository().findAll().subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Mono<Place> save(PlaceDto dto) {
		return this.convertToEntity(Mono.just(dto)).flatMap((e) -> {
			return this.repository().save(e);
		}).subscribeOn(Schedulers.boundedElastic());
	}

	@Override
	public Mono<Place> findById(String id) {
		return this.placeMap.get(id)
				.switchIfEmpty(
						this.repository().findById(id)
								.switchIfEmpty(Mono.error(new EntityNotFoundException("Entity of id " + String.valueOf(id) + " not found.")))
								.subscribeOn(Schedulers.boundedElastic())
								.flatMap(c -> this.placeMap.fastPut(id, c).thenReturn(c))
				);
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
				})
				.subscribeOn(Schedulers.boundedElastic());
	}



}
