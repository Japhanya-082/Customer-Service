package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

	@Query("SELECT v FROM Vendor v " +
		       "WHERE LOWER(v.vendorName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(v.vendorAccountNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(v.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(v.vendorAddress.street) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(v.vendorAddress.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(v.vendorAddress.state) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(v.vendorAddress.zipCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(CAST(v.einNumber AS string)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
		       "   OR LOWER(CAST(v.phoneNumber AS string)) LIKE LOWER(CONCAT('%', :keyword, '%'))")
		Page<Vendor> searchVendors(@Param("keyword") String keyword, Pageable pageable);
	
	// Find vendor using email domain (e.g., endsWith("@tcs.com"))
    List<Vendor> findByEmailEndingWith(String domain);

    // OR, if you want to match partial domain (no '@')
    Optional<Vendor> findByEmailContains(String domain);

}
