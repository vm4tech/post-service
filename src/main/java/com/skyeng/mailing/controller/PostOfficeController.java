package com.skyeng.mailing.controller;

import java.util.List;

import com.skyeng.mailing.controller.dto.RecipientDTO;
import com.skyeng.mailing.controller.dto.RegistrationDTO;
import com.skyeng.mailing.controller.dto.SendMailDTO;
import com.skyeng.mailing.exception.ValidationException;
import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.PostOffice;
import com.skyeng.mailing.service.PostOfficeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/all")
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

    @PostMapping("/register_mail")
    public Mail registerMail(@RequestBody @Valid RegistrationDTO registrationDTO, Errors errors){
        if (errors.hasErrors())
            throw new ValidationException(errors.getFieldErrors());
        return postOfficeService.registration(registrationDTO);
    }

    @PostMapping("/send_mail")
    public Mail sendMail(@RequestBody @Valid SendMailDTO sendMailDTO, Errors errors){
        if (errors.hasErrors())
            throw new ValidationException(errors.getFieldErrors());
        return postOfficeService.sendMailTo(sendMailDTO);
    }

    @GetMapping("/all_mails/{index}")
    public List<Mail> getOfficeMailsByIndex(@PathVariable String index){
        return postOfficeService.findAllMailByOfficeIndex(index);
    }

    @PostMapping("/get_mail_by_recipient")
    public List<Mail> getMailsByUser(@RequestBody @Valid RecipientDTO recipientDTO, Errors errors){
        if (errors.hasErrors())
            throw new ValidationException(errors.getFieldErrors());
        return postOfficeService.getMailsByRecipient(recipientDTO);
    }

}
