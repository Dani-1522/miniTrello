package com.practica.miniTrello.DTOs;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public String getUsername() {
        return username;
    }



    public String getEmail() {
        return email;
    }



    public String getPassword() {
        return password;
    }


}
