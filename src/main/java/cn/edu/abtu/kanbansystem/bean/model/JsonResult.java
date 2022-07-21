package cn.edu.abtu.kanbansystem.bean.model;

import lombok.*;

/**
 * @author Richard
 * @date 2022/1/29 12:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class JsonResult<T> {

    @NonNull
    private Integer code;


    private T content;


    private String msg;

    public JsonResult(@NonNull Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
