package com.wanshun.console.permission.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCodeVo {
    private String keyCode;
    private String authCode;
}
