package com.insert.ioj.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.insert.ioj.global.feign")
public class FeignConfig {
}
