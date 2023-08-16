package com.skyeng.mailing.controller;

import java.util.List;

import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.repository.MailRepository;
import com.skyeng.mailing.service.MailService;
import com.skyeng.mailing.service.PostOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/postoffice", produces="application/json")
public class PostOfficeController {

    private PostOfficeService postOfficeService;

    @Autowired
    public void setPostOfficeService(PostOfficeService postOfficeService) {
        this.postOfficeService = postOfficeService;
    }

    @GetMapping("/getAllOffices")
    public List<PostOffice> getAllOffices(){
        return postOfficeService.getAllPostOffices();
    }

//    @PostMapping("/create")


}
