package com.taskManger.repositories;

import java.util.function.BiFunction;

@FunctionalInterface
public interface SelectionCondition<T> extends BiFunction<Boolean, T, T> {

}
