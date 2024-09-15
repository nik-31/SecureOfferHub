package com.project_nikhil.SecureOfferHub.controller;

import com.project_nikhil.SecureOfferHub.model.Offer;
import com.project_nikhil.SecureOfferHub.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    @Autowired
    private OfferRepository offerRepository;
    @GetMapping
    public List<Offer> getAllActiveOffers() {
        return offerRepository.findByActiveTrue();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Offer createOffer(@RequestBody Offer offer) {
        return offerRepository.save(offer);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable String id, @RequestBody Offer offerDetails) {
        return offerRepository.findById(id).map(offer -> {
            offer.setTitle(offerDetails.getTitle());
            offer.setDescription(offerDetails.getDescription());
            offer.setStartDate(offerDetails.getStartDate());
            offer.setEndDate(offerDetails.getEndDate());
            offer.setActive(offerDetails.isActive());
            return ResponseEntity.ok(offerRepository.save(offer));
        }).orElse(ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable String id) {
        return offerRepository.findById(id).map(offer -> {
            offerRepository.delete(offer);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
