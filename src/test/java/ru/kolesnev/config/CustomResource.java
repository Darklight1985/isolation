package ru.kolesnev.config;

import io.quarkus.test.common.QuarkusTestResource;
import jakarta.enterprise.inject.Stereotype;
import org.eclipse.sisu.bean.LifecycleManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Stereotype
public @interface CustomResource {
}
