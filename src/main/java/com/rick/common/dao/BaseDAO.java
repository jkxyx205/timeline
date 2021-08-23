package com.rick.common.dao;

import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:33:00
 */
public interface BaseDAO<T> {

    long insert(T t);

    default void batchInsert(Collection<T> coll) {
        throw new UnsupportedOperationException();
    }

    void deleteById(long id);

    T selectById(long id);

    default List<T> selectByIds(Collection<Long> ids) {
        throw new UnsupportedOperationException();
    };
}
