package com.skyeng.mailing.controller;

import java.util.List;

import com.skyeng.mailing.model.MailHistory;
import com.skyeng.mailing.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail", produces="application/json")
public class MailController {
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/history/{id}")
    public List<MailHistory> getMailHistory(@PathVariable Long id){
        return mailService.getMailHistory(id);
    }

}
