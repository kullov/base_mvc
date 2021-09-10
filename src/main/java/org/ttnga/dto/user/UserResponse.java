package org.ttnga.dto.user;

import lombok.Builder;
import lombok.Data;
import org.ttnga.entity.user.User;

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
