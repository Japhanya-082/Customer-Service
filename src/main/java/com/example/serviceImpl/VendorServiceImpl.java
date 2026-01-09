package com.example.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.entity.Vendor;
import com.example.exception.DuplicateVendorException;
import com.example.repository.VendorRepository;
import com.example.service.VendorService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

@Service
public class VendorServiceImpl implements VendorService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private VendorRepository vendorRepository;

    // ---------------- CREATE VENDOR ----------------
    @Override
    public Vendor createVendor(Vendor vendor) {

        StringBuilder duplicateFields = new StringBuilder();

        if (vendorRepository.findByVendorName(vendor.getVendorName()).isPresent())
            duplicateFields.append("vendorName, ");

        if (vendorRepository.findByEmail(vendor.getEmail()).isPresent())
            duplicateFields.append("email, ");

        if (vendorRepository.findByEinNumber(vendor.getEinNumber()).isPresent())
            duplicateFields.append("einNumber, ");

        if (vendorRepository.findByPhoneNumber(vendor.getPhoneNumber()).isPresent())
            duplicateFields.append("phoneNumber, ");

        if (duplicateFields.length() > 0) {
            String fields = duplicateFields.substring(0, duplicateFields.length() - 2);
            throw new DuplicateVendorException("Duplicate entry found in: " + fields);
        }

        //  DO NOT SET vendorId
        return vendorRepository.save(vendor);
    }


    // ---------------- GET ALL VENDORS ----------------
    @Override
    public List<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    // ---------------- SEARCH BY NAME ----------------
    @Override
    public List<Vendor> searchByName(String name) {
        String searchText = name.trim().toLowerCase();

        List<Vendor> vendors = vendorRepository.findByVendorNameContainingIgnoreCase(searchText);

        return vendors.stream()
            .sorted((a, b) -> {
                String aName = a.getVendorName().toLowerCase();
                String bName = b.getVendorName().toLowerCase();

                if (aName.equals(searchText) && !bName.equals(searchText)) return -1;
                if (!aName.equals(searchText) && bName.equals(searchText)) return 1;
                if (aName.startsWith(searchText) && !bName.startsWith(searchText)) return -1;
                if (!aName.startsWith(searchText) && bName.startsWith(searchText)) return 1;
                if (aName.contains(searchText) && !bName.contains(searchText)) return -1;
                if (!aName.contains(searchText) && bName.contains(searchText)) return 1;

                return aName.compareTo(bName);
            })
            .toList();
    }

    // ---------------- UPDATE VENDOR ----------------
    @Override
    public Vendor updateVendor(Long vendorId, Vendor vendor) {
        return vendorRepository.findById(vendorId).map(existing -> {
            existing.setVendorName(vendor.getVendorName());
            existing.setPhoneNumber(vendor.getPhoneNumber());
            existing.setEmail(vendor.getEmail());
            existing.setEinNumber(vendor.getEinNumber());

            if (vendor.getVendorAddress() != null) {
                existing.setVendorAddress(vendor.getVendorAddress());
            }

            return vendorRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Vendor not found with id " + vendorId));
    }

    // ---------------- DELETE VENDOR ----------------
    @Override
    public void deleteVendor(Long vendorId) {
        vendorRepository.deleteById(vendorId);
    }

    // ---------------- GET VENDORS WITH PAGINATION, SORTING & SEARCH ----------------
    @Override
    public Page<Vendor> getVendors(int page, int size, String sortField, String sortDir, String search) {
        Pageable pageable = PageRequest.of(page, size); // sort will be applied via Specification for embedded fields

        Specification<Vendor> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(search)) {
                // Root fields
                predicates.add(cb.like(cb.lower(root.get("vendorName")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("einNumber")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("phoneNumber")), "%" + search.toLowerCase() + "%"));

                // Embedded fields
                predicates.add(cb.like(cb.lower(root.get("vendorAddress").get("street")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("vendorAddress").get("suite")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("vendorAddress").get("city")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("vendorAddress").get("state")), "%" + search.toLowerCase() + "%"));
                predicates.add(cb.like(cb.lower(root.get("vendorAddress").get("zipCode")), "%" + search.toLowerCase() + "%"));
            }

            // ---------------- SORTING ----------------
            if (StringUtils.hasText(sortField)) {
                if (sortField.contains(".")) {
                    // Embedded field
                    String[] parts = sortField.split("\\.");
                    if (parts.length == 2) {
                        query.orderBy(sortDir.equalsIgnoreCase("desc")
                                ? cb.desc(root.get(parts[0]).get(parts[1]))
                                : cb.asc(root.get(parts[0]).get(parts[1])));
                    }
                } else {
                    // Root field
                    query.orderBy(sortDir.equalsIgnoreCase("desc")
                            ? cb.desc(root.get(sortField))
                            : cb.asc(root.get(sortField)));
                }
            }

            return predicates.isEmpty() ? null : cb.or(predicates.toArray(new Predicate[0]));
        };

        return vendorRepository.findAll(spec, pageable);
    }


    // ---------------- GET VENDOR BY ID ----------------
    @Override
    public Vendor getById(Long vendorId) {
        return vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found with id " + vendorId));
    }

    // ---------------- GET VENDOR BY NAME ----------------
    @Override
    public Optional<Vendor> getVendorByName(String vendorName) {
        return vendorRepository.findByVendorName(vendorName);
    }

    // ---------------- SEARCH VENDORS BY NAME ----------------
    @Override
    public List<Vendor> searchVendorsByName(String keyword) {
        return vendorRepository.findByVendorNameContainingIgnoreCase(keyword);
    }

    // ---------------- GET VENDORS BY EMAIL DOMAIN ----------------
    public List<Vendor> getVendorByDomain(String domain) {
        List<Vendor> vendors = vendorRepository.findByEmailEndingWith(domain);
        if (vendors.isEmpty()) throw new RuntimeException("No vendors found for domain: " + domain);
        return vendors;
    }
}
