package com.example.repository;

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
           "   OR LOWER(v.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(STR(v.gstNumber)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(STR(v.phoneNumber)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "ORDER BY v.vendorId ASC")
    Page<Vendor> searchVendors(@Param("keyword") String keyword, Pageable pageable);
}
