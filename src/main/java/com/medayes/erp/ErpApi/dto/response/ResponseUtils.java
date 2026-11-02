package com.medayes.erp.ErpApi.dto.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseUtils {

    public static <T> APIResponse<T> success(String message, List<T> data)
    {
         return  APIResponse.<T>builder()
                         .statusCode(HttpStatus.OK.value())
                          .status(true)
                           .message(message)
                           .data(data)
                           .time(LocalDateTime.now())
                            .build();
    }

    public static <T> APIResponse<T> error(String message, int statusCode)
    {
        return  APIResponse.<T>builder()
                .statusCode(statusCode)
                .status(false)
                .message(message)
                .data(null)
                .time(LocalDateTime.now()).

                build();
    }
}
