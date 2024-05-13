package dev.faceless.swiftlib.lib.util.interfaces;

import java.sql.SQLException;

@FunctionalInterface
public interface QuadFunction<T, U, V, W, R> {
    R apply(T t, U u, V v, W w) throws SQLException;
}
