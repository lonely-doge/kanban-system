package cn.edu.abtu.kanbansystem.enums;

import java.util.Arrays;

/**
 * @author RichardDev
 * @date 2022/5/15 13:34
 */
public enum FileType {

    /**
     * 头像
     */
    AVATAR("AVATAR"),
    /**
     * 背景图片
     */
    BACKGROUND("BACKGROUND"),
    /**
     * 附件
     */
    ATTACHMENTS("ATTACHMENT");

    private final String flag;

    FileType(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public static Boolean hasType(String type) {
        return Arrays.stream(FileType.values()).filter(item -> item.flag.equals(type)).count() == 1;
    }


}
