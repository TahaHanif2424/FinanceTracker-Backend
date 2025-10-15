package com.FutureConnect.FutureConnect.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
