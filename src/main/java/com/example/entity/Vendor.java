package com.example.entity;

import com.example.DTO.VendorAddress;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vendor_info")
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long vendorId;

    private String vendorName;
    private String vendorAccountNumber;
    private String email;
    private String gstNumber;
    private Long phoneNumber;
    
    @Embedded
    private VendorAddress vendorAddress; }

