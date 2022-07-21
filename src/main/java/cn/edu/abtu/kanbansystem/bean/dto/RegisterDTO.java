package cn.edu.abtu.kanbansystem.bean.dto;

import lombok.Data;

/**
 * @author RichardDev
 * @date 2022/5/15 22:15
 */
@Data
public class RegisterDTO {

    private String account;

    private String password;

    private String nickName;

    private String teamId;

    private String gender;

    private String type;
}
