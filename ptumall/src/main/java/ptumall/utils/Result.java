package ptumall.utils;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    boolean success;

    public Result() {
        this.code = 200;
        this.success = true;
    }

    public Result(T data) {
        this.code = 200;
        this.data = data;
        this.success = true;
    }

    public Result(ResultCodeEnum resultCodeEnum){
        this.success = false;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    public Result(ResultCodeEnum resultCodeEnum,String message){
        this.success = false;
        this.code = resultCodeEnum.getCode();
        this.message = message;
    }

    public static<T> Result<T> success(){
        return  new Result<>();
    }
    public static<T> Result<T> success(T data){
        return new Result<>(data);
    }
    public static<T> Result<T> success(ResultCodeEnum resultCodeEnum){
        return new Result<>(resultCodeEnum);

    }
    public static<T> Result<T> success(ResultCodeEnum resultCodeEnum,String message){
        return new Result<>(resultCodeEnum,message);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static<T> Result<T> failure(ResultCodeEnum resultCode){
        return  new Result<T>(resultCode);
    }


    public static<T> Result<T> failure(ResultCodeEnum resultCode,String message){
        return  new Result<T>(resultCode,message);
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }
}
