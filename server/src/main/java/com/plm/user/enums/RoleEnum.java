package com.plm.user.enums;

import lombok.Getter;

/**
 * @author : cwh
 * 2019/1/3 0003
 * description ：
 */
@Getter
public enum RoleEnum {
    BUYER(1,"买家"),
    SELLER(2,"卖家"),
    ;
    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
