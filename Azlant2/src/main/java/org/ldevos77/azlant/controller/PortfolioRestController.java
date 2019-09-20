package org.ldevos77.azlant.controller;

import java.util.Optional;

import org.ldevos77.azlant.exception.PortfolioNotFoundException;
import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.model.PortfolioLine;
import org.ldevos77.azlant.repository.PortfolioLineRepository;
import org.ldevos77.azlant.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * @author Ludovic Devos
 */
@CrossOrigin
@RestController
@RequestMapping(path="/portfolios")
public class PortfolioRestController {
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Autowired
	private PortfolioLineRepository portfolioLineRepository;
	
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
	 * @param id : portfolio ID
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
	 * @param portfolio
	 * @return
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Portfolio addPortfolio(@RequestBody Portfolio portfolio) {
		return portfolioRepository.save(portfolio);
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
			Iterable<PortfolioLine> portfolioLineList = portfolioLineRepository.findByPortfolio(portfolio.get());
			return portfolioLineList;
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

	/**
	 * Delete a specific portfolio based on its ID
	 * 
	 * @param id : portfolio ID
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePortfolio(@PathVariable long id) {
		Optional<Portfolio> portfolio = portfolioRepository.findById(id);
		if (portfolio.isPresent()) {
			portfolioLineRepository.deleteByPortfolio(portfolio.get());
			portfolioRepository.deleteById(id);
		}
		else 
			throw(new PortfolioNotFoundException());
	}

	/**
	 * Delete a specific portfolio line based on its ID
	 * 
	 * @param id : portfolio line ID
	 */
	@DeleteMapping("/{portfolioId}/lines/{lineId}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePortfolioLine(@PathVariable long portfolioId, @PathVariable long lineId) {
		Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
		if (portfolio.isPresent()) {
			portfolioLineRepository.deleteById(lineId);
		}
		else 
			throw(new PortfolioNotFoundException());
	}

}
