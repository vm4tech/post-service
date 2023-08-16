package com.skyeng.mailing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skyeng.mailing.model.common.enums.MailType;
import com.skyeng.mailing.model.common.enums.StateType;
import com.skyeng.mailing.model.common.extenders.CommonFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Модель почтового отправления
 * @author vm4tech
 * @since 16.08.2023
 */
@Data
@Entity
@Table(name = Mail.TABLE_NAME)
public class Mail extends CommonFields {
    public static final String TABLE_NAME = "skyeng_mail";

    @Enumerated(EnumType.STRING)
    @Column(name = "mailType")
    private MailType mailType;

    @Column(name = "indexRecipient")
    private String indexRecipient;

    @Column(name = "addressRecipient")
    private String addressRecipient;

    @Column(name = "nameRecipient")
    private String nameRecipient;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateType stateType;

    @JsonIgnoreProperties(value = "mailList")
    @ManyToOne
    @JoinColumn(name = "postoffice_id")
    private PostOffice postOffice;
}
