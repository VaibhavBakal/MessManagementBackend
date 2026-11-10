package org.example.service;

import org.example.entity.TiffinRequest;
import org.example.repositiories.TiffinRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiffinSchedulerService {
    @Autowired
    private TiffinRequestRepository tiffinRequestRepository;

    @Autowired
    private EmailService emailService;

    // Runs every 10 minutes (600,000 milliseconds)
    @Scheduled(fixedRate = 240000)
    public void processPendingTiffinRequests() {
        List<TiffinRequest> pendingRequests = tiffinRequestRepository.findBystatus("PENDING");
        for (TiffinRequest request : pendingRequests) {
            request.setStatus("APPROVED");
            tiffinRequestRepository.save(request);

            // Send email confirmation
            emailService.sendTiffinApprovalEmail(
                    request.getUser().getEmailId(),
                    request.getDeliveryAddress()
            );
        }
    }


}
