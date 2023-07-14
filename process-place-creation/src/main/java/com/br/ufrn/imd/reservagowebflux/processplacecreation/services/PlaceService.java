package com.br.ufrn.imd.reservagowebflux.processplacecreation.services;

import br.ufrn.imd.reservagowebflux.base.service.GenericService;
import com.br.ufrn.imd.reservagowebflux.processplacecreation.models.Place;
import com.br.ufrn.imd.reservagowebflux.processplacecreation.models.dto.PlaceDto;
import com.br.ufrn.imd.reservagowebflux.processplacecreation.repositories.PlaceRepository;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PlaceService extends GenericService<Place, PlaceDto, String> {

	private final PlaceRepository placeRepository;

	private RLocalCachedMapReactive<String, Place> placeMap;

	@Autowired
	public PlaceService(PlaceRepository placeRepository, RedissonReactiveClient cacheser) {
		this.placeRepository = placeRepository;
		LocalCachedMapOptions<String, Place> mapOptions = LocalCachedMapOptions.<String, Place>defaults()
				.syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE) // If data changes, redis update the local cache of others services
				.reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR); // If connection fails, local cache is cleaned after reconnection
		this.placeMap = cacheser.getLocalCachedMap("/place/", new TypedJsonJacksonCodec(String.class, Place.class), mapOptions);
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
	public Mono<Place> convertToEntity(Mono<PlaceDto> placeDto) {
		return placeDto
				.zipWith(placeDto, (user, dto) -> {
					Place place = new Place();
					place.setHostId(dto.hostId());
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
