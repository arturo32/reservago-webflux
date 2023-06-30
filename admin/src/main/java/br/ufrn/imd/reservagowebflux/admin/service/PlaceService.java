package br.ufrn.imd.reservagowebflux.admin.service;

import static org.redisson.api.LocalCachedMapOptions.defaults;

import br.ufrn.imd.reservagowebflux.admin.model.Place;
import br.ufrn.imd.reservagowebflux.admin.model.dto.PlaceDto;
import br.ufrn.imd.reservagowebflux.admin.repository.PlaceRepository;
import br.ufrn.imd.reservagowebflux.base.exception.EntityNotFoundException;
import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LocalCachedMapOptions.ReconnectionStrategy;
import org.redisson.api.LocalCachedMapOptions.SyncStrategy;
import org.redisson.api.RLocalCachedMapReactive;
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

//	private RMapReactive<String, Place> placeMap;

	private RLocalCachedMapReactive<String, Place> placeMap;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, UserService userService, RedissonReactiveClient cacheser) {
		this.placeRepository = placeRepository;
		this.userService = userService;
		LocalCachedMapOptions<String, Place> mapOptions = LocalCachedMapOptions.<String, Place>defaults()
				.syncStrategy(SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.placeMap = cacheser.getLocalCachedMap("/place/", new TypedJsonJacksonCodec(String.class, Place.class), mapOptions);
//		this.placeMap = cacheser.getMap("/place/", new TypedJsonJacksonCodec(String.class, Place.class));
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
