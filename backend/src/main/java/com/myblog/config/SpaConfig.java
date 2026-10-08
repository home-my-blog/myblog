package com.myblog.config;

import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/**
 * 배포 이미지에서는 화면 빌드 결과를 classpath:/static 에 넣어 서버 하나로 같이 내보낸다.
 * 없는 경로(/blogs/1 같은 화면 주소)는 index.html 을 돌려줘 화면 라우터가 처리하게 한다. /api 는 제외.
 */
@Configuration
public class SpaConfig implements WebMvcConfigurer {
    private static final Resource INDEX = new ClassPathResource("static/index.html");

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String path, Resource location) throws IOException {
                        Resource found = location.createRelative(path);
                        if (found.exists() && found.isReadable()) {
                            return found;
                        }
                        if (path.startsWith("api/") || !INDEX.exists()) {
                            return null;
                        }
                        return INDEX;
                    }
                });
    }
}
