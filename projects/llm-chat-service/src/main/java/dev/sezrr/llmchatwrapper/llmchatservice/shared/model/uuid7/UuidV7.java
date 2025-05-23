package dev.sezrr.llmchatwrapper.llmchatservice.shared.model.uuid7;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IdGeneratorType(UuidV7Generator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UuidV7 {
}