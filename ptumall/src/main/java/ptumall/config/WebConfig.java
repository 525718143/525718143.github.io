package ptumall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptrots())
                .addPathPatterns("/**")//所有接口拦截
                .excludePathPatterns("/user/login","/user/register")//放行登录注册
                .excludePathPatterns("/img/**")
                .excludePathPatterns("/swagger-resources/**","/swagger-ui/**", "/v3/**", "/error");//放行swagger
    }

    @Value("${file.save-path}")
    String filePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //其中img表示访问的前缀。"file:"是文件真实的存储路径
        registry.addResourceHandler("/img/**").addResourceLocations("file:"+filePath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


}
