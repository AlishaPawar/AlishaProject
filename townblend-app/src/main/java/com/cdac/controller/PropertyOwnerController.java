package com.cdac.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cdac.dto.LoginStatus;
import com.cdac.dto.PropertyDetailsDto;
import com.cdac.dto.RegistrationStatus;
import com.cdac.dto.Status;
import com.cdac.entity.PropertyDetails;
import com.cdac.entity.PropertyOwner;
import com.cdac.exception.ServiceException;
import com.cdac.service.PropertyDetailsService;
import com.cdac.service.PropertyOwnerService;

@RestController
@CrossOrigin
public class PropertyOwnerController {
	@Autowired
	private PropertyOwnerService propertyOwnerService;
	@Autowired
	private PropertyDetailsService propertyDetailsService;
	
	@PostMapping("/ownerregister")
	public RegistrationStatus register(@RequestBody PropertyOwner propertyOwner, Map map) {
		
		try {
			int id = propertyOwnerService.register(propertyOwner);
			
			RegistrationStatus status = new RegistrationStatus();
			
			status.setStatus(true);
			status.setStatusMessage("Owner registered successfully");
			status.setId(id);
			return status;
			
		}catch(ServiceException e) {
			
			RegistrationStatus status = new RegistrationStatus();
			
			status.setStatus(false);
			status.setStatusMessage(e.getMessage());
			
			return status;
		}
		
	}
	
	@PostMapping("/owner/login")
	public LoginStatus login(@RequestBody Map<String, String> loginData) {
		try {
			String email = loginData.get("email");
			String password = loginData.get("password");
			
			PropertyOwner propertyOwner = propertyOwnerService.login(email, password);
			
			LoginStatus status = new LoginStatus();
			
			status.setStatus(true);
			status.setStatusMessage("Login successful");
			
			return status;
			
		}catch(ServiceException e) {
			
			LoginStatus status = new LoginStatus();
			
			status.setStatus(false);
			status.setStatusMessage(e.getMessage());
			
			return status;
		}
	}
		
	 @PostMapping("/addProperty/{id}")
    public ResponseEntity<Status> addDetails(@PathVariable int id , PropertyDetailsDto propertyDto) {
        try {
            PropertyDetails propertyDetails = new PropertyDetails();
        	BeanUtils.copyProperties(propertyDto, propertyDetails);
            
        	try {
    			String fileName = propertyDto.getImage1().getOriginalFilename();
    			String fileName2 = propertyDto.getImage2().getOriginalFilename();
    			String fileName3 = propertyDto.getImage3().getOriginalFilename();
    			String fileName4= propertyDto.getVideo().getOriginalFilename();
    			//TODO:here should be the code to generate a unique name for the file before proceeding further
    		//replace this later
    			propertyDetails.setImage1(fileName);
    			propertyDetails.setImage2(fileName2);
    			propertyDetails.setImage3(fileName3);
    			propertyDetails.setVideo(fileName4);
    			
    			
    			InputStream is1 = propertyDto.getImage1().getInputStream();
    			InputStream is2 = propertyDto.getImage2().getInputStream();
    			InputStream is3 = propertyDto.getImage3().getInputStream();
    			InputStream is4 = propertyDto.getVideo().getInputStream();
    			FileOutputStream os1 = new FileOutputStream("c:/uploads/" + fileName);
    			FileOutputStream os2 = new FileOutputStream("c:/uploads/" + fileName2);
    			FileOutputStream os3 = new FileOutputStream("c:/uploads/" + fileName3);
    			FileOutputStream os4 = new FileOutputStream("c:/uploads/" + fileName4);
    			FileCopyUtils.copy(is1, os1);
    			FileCopyUtils.copy(is2, os2);
    			FileCopyUtils.copy(is3, os3);
    			FileCopyUtils.copy(is4, os4);
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		
    			
    		}
        	PropertyOwner propertyOwner = propertyOwnerService.getOwner(id);
        	  propertyDetails.setOwner(propertyOwner);
              propertyDetailsService.addpropertyDetails(propertyDetails);
            
            

            
            Status status = new Status();
            status.setStatus(true);
            status.setMessageIfAny("Property details added successfully");
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (ServiceException e) {
            Status status = new Status();
            status.setStatus(false);
            status.setMessageIfAny(e.getMessage());
            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        }
    }
	 
	 @GetMapping("/propertyDetails/{propertyDetailsId}")
	 public ResponseEntity<PropertyDetailsDto> getPropertyDetails(@PathVariable int propertyDetailsId) {
	     try {
	         PropertyDetails propertyDetails = propertyOwnerService.getPropertyDetails(propertyDetailsId);

	         // Creating a DTO to avoid exposing entity-specific details
	         PropertyDetailsDto propertyDetailsDto = new PropertyDetailsDto();
	         BeanUtils.copyProperties(propertyDetails, propertyDetailsDto);

	         return new ResponseEntity<>(propertyDetailsDto, HttpStatus.OK);
	     } catch (ServiceException e) {
	         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	     }
	 }
	
	@DeleteMapping("/propertyDetails/{propertyDetailsId}")
	public ResponseEntity<Status> deletePropertyDetails(@PathVariable int propertyDetailsId, @RequestParam int ownerId) {
	    try {
	        propertyOwnerService.deletePropertyDetails(propertyDetailsId, ownerId);
	        Status status = new Status();
	        status.setStatus(true);
	        status.setMessageIfAny("Details deleted successfully");
	        return new ResponseEntity<>(status, HttpStatus.OK);
	    } catch (ServiceException e) {
	        Status status = new Status();
	        status.setStatus(false);
	        status.setMessageIfAny("Details do not exist!!!");
	        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
	    }
	}
	
	@PostMapping("/{ownerId}/approve")
	public ResponseEntity<Status> approveOwner(@PathVariable int ownerId) {
	    try {
	        propertyOwnerService.approveOwner(ownerId);
	        Status status = new Status();
	        status.setStatus(true);
	        status.setMessageIfAny("Owner approved successfully");
	        return new ResponseEntity<>(status, HttpStatus.OK);
	    } catch (ServiceException e) {
	        Status status = new Status();
	        status.setStatus(false);
	        status.setMessageIfAny(e.getMessage());
	        return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
	    }
	}
}
