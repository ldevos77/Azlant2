package org.ldevos77.azlant.controller;

import java.util.Collections;

import org.ldevos77.azlant.exception.AssetNotFoundException;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetQuote;
import org.ldevos77.azlant.repository.AssetQuoteRepository;
import org.ldevos77.azlant.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Asset controller for Rest API Request
 * 
 * @author Ludovic Devos
 */
@RestController
@RequestMapping(path="/assets")
public class AssetRestController {

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
	 * Get assets by ISIN Code
	 * 
	 * @return Asset
	 */
	@GetMapping(params="isin")
	public Iterable<Asset> getAssetByIsinCode(@RequestParam("isin") String isinCode) {
		Asset asset = assetRepository.findByIsinCode(isinCode)
				.orElseThrow(() -> new AssetNotFoundException());
		return Collections.singletonList(asset);
	}
	
	/**
	 * Get a specific asset based on its ID
	 * 
	 * @param id
	 * @return Requested asset
	 */
	@GetMapping(value = "/{id}")
	public Asset getAsset(@PathVariable Long id) {
		return assetRepository.findById(id)
				.orElseThrow(() -> new AssetNotFoundException());
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
