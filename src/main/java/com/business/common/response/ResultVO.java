package com.business.common.response;

import com.business.common.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: GaoLu
 * @Date: 2023-10-18 16:06
 * @Description: 统一响应体类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "返回", description = "返回对象")
public class ResultVO<T> {

    @Schema(title = "状态码", description = "状态码", name = "code")
    private int code;

    @Schema(title = "响应信息", description = "响应信息，用来说明响应情况", name = "msg")
    private String msg;

    @Schema(title = "数据", description = "响应的具体数据", name = "data")
    private T data;

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public ResultVO(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static <T> ResultVO<T> success(T data){
        return new ResultVO<>(ResultCode.SUCCESS, data);
    }

    public static <T> ResultVO<T> success(String msg, T data){
        return new ResultVO<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResultVO<T> error(String msg){
        return new ResultVO<>(ResultCode.ERROR.getCode(), msg, null);
    }

    public static <T> ResultVO<T> error(ResultCode resultCode){
        return new ResultVO<>(resultCode);
    }

    public static <T> ResultVO<T> error(Integer code, String msg){
        return new ResultVO<>(code, msg, null);
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';

    }


}
