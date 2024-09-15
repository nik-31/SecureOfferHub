package com.project_nikhil.SecureOfferHub.repository;

import com.project_nikhil.SecureOfferHub.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends MongoRepository<Offer, String> {
    List<Offer> findByActiveTrue();
}
