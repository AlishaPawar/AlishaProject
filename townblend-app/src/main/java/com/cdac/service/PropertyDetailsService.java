package com.cdac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.entity.PropertyDetails;
import com.cdac.repository.PropertyDetailsRepository;

@Service
public class PropertyDetailsService {

	@Autowired
	private PropertyDetailsRepository propertyDetailsRepository;
	
	public void addpropertyDetails(PropertyDetails propertyDetails) {
		propertyDetailsRepository.save(propertyDetails);
	}
}
