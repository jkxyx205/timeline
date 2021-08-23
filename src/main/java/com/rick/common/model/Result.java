package com.rick.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

/**
 * @author Rick
 * @createdAt 2021-08-17 18:09:00
 */
@Value(staticConstructor = "of")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    private int code;

    private T data;

    private String msg;

    public static Result success() {
        return Result.of(0, null, "OK" );
    }

    public static <T> Result success(T data) {
        return Result.of(0, data, "OK" );
    }

    public static Result fail() {
        return Result.of(-1, null, "FAIL");
    }
}
