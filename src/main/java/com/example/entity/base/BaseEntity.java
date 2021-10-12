package com.example.entity.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * The class Base entity.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-09-17
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@FieldNameConstants
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * The Created time.
     */
    @Column(name = "created_time", nullable = false)
    private OffsetDateTime createdTime;

    /**
     * The Updated time.
     */
    @Column(name = "updated_time")
    private OffsetDateTime updatedTime;

    /**
     * Pre create.
     */
    @PrePersist
    private void prePersist() {
        this.createdTime = OffsetDateTime.now(ZoneOffset.UTC);
    }

    /**
     * Pre update.
     */
    @PreUpdate
    private void preUpdate() {
        this.updatedTime = OffsetDateTime.now(ZoneOffset.UTC);
    }
}