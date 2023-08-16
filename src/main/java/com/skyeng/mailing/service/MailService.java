package com.skyeng.mailing.service;

import java.util.List;

import com.skyeng.mailing.controller.dto.RecipientDTO;
import com.skyeng.mailing.controller.dto.RegistrationDTO;
import com.skyeng.mailing.exception.BadRequestException;
import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.MailHistory;
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
    public void setMailHistoryRepository(MailHistoryRepository mailHistoryRepository) {
        this.mailHistoryRepository = mailHistoryRepository;
    }

    @Autowired
    public void setMailRepository(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
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

    public Mail getMailById(Long id){
        Mail mail = mailRepository.findById(id).orElse(null);
        if (mail == null)
            throw new BadRequestException("cannot find mail by id: " + id);
        return mail;
    }
    public Mail getMailByIdAndOffice(Long id, PostOffice office){
        Mail mail = mailRepository.findMailByIdAndPostOffice(id, office);
        if (mail == null)
            throw new BadRequestException(String.format("cannot find mail by id: %d and office.index: %s", id, office.getIndex()));
        return mail;
    }

    public Mail moveMailToOffice(Long mailId, PostOffice oldPostOffice, PostOffice newPostOffice){
        Mail mail = getMailByIdAndOffice(mailId, oldPostOffice);
        if (oldPostOffice.getIndex().equals(mail.getIndexRecipient())){
            throw new BadRequestException(String.format("Посылка вас ждет на пункте выдачи, по адресу: %s, индекс: %s", oldPostOffice.getPostName(), oldPostOffice.getIndex()));
        }
        return moveToOffice(mail, newPostOffice);
    }
    public Mail moveToOffice(Mail mail, PostOffice newPostOffice){
        if (newPostOffice.getIndex().equals(mail.getIndexRecipient()))
            mail.setStateType(StateType.AWAITNG_RECEIPT);
        else
            mail.setStateType(StateType.IN_PROCESSING);
        mail.setPostOffice(newPostOffice);
        return mail;
    }

    public List<Mail> getAllMailByOfficeIndex(String index){
        return mailRepository.findAllByPostOffice_Index(index);
    }

    public List<MailHistory> getMailHistory(Long id){
        return mailHistoryRepository.findAllByMail_Id(id);
    }

    public List<Mail> getMailsByRecipient(RecipientDTO recipientDTO){
        List<Mail> mails = mailRepository.findAllByAddressRecipientAndNameRecipientAndPostOffice_Index(recipientDTO.getAddress(), recipientDTO.getName(), recipientDTO.getOfficeIndex());
        if (mails.isEmpty())
            throw new BadRequestException("Для вас нет сообщений по индексу: " + recipientDTO.getOfficeIndex());
        mails.forEach(mail -> mail.setStateType(StateType.RECEIVED));
        return mails;
    }
}
