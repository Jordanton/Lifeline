/**
 * This class is intended to convert the supplierCode submitted through the 
 * jsp form as a single value, into a Supplier object when being passed into the confirm page
 * but it is not being called for some reason
 */

package com.itafin.lifeline.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.itafin.lifeline.model.Supplier;
import com.itafin.lifeline.service.ScersService;

@Component
public class SupplierConverter implements Converter<String, Supplier>{

	@Autowired ScersService scersService;
	
	@Override
	public Supplier convert(String supplierCode) {
		return scersService.getSupplierByCode(supplierCode);
	}

}
