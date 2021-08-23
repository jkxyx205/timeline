package com.rick.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rick
 * @createdAt 2021-08-18 16:53:00
 */
@Slf4j
public final class DefaultDataUtils {

    public static List defaultEmptyList(List<?> list) {
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public static Set defaultEmptySet(Set<?> set) {
        return set == null ? Collections.EMPTY_SET : set;
    }

    public static Map defaultEmptyMap(Map<?, ?> map) {
        return map == null ? Collections.emptyMap() : map;
    }
}
