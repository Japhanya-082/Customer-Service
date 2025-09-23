package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DTO.RestAPIResponse;
import com.example.entity.Vendor;
import com.example.serviceImpl.VendorServiceImpl;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	
	@Autowired 
	private VendorServiceImpl vendorServiceImpl;
	
	@PostMapping("/save")
	public ResponseEntity<RestAPIResponse> saveVendor(@RequestBody Vendor vendor){
		try {
			 return new ResponseEntity<>(new RestAPIResponse("success","Registered Successfully",vendorServiceImpl.createVendor(vendor)),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RestAPIResponse("error","registerd failed"),HttpStatus.OK);
	}
}
	
    @GetMapping("getall")
	public ResponseEntity<RestAPIResponse> getAllVendors(){
		try {
			 return new ResponseEntity<>(new RestAPIResponse("success","All Vendors Data Successfully",vendorServiceImpl.getAll()),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new RestAPIResponse("error","Getting Data failed"),HttpStatus.OK);
	}
    }
		
    @GetMapping("/search")
    public ResponseEntity<RestAPIResponse> searchVendors(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Vendor> result = vendorServiceImpl.searchVendors(keyword, page, size);
            return new ResponseEntity<>(new RestAPIResponse("Success", "Vendors Retrieved Successfully (Search + Pagination)",result),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new RestAPIResponse("Error", "Failed to search vendors"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
		@PutMapping("/{vendorId}")
		public ResponseEntity<RestAPIResponse> updateVendor(@PathVariable Long vendorId, @RequestBody Vendor vendor){
			try {
				 return new ResponseEntity<>(new RestAPIResponse("success","Vendor Data Updated Successfully",vendorServiceImpl.updateVendor(vendorId, vendor)),HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(new RestAPIResponse("error","Updated failed or Inavlaid VendorId to update the data"),HttpStatus.OK);
		}
		}
		
		@DeleteMapping("/{vendorId}")
		public ResponseEntity<RestAPIResponse> deleteVendor(@PathVariable Long vendorId){
			 try {
			       vendorServiceImpl.deleteVendor(vendorId);
			        RestAPIResponse response = new RestAPIResponse(  "success",  "Deleted Successfully" );
			        return ResponseEntity.ok(response);
			    } catch (Exception e) {
			        RestAPIResponse response = new RestAPIResponse( "error", "User Data is Not Deleted" );
			        return ResponseEntity.ok(response);
			    }
		}
		
		
	
    
}
