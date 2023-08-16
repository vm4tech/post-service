package com.skyeng.mailing.controller.dto;

import com.skyeng.mailing.model.common.enums.MailType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RegistrationDTO {
    @NotNull
    private MailType mailType;
    @NotBlank
    private String indexRecipient;
    @NotBlank
    private String addressRecipient;
    @NotBlank
    private String nameRecipient;
    @NotBlank
    private String indexOffice;
}
