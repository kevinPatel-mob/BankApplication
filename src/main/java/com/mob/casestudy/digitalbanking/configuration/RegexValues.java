package com.mob.casestudy.digitalbanking.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.yml")
@Getter
@Setter
public class RegexValues {

    @Value("${regex.phone}")
    public String phoneRegex;

    @Value("${regex.email}")
    public String emailRegex;

}
