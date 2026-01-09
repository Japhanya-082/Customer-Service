package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.example.entity.Vendor;

public interface VendorService {

	public Vendor createVendor(Vendor vendor);
	public Vendor getById(Long vendorId);
	
	public List<Vendor> searchByName(String name);
	public List<Vendor> getAll();
	public Vendor updateVendor(Long vendorId, Vendor vendor);
	public void deleteVendor(Long vendorId);
	public Optional<Vendor> getVendorByName(String vendorName);
	public List<Vendor> searchVendorsByName(String keyword);
	public Page<Vendor> getVendors(int page, int size, String sortField, String sortDir, String search);
}
