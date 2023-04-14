package com.example.whitecommunity.result;

public class Result<T> {
    private int code;
    private String message;
    T data;

    public Result() {
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "成功", data);
    }

    public static <T> Result<T> error(String message) {
        return new Result<>(1, message);
    }

    public static <T> Result<T> forbidden() {
        return new Result<>(2, "没有权限");
    }
}
