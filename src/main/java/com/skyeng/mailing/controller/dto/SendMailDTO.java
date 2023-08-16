package com.skyeng.mailing.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SendMailDTO {
    @NotNull
    private Long mailId;
    @NotBlank
    private String fromIndex;
    @NotBlank
    private String toIndex;

//    public void setMailId(Integer mailId) {
//        this.mailId = Long.valueOf(mailId);
//    }
//    public void setMailId(Long mailId) {
//        this.mailId = mailId;
//    }

}
