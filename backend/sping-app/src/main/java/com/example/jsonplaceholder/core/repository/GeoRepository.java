package com.example.jsonplaceholder.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jsonplaceholder.core.model.Geo;

@Repository
public interface GeoRepository extends JpaRepository<Geo, Long> {
} 