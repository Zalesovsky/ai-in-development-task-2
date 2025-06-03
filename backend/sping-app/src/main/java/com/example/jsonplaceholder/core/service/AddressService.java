package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.AddressDto;
import com.example.jsonplaceholder.core.model.Address;
import com.example.jsonplaceholder.core.repository.AddressRepository;
import com.example.jsonplaceholder.core.mapper.AddressMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressDto> getAll() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AddressDto> getById(Long id) {
        return addressRepository.findById(id).map(addressMapper::toDto);
    }

    public AddressDto create(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        return addressMapper.toDto(addressRepository.save(address));
    }

    public Optional<AddressDto> update(Long id, AddressDto addressDto) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    addressMapper.update(addressDto, existingAddress);
                    return addressMapper.toDto(addressRepository.save(existingAddress));
                });
    }

    public boolean delete(Long id) {
        return addressRepository.findById(id)
                .map(address -> {
                    addressRepository.delete(address);
                    return true;
                })
                .orElse(false);
    }
} 