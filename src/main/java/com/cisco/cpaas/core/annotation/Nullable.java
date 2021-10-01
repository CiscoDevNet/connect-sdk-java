package com.cisco.cpaas.core.annotation;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierNickname;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static javax.annotation.meta.When.MAYBE;

/**
 * Annotation that can be applied to various methods to note that they can return a null value.
 * Useful for static analysis tools. Essentially this wraps the javax annotation so another
 * implementation can be used later on if needed since JSR305 is no longer maintained.
 */
@Target(value = {METHOD, PARAMETER, FIELD})
@Retention(value = CLASS)
@Nonnull(when = MAYBE)
@TypeQualifierNickname
public @interface Nullable {}
