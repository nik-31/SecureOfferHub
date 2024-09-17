package com.project_nikhil.SecureOfferHub.controller;

import com.project_nikhil.SecureOfferHub.model.Offer;
import com.project_nikhil.SecureOfferHub.model.User;
import com.project_nikhil.SecureOfferHub.repository.OfferRepository;
import com.project_nikhil.SecureOfferHub.repository.UserRepository;
import com.project_nikhil.SecureOfferHub.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/mail")
public class EmailController {
    @Autowired
    MailService mailService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;
    @GetMapping
    public void sendOffersInMail() throws MessagingException {
        List<User> allUsers = userRepository.findAll();
        List<Offer> allOffers = offerRepository.findByActiveTrue();

        String offerContent = "";
        for(Offer offer : allOffers) {
            offerContent += offer.getTitle() +" "+offer.getDescription() +" Ending on : "+offer.getEndDate();
        }
        for(User user : allUsers) {
            mailService.sendMail(user.getEmail(), "Hi " + user.getName() +"\n Here is the List of all the new Offers.\n",
                    offerContent);

        }

    }
}
