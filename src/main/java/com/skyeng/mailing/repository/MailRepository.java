package com.skyeng.mailing.repository;

import java.util.List;

import com.skyeng.mailing.model.Mail;
import com.skyeng.mailing.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {
    Mail findMailByIdAndPostOffice(Long id, PostOffice postOffice);
    List<Mail> findAllByPostOffice_Index(String index);

    List<Mail> findAllByAddressRecipientAndNameRecipientAndPostOffice_Index(String address, String name, String index);
}
