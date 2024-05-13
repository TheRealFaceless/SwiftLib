package dev.faceless.swiftlib.lib.util.interfaces;

import java.sql.SQLException;

@FunctionalInterface
public interface QuadConsumer<T, U, V, W> {
    void accept(T t, U u, V v, W w) throws SQLException;
}

