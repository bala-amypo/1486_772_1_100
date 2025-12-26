package com.example.demo.service.impl;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Vendor;
import com.example.demo.repository.VendorRepository;
import com.example.demo.service.VendorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository repository;

    public VendorServiceImpl(VendorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vendor createVendor(Vendor vendor) {
        return repository.save(vendor);
    }

    @Override
    public Vendor getVendor(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationException("Vendor not found"));
    }

    @Override
    public List<Vendor> getAllVendors() {
        return repository.findAll();
    }
}
