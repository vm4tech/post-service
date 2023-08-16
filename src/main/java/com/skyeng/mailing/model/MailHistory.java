package com.skyeng.mailing.model;

import com.skyeng.mailing.model.common.enums.StateType;
import com.skyeng.mailing.model.common.extenders.CommonFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = MailHistory.TABLE_NAME)
public class MailHistory extends CommonFields {
    public static final String TABLE_NAME = "skyeng_mail_history";

    @ManyToOne
    @JoinColumn(name = "mail_id")
    private Mail mail;

    @ManyToOne
    @JoinColumn(name = "postoffice_id")
    private PostOffice postOffice;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private StateType stateType;

}
