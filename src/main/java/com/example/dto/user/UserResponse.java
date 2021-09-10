package com.example.dto.user;

import com.example.entity.user.User;
import lombok.Builder;
import lombok.Data;

/**
 * The type User response.
 */
@Data
@Builder
public class UserResponse {
    /**
     * The Id.
     */
    private Long id;
    /**
     * The Name.
     */
    private String name;
    /**
     * The Address.
     */
    private String address;
    /**
     * The Phone.
     */
    private String phone;

    /**
     * Convert from entity user response.
     *
     * @param e the e
     * @return the user response
     */
    public static UserResponse convertFromEntity(User e) {
        return UserResponse.builder()
                .id(e.getId())
                .name(e.getName())
                .address(e.getAddress())
                .phone(e.getPhone())
                .build();
    }
}
