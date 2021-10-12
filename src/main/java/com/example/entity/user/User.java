package com.example.entity.user;

import com.example.common.enums.Authority;
import com.example.common.enums.Language;
import com.example.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * The class User.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-09-17
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@Table(name = "tbl_user")
@Entity
public class User extends BaseEntity implements Serializable {

    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The Username.
     */
    @Column(name = "username", length = 15, nullable = false)
    private String username;

    /**
     * The Password.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The Authority.
     */
    @Column(name = "authority", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    /**
     * The First name.
     */
    @Column(name = "first_name", length = 15)
    private String firstName;

    /**
     * The Last name.
     */
    @Column(name = "last_name", length = 15)
    private String lastName;

    /**
     * The Address.
     */
    @Column(name = "address", length = 250)
    private String address;

    /**
     * The Phone.
     */
    @Column(name = "phone", length = 15)
    private String phone;

    /**
     * The Mail.
     */
    @Column(name = "mail", length = 50)
    private String mail;

    /**
     * The Language.
     */
    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    /**
     * The Is deleted.
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    /**
     * The Last login time.
     */
    @Column(name = "last_login_time")
    private OffsetDateTime lastLoginTime;

    /**
     * Gets is deleted.
     *
     * @return the boolean
     */
    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    /**
     * Sets is deleted.
     *
     * @param isDeleted is deleted
     */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
