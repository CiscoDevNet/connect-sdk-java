package com.cisco.cpaas.core.annotation;

import com.cisco.cpaas.core.annotation.Nullable;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Annotation that can applied to package-info types and that lets IDE or other tools do static
 * analysis on the code. The intention is all classes an an annotated package would not return null
 * values unless annotated with the {@link Nullable} annotation.
 */
@Target(value = PACKAGE)
@Retention(value = CLASS)
@Nonnull
@TypeQualifierDefault(value = {METHOD, PARAMETER})
public @interface NonNullApi {}
