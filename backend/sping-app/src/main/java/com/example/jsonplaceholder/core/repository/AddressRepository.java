package com.example.jsonplaceholder.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jsonplaceholder.core.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
} 