package com.example.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorAddressDTO {
    private String street;
    private String suite;
    private String city;
    private String state;
    private String zipCode;
}
