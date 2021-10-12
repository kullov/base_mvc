package com.example.common.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The class Response.
 *
 * @param <T> the type parameter
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    /**
     * The constant CODE_SUCCESS.
     */
    public static final int CODE_SUCCESS = 200;

    /**
     * The constant CODE_BUSINESS.
     */
    public static final int CODE_BUSINESS = 409;

    /**
     * The constant CODE_VALIDATION.
     */
    public static final int CODE_VALIDATION = 400;

    /**
     * The constant CODE_UNAUTHORIZED.
     */
    public static final int CODE_UNAUTHORIZED = 401;

    /**
     * The Code.
     */
    private int code;

    /**
     * The Data.
     */
    private T data;

    /**
     * Ok response.
     *
     * @param <T> the type parameter
     * @return the response
     */
    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.code = CODE_SUCCESS;
        return response;
    }

    /**
     * Code response.
     *
     * @param code the code
     * @return the response
     */
    public Response<T> code(int code) {
        this.code = code;
        return this;
    }

    /**
     * Build response.
     *
     * @param <T> the type parameter
     * @return the response
     */
    public static <T> Response<T> build() {
        return new Response<T>();
    }

    /**
     * Data response.
     *
     * @param data the data
     * @return the response
     */
    public Response<T> data(T data) {
        this.data = data;
        return this;
    }
}