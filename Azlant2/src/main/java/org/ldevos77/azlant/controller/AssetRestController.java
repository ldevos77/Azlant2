package org.ldevos77.azlant.controller;

import org.ldevos77.azlant.exception.AssetNotFoundException;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetQuote;
import org.ldevos77.azlant.repository.AssetQuoteRepository;
import org.ldevos77.azlant.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Asset controller for Rest API Requests.
 * 
 * @author Ludovic Devos
 */
@CrossOrigin
@RestController
@RequestMapping(path="/assets")
public class AssetRestController {

	Logger logger = LoggerFactory.getLogger(AssetRestController.class);

	@Autowired
	private AssetRepository assetRepository;
	
	@Autowired
	private AssetQuoteRepository assetQuoteRepository;
	
	/**
	 * Get all assets without any restriction
	 * 
	 * @return All asset
	 */
	@GetMapping
	public Iterable<Asset> getAllAsset() {
		return assetRepository.findAll();
	}
	
	/**
	 * Get assets by code
	 * 
	 * @return Asset
	 */
	@GetMapping(params="code")
	public Asset getAssetByIsinCode(@RequestParam("code") String code) {
		logger.debug("AssetRestController.getAssetByIsinCode(code=)");
		return assetRepository.findByCode(code)
				.orElseThrow(() -> new AssetNotFoundException());
	}
	
	/**
	 * Get a specific asset based on its ID
	 * 
	 * @param id
	 * @return Requested asset
	 */
	@GetMapping(value = "/{id}")
	public Asset getAsset(@PathVariable Long id) {
		Asset asset = assetRepository.findById(id)
					.orElseThrow(() -> new AssetNotFoundException());
		
		return asset;
	}
	
	/**
	 * Get quotes for a specific asset ID
	 * 
	 * @param id : asset ID
	 * @return Requested asset quotes
	 */
	@GetMapping(value = "/{id}/quotes")
	public Iterable<AssetQuote> getAssetQuotes(@PathVariable Long id) {
		Asset asset = assetRepository.findById(id)
					.orElseThrow(() -> new AssetNotFoundException());
		return assetQuoteRepository.findByAsset(asset);
	}

}
