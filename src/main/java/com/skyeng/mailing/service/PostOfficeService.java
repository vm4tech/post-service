package com.skyeng.mailing.service;

import java.util.List;

import com.skyeng.mailing.controller.dto.RecipientDTO;
import com.skyeng.mailing.controller.dto.RegistrationDTO;
import com.skyeng.mailing.controller.dto.SendMailDTO;
import com.skyeng.mailing.exception.BadRequestException;
import com.skyeng.mailing.exception.ValidationException;
import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.MailHistory;
import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.model.common.enums.StateType;
import com.skyeng.mailing.repository.MailHistoryRepository;
import com.skyeng.mailing.repository.MailRepository;
import com.skyeng.mailing.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostOfficeService {
    private PostOfficeRepository postOfficeRepository;
    private MailHistoryService mailHistoryService;
    private MailService mailService;

    @Autowired
    public void setMailHistoryService(MailHistoryService mailHistoryService) {
        this.mailHistoryService = mailHistoryService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
    @Autowired
    public void setPostOfficeRepository(PostOfficeRepository postOfficeRepository) {
        this.postOfficeRepository = postOfficeRepository;
    }

    public List<PostOffice> getAllPostOffices(){
        return postOfficeRepository.findAll();
    }

    @Transactional
    public PostOffice create(PostOffice postOffice){
        if (postOffice.getId() != null)
            throw new ValidationException("id", "object id should be null");
        try {
            return postOfficeRepository.save(postOffice);
        } catch (DataAccessException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Transactional
    public PostOffice update(PostOffice postOffice){
        if (postOffice.getId() == null)
            throw new ValidationException("id", "object id cannot be null");
        try {
            return postOfficeRepository.save(postOffice);
        } catch (DataAccessException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Transactional
    public Mail registration(RegistrationDTO registrationDTO){
        PostOffice postOffice = findPostOfficeByIndex(registrationDTO.getIndexOffice());
        Mail mail = mailService.createMail(registrationDTO, postOffice);
        mailHistoryService.createHistoryPoint(mail, postOffice);
        return mail;
    }

    @Transactional
    public Mail sendMailTo(SendMailDTO sendMailDTO){
        PostOffice fromOffice = findPostOfficeByIndex(sendMailDTO.getFromIndex());
        PostOffice toOffice = findPostOfficeByIndex(sendMailDTO.getToIndex());
        Mail mail = mailService.moveMailToOffice(sendMailDTO.getMailId(), fromOffice, toOffice);
        mailHistoryService.createHistoryPoint(mail, toOffice);
        return mail;
    }
    public PostOffice findPostOfficeByIndex(String index){
        PostOffice postOffice = postOfficeRepository.findPostOfficeByIndex(index);
        if (postOffice == null)
            throw new BadRequestException("Cannot find post office with index: " + index);
        return postOffice;
    }

    public List<Mail> findAllMailByOfficeIndex(String index){
        return mailService.getAllMailByOfficeIndex(index);
    }

    @Transactional
    public List<Mail> getMailsByRecipient(RecipientDTO recipientDTO){
        return mailService.getMailsByRecipient(recipientDTO);
    }
}
