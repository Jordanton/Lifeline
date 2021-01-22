package com.itafin.lifeline.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itafin.lifeline.dao.ScersDao;
import com.itafin.lifeline.dao.ScersDaoImpl;
import com.itafin.lifeline.model.Supplier;

@Service("scersService")
public class ScersService {

	@Autowired
	ScersDao scersDao;
	
	public List<Supplier> getActiveSupplierList() {
		List<Supplier> list = null;
		
		list = scersDao.retrieve_activeSupplierList();
		
		return list;
	}
	
	public Supplier getSupplierByCode(String supplierCode) {
		Supplier supplier = null;
		supplier = scersDao.getSupplierByCode(supplierCode);
		
		return supplier;
	}

}
