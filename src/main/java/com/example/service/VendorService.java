package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.entity.Vendor;

public interface VendorService {

	public Vendor createVendor(Vendor vendor);
	public Vendor getById(Long vendorId);
	public List<Vendor> getAll();
	public Vendor updateVendor(Long vendorId, Vendor vendor);
	public void deleteVendor(Long vendorId);
	
	public Page<Vendor> searchAndSortVendors(String keyword, int page, int size, String sortField, String sortDir);
}
