package com.smashrating.auth.config;

import com.smashrating.auth.resolver.AuthUserIdArgumentResolver;
import com.smashrating.user.application.query.UserQueryService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserQueryService userReader;

    public WebConfig(UserQueryService userReader) {
        this.userReader = userReader;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserIdArgumentResolver(userReader));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .exposedHeaders("set-cookie")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
