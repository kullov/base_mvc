package org.ttnga.common.exception;

import lombok.Getter;

/**
 * The type Business exception.
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * The Code.
     */
    final int code;

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     * @param code    the code
     */
    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }
}
