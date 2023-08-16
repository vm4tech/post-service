package com.skyeng.mailing.controller;

import java.util.List;

import com.skyeng.mailing.controller.dto.RegistrationDTO;
import com.skyeng.mailing.exception.ValidationException;
import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.repository.MailRepository;
import com.skyeng.mailing.service.MailService;
import com.skyeng.mailing.service.PostOfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/create")
    public PostOffice createOffice(@RequestBody PostOffice postOffice){
        return postOfficeService.create(postOffice);
    }

    @PatchMapping("/update")
    public PostOffice updateOffice(@RequestBody PostOffice postOffice){
        return postOfficeService.update(postOffice);
    }

    @PostMapping("/registerMail")
    public Mail registerMail(@RequestBody @Valid RegistrationDTO registrationDTO, Errors errors){
        if (errors.hasErrors())
            throw new ValidationException(errors.getFieldErrors());
        return postOfficeService.registration(registrationDTO);
    }
}
