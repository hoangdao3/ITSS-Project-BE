package com.example.itss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private String fullname;
    private String email;
    private String username;
    private String phone;

    public UserResponse(String fullname, String email, String username, String phone) {
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }
}
