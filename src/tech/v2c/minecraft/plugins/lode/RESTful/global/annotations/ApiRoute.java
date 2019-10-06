package tech.v2c.minecraft.plugins.lode.RESTful.global.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiRoute {
    public enum HttpMethod{
        GET, POST, PUT, DELETE
    }
    public HttpMethod HttpMethod() default HttpMethod.GET;
    public String Path();
}
