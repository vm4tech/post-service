package com.skyeng.mailing.model;

import java.util.List;

import com.skyeng.mailing.model.common.extenders.CommonFields;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = PostOffice.TABLE_NAME)
public class PostOffice extends CommonFields {
    public static final String TABLE_NAME = "skyeng_postoffice";

    @Column(name = "index", unique = true)
    private String index;

    @Column(name = "postName")
    private String postName;

    @OneToMany(mappedBy = "postOffice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mail> mailList;

}
