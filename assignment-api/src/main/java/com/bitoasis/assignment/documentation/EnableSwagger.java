package com.bitoasis.assignment.documentation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Import(SwaggerConfiguration.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @ interface EnableSwagger {
}
