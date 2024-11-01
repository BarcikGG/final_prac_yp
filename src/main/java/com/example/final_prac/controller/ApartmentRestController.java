package com.example.final_prac.controller;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.ApartmentDTO;
import com.example.final_prac.service.ApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/apartments")
@RequiredArgsConstructor
public class ApartmentRestController {

    private final ApartmentService apartmentService;
    private final ModelMapper modelMapper;

    private ApartmentDTO convertToDto(Apartment apartment) {
        return modelMapper.map(apartment, ApartmentDTO.class);
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDTO> getAllApartments() {
        return apartmentService.getAllApartments()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartment> getApartment(@PathVariable Long id) {
        return ResponseEntity.ok(apartmentService.getApartmentById(id));
    }

    @PostMapping
    public ResponseEntity<Apartment> createApartment(@Valid @RequestBody Apartment apartment) {
        return ResponseEntity.ok(apartmentService.createApartment(apartment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apartment> updateApartment(@PathVariable Long id,
                                                     @Valid @RequestBody Apartment apartment) {
        return ResponseEntity.ok(apartmentService.updateApartment(id, apartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return ResponseEntity.ok().build();
    }
}
