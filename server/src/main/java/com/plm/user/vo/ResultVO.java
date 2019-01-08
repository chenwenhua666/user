package com.plm.user.vo;

import lombok.Data;

/**
 * chenwenhua
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
