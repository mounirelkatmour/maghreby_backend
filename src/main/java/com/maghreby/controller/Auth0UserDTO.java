package com.maghreby.controller;

import lombok.Data;

@Data
public class Auth0UserDTO {
    private String email;
    private String name;
    private String picture;
    private String sub; // Auth0 user ID
}
