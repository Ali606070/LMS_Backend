package com.library.task.dtos;

import com.library.task.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class SignUpRequestDto {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    @ValidPassword
    private String password;
    @NotBlank(message = "Role is required")
    private String roleName;

    public SignUpRequestDto(String email, String password, String roleName) {
        this.email = email;
        this.password = password;
        this.roleName = roleName;
    }

    public SignUpRequestDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return roleName;
    }

    public void setRole(String role) {
        this.roleName = role;
    }
}
