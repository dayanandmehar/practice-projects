package org.example.userauthservice_feb2026.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequestDto {
    private String currentPassword;
    private String newPassword;
}
