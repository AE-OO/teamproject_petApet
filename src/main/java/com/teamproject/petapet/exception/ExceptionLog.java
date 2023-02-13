package com.teamproject.petapet.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionLog {
    private Integer code;
    private String message;
}
