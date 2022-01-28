package com.mob.casestudy.digitalbanking.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class RegexValues {

    @Value("${regex.phone}")
    public String phoneRegex;

    @Value("${regex.email}")
    public String emailRegex;

}
