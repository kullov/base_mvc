package com.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * The type User request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    /**
     * The Id.
     */
    private Long id;
    /**
     * The Name.
     */
    @NotBlank(message = "message.field_not_blank")
    private String name;
    /**
     * The Address.
     */
    private String address;
    /**
     * The Phone.
     */
    private String phone;
}
