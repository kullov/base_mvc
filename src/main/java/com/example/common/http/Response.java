package com.example.common.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * The type Response.
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
    public static final Integer CODE_SUCCESS = 200;

    /**
     * The constant CODE_BUSINESS.
     */
    public static final Integer CODE_BUSINESS = 409;

    /**
     * The constant CODE_VALIDATION.
     */
    public static final Integer CODE_VALIDATION = 400;
    /**
     * The Code.
     */
    protected Integer code;
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
