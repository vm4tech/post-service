package com.skyeng.mailing.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RecipientDTO {
    @NotBlank
    private String officeIndex;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
}
