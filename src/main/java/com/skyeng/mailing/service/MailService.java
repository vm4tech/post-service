package com.skyeng.mailing.service;

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
}
