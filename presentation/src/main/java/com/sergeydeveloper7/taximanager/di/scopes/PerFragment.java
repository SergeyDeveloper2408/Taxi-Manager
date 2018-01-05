package com.sergeydeveloper7.taximanager.di.scopes;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by serg on 02.01.18.
 */

@Scope
@Retention(RUNTIME)
public @interface PerFragment {}