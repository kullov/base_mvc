package com.example.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * The class Sign in request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    /**
     * The Username.
     */
    @NotBlank(message = "message.field_not_blank")
    private String username;

    /**
     * The Password.
     */
    @NotBlank(message = "message.field_not_blank")
    private String password;
}