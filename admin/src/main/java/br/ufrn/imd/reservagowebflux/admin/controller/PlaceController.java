package br.ufrn.imd.reservagowebflux.admin.controller;

import br.ufrn.imd.reservagowebflux.admin.model.Place;
import br.ufrn.imd.reservagowebflux.admin.model.dto.PlaceDto;
import br.ufrn.imd.reservagowebflux.admin.service.GenericService;
import br.ufrn.imd.reservagowebflux.admin.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("place")
public class PlaceController extends GenericController<Place, PlaceDto, String> {

	private final PlaceService placeService;

	@Autowired
	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}


	@Override
	protected GenericService<Place, PlaceDto, String> service() {
		return this.placeService;
	}
}
