package com.example.jsonplaceholder.core.service;

import com.example.jsonplaceholder.core.dto.AddressDto;
import com.example.jsonplaceholder.core.mapper.AddressMapper;
import com.example.jsonplaceholder.core.model.Address;
import com.example.jsonplaceholder.core.repository.AddressRepository;
import com.example.jsonplaceholder.core.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressMapper addressMapper;
    @InjectMocks
    private AddressService addressService;

    private Address address;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        address = new Address();
        addressDto = new AddressDto();
    }

    @Test
    void getAll_whenAddressesExist_shouldReturnListOfAddressDtos() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList(address));
        when(addressMapper.toDto(address)).thenReturn(addressDto);
        List<AddressDto> result = addressService.getAll();
        assertEquals(1, result.size());
        verify(addressRepository).findAll();
        verify(addressMapper).toDto(address);
    }

    @Test
    void getById_whenAddressExists_shouldReturnAddressDto() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        when(addressMapper.toDto(address)).thenReturn(addressDto);
        Optional<AddressDto> result = addressService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(addressDto, result.get());
    }

    @Test
    void create_whenValidAddressDto_shouldSaveAndReturnAddressDto() {
        when(addressMapper.toEntity(addressDto)).thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDto);
        AddressDto result = addressService.create(addressDto);
        assertEquals(addressDto, result);
    }

    @Test
    void update_whenAddressExists_shouldUpdateAndReturnAddressDto() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressMapper).update(addressDto, address);
        when(addressRepository.save(address)).thenReturn(address);
        when(addressMapper.toDto(address)).thenReturn(addressDto);
        Optional<AddressDto> result = addressService.update(1L, addressDto);
        assertTrue(result.isPresent());
        assertEquals(addressDto, result.get());
    }

    @Test
    void delete_whenAddressExists_shouldReturnTrue() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        doNothing().when(addressRepository).delete(address);
        boolean result = addressService.delete(1L);
        assertTrue(result);
        verify(addressRepository).delete(address);
    }

    @Test
    void delete_whenAddressDoesNotExist_shouldReturnFalse() {
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        boolean result = addressService.delete(1L);
        assertFalse(result);
    }
} 