package com.mob.casestudy.digitalbanking.exceptionresponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExceptionResponse {
    private String errorCode;
    private String errorDescription;
}
