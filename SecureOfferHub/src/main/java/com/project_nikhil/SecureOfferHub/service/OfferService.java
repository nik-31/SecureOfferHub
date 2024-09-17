package com.project_nikhil.SecureOfferHub.service;

import com.project_nikhil.SecureOfferHub.model.Offer;
import com.project_nikhil.SecureOfferHub.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public List<Offer> getAllActiveOffers() {
        return offerRepository.findByActiveTrue();
    }
    public Offer createOffer(@RequestBody Offer offer) throws Exception {
        Date todaysDate = new Date();
        if(todaysDate.compareTo(offer.getStartDate()) < 0) {
            throw new Exception("You cannot add previous Date");
        }
        return offerRepository.save(offer);
    }
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
    public ResponseEntity<Object> deleteOffer(@PathVariable String id) {
        return offerRepository.findById(id).map(offer -> {
            offerRepository.delete(offer);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
