package org.example.back_end.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class ResponseUtil {
    private int code;
    private String message;
    private Object data;
}
