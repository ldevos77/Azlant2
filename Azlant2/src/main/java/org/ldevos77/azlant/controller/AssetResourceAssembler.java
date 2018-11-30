package org.ldevos77.azlant.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.ldevos77.azlant.model.Asset;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

/**
 * Converts Asset objects to Resource<Asset> objects.
 * 
 * Class based on Spring HATEOAS library, used to write hypermedia-driven outputs 
 * as part of RESTful API. 
 * 
 * @author Ludovic Devos
 */
@Component
public class AssetResourceAssembler implements ResourceAssembler<Asset, Resource<Asset>> {

	@Override
	public Resource<Asset> toResource(Asset asset) {

		return new Resource<>(asset,
			linkTo(methodOn(AssetRestController.class).getAsset(asset.getId())).withSelfRel(),
			linkTo(methodOn(AssetRestController.class).getAllAsset()).withRel("assets"));
		
	}
	
}
