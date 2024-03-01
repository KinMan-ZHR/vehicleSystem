package com.scuse.volunteerhub.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    private int code; // 400: fail, 200: success
    private String message;
    private Object coredata;

    public static Result success(Object data) {
        return new Result(200, "success", data);
    }

    public static Result success(String msg, Object data) {
        return new Result(200, msg, data);
    }

    public static Result fail(String msg) {
        return new Result(401, msg, null);
    }

    private Result(int code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.coredata = data;
    }
}
