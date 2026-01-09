//package com.example.DTO;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class VendorAddress {
//
//	private String street;
//    private String suite; // Added, since your JSON has "suite"
//    private String city;
//    private String state;
//    private String zipCode;
//}


package com.example.DTO;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorAddress {

    private String street;
    private String suite;
    private String city;
    private String state;
    private String zipCode;
}
