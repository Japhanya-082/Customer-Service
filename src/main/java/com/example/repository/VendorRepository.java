package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {

    // ---------------- DUPLICATE CHECK ----------------
    Optional<Vendor> findByVendorName(String vendorName);
    Optional<Vendor> findByEmail(String email);
    Optional<Vendor> findByEinNumber(String einNumber);
    Optional<Vendor> findByPhoneNumber(String phoneNumber);

    // ---------------- SEARCH BY KEYWORD (ALL FIELDS INCLUDING EMBEDDED) ----------------
    @Query("SELECT v FROM Vendor v " +
           "LEFT JOIN v.vendorAddress a " +
           "WHERE LOWER(v.vendorName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(v.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(a.street) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(a.suite) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(a.city) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(a.state) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR LOWER(a.zipCode) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "   OR v.einNumber LIKE CONCAT('%', :keyword, '%') " +
           "   OR v.phoneNumber LIKE CONCAT('%', :keyword, '%')")
    Page<Vendor> searchVendors(@Param("keyword") String keyword, Pageable pageable);

    // ---------------- SEARCH BY EMAIL DOMAIN ----------------
    List<Vendor> findByEmailEndingWith(String domain);
    Optional<Vendor> findByEmailContains(String domain);

    // ---------------- SEARCH BY NAME ----------------
    List<Vendor> findByVendorNameContainingIgnoreCase(String name);
}
