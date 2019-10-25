package com.shop.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 */
@Getter
@Setter
@Slf4j
public class ResponseBase {
    private Integer rtnCode;
    private String msg;
    private Object data;

    public ResponseBase() {

    }

    ResponseBase(Integer rtnCode, String msg, Object data) {
        this.rtnCode = rtnCode;
        this.msg = msg;
        this.data = data;
    }

    public static void main(String[] args) {
        log.info("lommbok");
    }
}
