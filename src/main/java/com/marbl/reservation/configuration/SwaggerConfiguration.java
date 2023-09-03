//package com.tenpo.reservation.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import static springfox.documentation.builders.PathSelectors.any;
//
//@Configuration
//public class SwaggerConfiguration {
//
//    @Bean
//    public Docket petApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("full-petstore-api")
//                .apiInfo(apiInfo())
//                .select()
//                .paths(any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("API")
//                .description("Service API")
//                .termsOfServiceUrl("http://springfox.io")
//                .contact(new Contact("springfox", "", ""))
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
//                .version("2.0")
//                .build();
//    }
//}
