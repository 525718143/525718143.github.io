package ptumall.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableOpenApi
public class SwaggerConfig {//设定swaggerUI界面 类似postman 测试更简易化 可以忽略此类
    @Value("${swagger.enabled}")
    Boolean swaggerEnabled;
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启swagger
                .enable(swaggerEnabled)
                .select()
                // 过滤条件，扫描指定路径下的文件
                .apis(RequestHandlerSelectors.basePackage("ptumall.controller"))
                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Arrays.asList(tokenContext()));
    }

    private ApiInfo apiInfo() {
        /*作者信息*/
        Contact contact = new Contact("Edward", "https://ptu.com", "525718143@qq.com");
        return new ApiInfo(
                "ptu mall",
                "ptu mall 测试接口文档",
                "v1.0",
                "https://ptu.com",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }

    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey("token", "token", "header");
    }

    private SecurityContext tokenContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(SecurityReference.builder()
                        .scopes(new AuthorizationScope[0])
                        .reference("token")
                        .build()))
                .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                .build();
    }
}
