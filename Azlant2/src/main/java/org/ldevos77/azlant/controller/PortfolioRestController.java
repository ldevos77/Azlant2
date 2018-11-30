package org.ldevos77.azlant.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.ldevos77.azlant.exception.PortfolioNotFoundException;
import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.model.PortfolioLine;
import org.ldevos77.azlant.repository.PortfolioLineRepository;
import org.ldevos77.azlant.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Portfolio controller for Rest API Requests.
 * 
 * Class based on Spring HATEOAS library, used to write hypermedia-driven outputs 
 * as part of RESTful API. 
 * 
 * @author Ludovic Devos
 */
@RestController
@RequestMapping(path="/portfolios")
public class PortfolioRestController {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Autowired
	private PortfolioResourceAssembler portfolioResourceAssembler;
	
	@Autowired
	private PortfolioLineRepository portfolioLineRepository;
	
	/**
	 * Get all portfolios without any restriction
	 * 
	 * @return All portfolios
	 */
	@GetMapping
	public Resources<Resource<Portfolio>> getAllPortfolio() {
		
		List<Resource<Portfolio>> portfolios = StreamSupport
			    .stream(portfolioRepository.findAll().spliterator(), false)
			.map(portfolioResourceAssembler::toResource)
			.collect(Collectors.toList());
		
		return new Resources<>(portfolios,
				linkTo(methodOn(PortfolioRestController.class).getAllPortfolio()).withSelfRel());
		
	}
	
	/**
	 * Get a specific portfolio based on its ID
	 * 
	 * @param id : portfolio ID
	 * @return Requested portfolio
	 */
	@GetMapping(value = "/{id}")
	public Resource<Portfolio> getPortfolio(@PathVariable Long id) {
		
		Portfolio portfolio = portfolioRepository.findById(id)
				.orElseThrow(() -> new PortfolioNotFoundException());
		
		return portfolioResourceAssembler.toResource(portfolio);

	}
	
	/**
	 * Create new portfolio
	 * 
	 * @param portfolio
	 * @return
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Resource<Portfolio> addPortfolio(@RequestBody Portfolio portfolio) {
		
		Portfolio newPortfolio = portfolioRepository.save(portfolio);
		
		return portfolioResourceAssembler.toResource(newPortfolio);

	}
	
	/**
	 * Get lines for a specific portfolio ID
	 * 
	 * @param id : portfolio ID
	 * @return Requested portfolio lines
	 */
	@GetMapping(value = "/{id}/lines")
	public Iterable<PortfolioLine> getPortfolioLines(@PathVariable Long id) {
		Optional<Portfolio> portfolio = portfolioRepository.findById(id);
		if (portfolio.isPresent()) {
			return portfolioLineRepository.findByPortfolio(portfolio.get());
		}
		else {
			return null;
		}
	}
	
	/**
	 * Create new portfolio line
	 * 
	 * @param portfolio
	 * @return
	 */
	@PostMapping(value = "/{id}/lines")
	@ResponseStatus(HttpStatus.CREATED)
	public PortfolioLine addPortfolioLine(@RequestBody PortfolioLine portfolioLine) {
		return portfolioLineRepository.save(portfolioLine);
	}
	
}
