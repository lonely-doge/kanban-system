package cn.edu.abtu.kanbansystem.exception;

import lombok.*;

/**
 * 自定义异常类
 *
 * @author Richard
 * @date 2022/1/29 12:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class BaseException extends RuntimeException {

    @NonNull
    private Integer errorCode;

    private String errorMsg;

    @Override
    public String getMessage() {
        return this.errorMsg;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
