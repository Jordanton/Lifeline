package com.itafin.lifeline.dao;

import java.util.List;

import com.itafin.lifeline.model.Supplier;

public interface ScersDao {

	public List<Supplier> retrieve_activeSupplierList();
	public Supplier getSupplierByCode(String supplierCode);
}
