package com.example.itss.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @NotBlank(message = "Fullname is required")
    @Size(max = 50, message = "Fullname must be less than 50 characters")
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10,11}$", message = "Phone number must be 10 or 11 digits")
    private String phone;

    public @NotBlank(message = "Fullname is required") @Size(max = 50, message = "Fullname must be less than 50 characters") String getFullname() {
        return fullname;
    }

    public void setFullname(@NotBlank(message = "Fullname is required") @Size(max = 50, message = "Fullname must be less than 50 characters") String fullname) {
        this.fullname = fullname;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Username is required") @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\d{10,11}$", message = "Phone number must be 10 or 11 digits") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\d{10,11}$", message = "Phone number must be 10 or 11 digits") String phone) {
        this.phone = phone;
    }
}