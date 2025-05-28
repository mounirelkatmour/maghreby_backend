package com.maghreby.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String bio;
    private String birthDate;
    private String country;
    private String city;
    private String phoneNumber;
    private String occupation;
    private String email;
    private String profilImg;
}