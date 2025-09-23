package com.example.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.entity.Vendor;
import com.example.repository.VendorRepository;
import com.example.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService{

                @Autowired
                private VendorRepository vendorRepository;
                
               
                @Override
                public Vendor createVendor(Vendor vendor) {
                    return vendorRepository.save(vendor); // returns saved entity with generated ID
                }           
              

                @Override
                public List<Vendor> getAll() {
                 return vendorRepository.findAll();
                }

                @Override
                public Vendor updateVendor(Long vendorId, Vendor vendor) {
                    return vendorRepository.findById(vendorId).map(existing -> {
                        existing.setVendorName(vendor.getVendorName());
                        existing.setVendorAccountNumber(vendor.getVendorAccountNumber());
                        existing.setPhoneNumber(vendor.getPhoneNumber());
                        existing.setEmail(vendor.getEmail());
                        existing.setGstNumber(vendor.getGstNumber());

                        if (vendor.getVendorAddress() != null) {
                            existing.setVendorAddress(vendor.getVendorAddress());
                        }

                        return vendorRepository.save(existing);
                    }).orElseThrow(() -> new RuntimeException("Vendor not found with id " + vendorId));
                }

                @Override
                public void deleteVendor(Long vendorId) {
                       vendorRepository.deleteById(vendorId);
                }


                @Override
                public Page<Vendor> searchVendors(String keyword, int page, int size) {
                    Pageable pageable = PageRequest.of(page, size, Sort.by("vendorId").ascending());
                    
                    if (keyword == null || keyword.isEmpty()) {
                        // No keyword → return all vendors paginated
                        return vendorRepository.findAll(pageable);
                    } else {
                        // Search with keyword
                        return vendorRepository.searchVendors(keyword, pageable);
                    }
                }

				

}

