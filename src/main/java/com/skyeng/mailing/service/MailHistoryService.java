package com.skyeng.mailing.service;

import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.MailHistory;
import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.model.common.enums.StateType;
import com.skyeng.mailing.repository.MailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailHistoryService {
    private MailHistoryRepository mailHistoryRepository;

    @Autowired
    public void setMailHistoryRepository(MailHistoryRepository mailHistoryRepository) {
        this.mailHistoryRepository = mailHistoryRepository;
    }

    public void createHistoryPoint(Mail mail, PostOffice postOffice){
        MailHistory history = new MailHistory();
        history.setMail(mail);
        history.setPostOffice(postOffice);
        history.setStateType(mail.getStateType());
        mailHistoryRepository.save(history);
    }
}
