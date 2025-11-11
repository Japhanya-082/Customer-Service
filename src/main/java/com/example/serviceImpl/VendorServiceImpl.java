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
                    if (vendor.getVendorId()== null) {
                        // Find the smallest missing vendorId
                        List<Long> ids = vendorRepository.findAll()
                                                         .stream()
                                                         .map(Vendor::getVendorId)
                                                         .sorted()
                                                         .toList();

                        long nextId = 1;
                        for (Long id : ids) {
                            if (id != nextId) break;
                            nextId++;
                        }
                        vendor.setVendorId(nextId);
                    }
                    return vendorRepository.save(vendor);
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
                        existing.setEinNumber(vendor.getEinNumber());

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
                public Page<Vendor> searchAndSortVendors(String keyword, int page, int size, String sortField, String sortDir) {
                    Sort sort = sortDir.equalsIgnoreCase("asc") ?
                            Sort.by(sortField).ascending() : Sort.by(sortField).descending();

                    Pageable pageable = PageRequest.of(page, size, sort);

                    if (keyword == null || keyword.isEmpty()) {
                        // No keyword → just return all with pagination and sorting
                        return vendorRepository.findAll(pageable);
                    } else {
                        // Keyword present → apply search query with pageable (which already includes sort)
                        return vendorRepository.searchVendors(keyword, pageable);
                    }
                }


                public List<Vendor> getVendorByDomain(String domain) {
                    // domain could be like "tcs.com"
                	List<Vendor> vendors = vendorRepository.findByEmailEndingWith(domain);
                    if (vendors.isEmpty()) {
                    	throw new RuntimeException("No vendors found for domain: " + domain);
					}
                    return vendors;
                }
                
				@Override
				public Vendor getById(Long vendorId) {
					
					return vendorRepository.findById(vendorId).get();
				}

				

}

