package com.project_nikhil.SecureOfferHub.controller;

import com.project_nikhil.SecureOfferHub.model.Offer;
import com.project_nikhil.SecureOfferHub.repository.OfferRepository;
import com.project_nikhil.SecureOfferHub.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @GetMapping
    public List<Offer> getAllActiveOffers() {
        return offerService.getAllActiveOffers();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Offer createOffer(@RequestBody Offer offer) {
        return offerService.createOffer(offer);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable String id, @RequestBody Offer offerDetails) {
        return offerService.updateOffer(id, offerDetails);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOffer(@PathVariable String id) {
        return offerService.deleteOffer(id);
    }
}
