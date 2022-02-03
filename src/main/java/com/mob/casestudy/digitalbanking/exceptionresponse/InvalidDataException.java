package com.mob.casestudy.digitalbanking.exceptionresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidDataException extends RuntimeException {
   private final String code;
   private final String description;
}
