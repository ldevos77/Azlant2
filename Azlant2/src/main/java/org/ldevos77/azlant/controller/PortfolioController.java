package org.ldevos77.azlant.controller;

import org.ldevos77.azlant.exception.PortfolioNotFoundException;
import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/portfolios")
public class PortfolioController {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	/**
	 * Get all portfolios without any restriction
	 * 
	 * @return All portfolios
	 */
	@GetMapping
	public Iterable<Portfolio> getAllPortfolio() {
		return portfolioRepository.findAll();
	}
	
	/**
	 * Get a specific portfolio based on its ID
	 * 
	 * @param id
	 * @return Requested portfolio
	 */
	@GetMapping(value = "/{id}")
	public Portfolio getPortfolio(@PathVariable Long id) {
		return portfolioRepository.findById(id)
				.orElseThrow(() -> new PortfolioNotFoundException());
	}
	
	/**
	 * Create new portfolio
	 * 
	 * @param name
	 * @return
	 */
	@PostMapping
	public ResponseEntity<String> addPortfolio(@RequestBody Portfolio portfolio) {
		portfolioRepository.save(portfolio);
		return new ResponseEntity<String>("POST Response", HttpStatus.CREATED);
	}
}
