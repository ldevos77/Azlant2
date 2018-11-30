package org.ldevos77.azlant.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.ldevos77.azlant.model.Portfolio;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

/**
 * Converts Portfolio objects to Resource<Portfolio> objects.
 * 
 * Class based on Spring HATEOAS library, used to write hypermedia-driven outputs 
 * as part of RESTful API. 
 * 
 * @author Ludovic Devos
 */
@Component
public class PortfolioResourceAssembler implements ResourceAssembler<Portfolio, Resource<Portfolio>> {

	@Override
	public Resource<Portfolio> toResource(Portfolio portfolio) {

		return new Resource<>(portfolio,
			linkTo(methodOn(PortfolioRestController.class).getPortfolio(portfolio.getId())).withSelfRel(),
			linkTo(methodOn(PortfolioRestController.class).getAllPortfolio()).withRel("portfolios"));
		
	}
	
}
