package com.project_nikhil.SecureOfferHub.batch;

import com.project_nikhil.SecureOfferHub.model.Offer;
import com.project_nikhil.SecureOfferHub.model.User;
import com.project_nikhil.SecureOfferHub.repository.OfferRepository;
import com.project_nikhil.SecureOfferHub.repository.UserRepository;
import com.project_nikhil.SecureOfferHub.service.MailService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmailItemWriter  implements ItemWriter<String> {
    private final MailService mailService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OfferRepository offerRepository;

    public EmailItemWriter(MailService mailService) {
        this.mailService = mailService;
    }
    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        for (String content : chunk) {
            // Here, you would fetch the email address from the user model
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
}
