package com.cydeo.fintracker.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstname;
    private String lastname;
    private String phone;
    private RoleDto role;
    private CompanyDto company;
    private boolean isOnlyAdmin; // Note from User Story: Should be true if this user is only admin of any company
                                 // TO BE DONE DURING SECURITY

}
