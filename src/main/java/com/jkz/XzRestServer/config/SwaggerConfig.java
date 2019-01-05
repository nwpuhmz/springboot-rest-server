package com.jkz.XzRestServer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by scuhmz on 2017/9/19.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jkz.XzRestServer.controller"))
                .paths(PathSelectors.any())

                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("卸载管理系统接口文档")
                .description("rest api doc")
                .contact("郝明哲")
                .version("1.0")
                .build();
    }
}