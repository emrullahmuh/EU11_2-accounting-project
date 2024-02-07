package com.cydeo.fintracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Email is required field.")
    @NotNull(message = "Username is a required field.")
    @Email(message = "A user with this email already exists. Please try with different email.")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password is a required field.")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}", message = "Password should be at least 4 characters long and needs to contain 1 capital letter, 1 small letter and 1 special character or number.")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "ConfirmPassword is a required field.")
    private String confirmPassword;

    @NotBlank(message = "First Name is required field.")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters long.")
    private String firstname;

    @NotBlank(message = "Last Name is required field.")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters long.")
    private String lastname;

    @Pattern(regexp = "^\\+\\d{1,4}\\s\\(\\d{1,}\\)\\s\\d{1,}-\\d{1,}$", message = "Phone Number is required field and may be in any valid phone number format. Ex: +1 (222) 333-4444")
    private String phone;

    @NotNull(message = "Please select a Role.")
    private RoleDto role;

    @NotNull(message = "Please select a Customer.")
    private CompanyDto company;
    private boolean enabled;

    private boolean isOnlyAdmin; // Note from User Story: Should be true if this user is only admin of any company


    public void setPassword(String password) {
        this.password = password;
        checkConfirmPassword();
    }

    private void checkConfirmPassword() {
        if (this.password == null || this.confirmPassword == null) {
            return;
        } else if (!this.password.equals(this.confirmPassword)) {
            this.confirmPassword = null;
        }
    }
}
