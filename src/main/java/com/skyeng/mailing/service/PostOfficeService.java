package com.skyeng.mailing.service;

import java.util.List;

import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.repository.MailHistoryRepository;
import com.skyeng.mailing.repository.MailRepository;
import com.skyeng.mailing.repository.PostOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostOfficeService {
    private PostOfficeRepository postOfficeRepository;

    private MailHistoryRepository mailHistoryRepository;
    @Autowired
    public void setPostOfficeRepository(PostOfficeRepository postOfficeRepository) {
        this.postOfficeRepository = postOfficeRepository;
    }

    @Autowired
    public void setMailHistoryRepository(MailHistoryRepository mailHistoryRepository) {
        this.mailHistoryRepository = mailHistoryRepository;
    }

    public List<PostOffice> getAllPostOffices(){
        return postOfficeRepository.findAll();
    }
}
