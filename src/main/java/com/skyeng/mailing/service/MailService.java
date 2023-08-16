package com.skyeng.mailing.service;

import com.skyeng.mailing.controller.dto.RegistrationDTO;
import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.model.common.enums.StateType;
import com.skyeng.mailing.repository.MailHistoryRepository;
import com.skyeng.mailing.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private MailRepository mailRepository;
    private MailHistoryRepository mailHistoryRepository;

    @Autowired
    public void setMailRepository(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }
    @Autowired
    public void setMailHistoryRepository(MailHistoryRepository mailHistoryRepository) {
        this.mailHistoryRepository = mailHistoryRepository;
    }

    public Mail createMail(RegistrationDTO registrationDTO, PostOffice postOffice){
        Mail mail = new Mail();
        mail.setMailType(registrationDTO.getMailType());
        mail.setIndexRecipient(registrationDTO.getIndexRecipient());
        mail.setAddressRecipient(registrationDTO.getAddressRecipient());
        mail.setNameRecipient(registrationDTO.getNameRecipient());
        mail.setStateType(StateType.CREATED);
        mail.setPostOffice(postOffice);
        return mailRepository.save(mail);
    }
}
