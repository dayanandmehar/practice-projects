package org.example.userauthservice_feb2026.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUpdateRequestDto {
    private String name;
    private String phoneNumber;
}
