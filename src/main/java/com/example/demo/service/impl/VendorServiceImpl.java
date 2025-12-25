package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public Vendor createVendor(Vendor vendor) {

        if (vendor == null) {
            throw new ValidationException("Vendor cannot be null");
        }

        if (vendor.getVendorName() == null || vendor.getVendorName().trim().isEmpty()) {
            throw new ValidationException("Vendor name is required");
        }

        if (vendorRepository.existsByVendorName(vendor.getVendorName())) {
            throw new ValidationException("Duplicate vendor name");
        }

        return vendorRepository.save(vendor);
    }

    @Override
    public Vendor getVendor(Long id) {

        if (id == null) {
            throw new ValidationException("Vendor id cannot be null");
        }

        return vendorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Vendor not found"));
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }
}
