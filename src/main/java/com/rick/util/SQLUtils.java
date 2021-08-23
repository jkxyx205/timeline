package com.rick.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Rick
 * @createdAt 2021-08-18 16:53:00
 */
@Slf4j
public final class SQLUtils {

    private static final int IN_SIZE = 1000;

    private static final String SQL_PATH_NOT_IN = "NOT IN";

    /**
     * @param jdbcTemplate
     * @param idValues
     * @param tableName
     * @param deletedIdColumn
     */
    public static void deleteByIn(JdbcTemplate jdbcTemplate, List<String> idValues, String tableName, String deletedIdColumn) {
        deleteData(jdbcTemplate, idValues, tableName, deletedIdColumn, "IN");
    }

    /**
     * @param jdbcTemplate
     * @param idValues        数量不能大于IN_SIZE = 1000
     * @param tableName
     * @param deletedIdColumn
     */
    public static void deleteByNotIn(JdbcTemplate jdbcTemplate, List<String> idValues, String tableName, String deletedIdColumn) {
        deleteData(jdbcTemplate, idValues, tableName, deletedIdColumn, SQL_PATH_NOT_IN);
    }

    /**
     * if paramSize == 4 format as (?, ?, ?, ?)
     *
     * @param paramSize
     * @return
     */
    public static String formatInSQLPlaceHolder(int paramSize) {
        if (paramSize > IN_SIZE) {
            throw new RuntimeException("SQL_PATH_NOT_IN in的个数不能超过" + IN_SIZE);
        }

        return "(" + String.join(",", Collections.nCopies(paramSize, "?")) + ")";
    }

    public static <T> List<T> selectList(JdbcTemplate jdbcTemplate, Class<T> clazz, String selectSQL, Object... params) {
        return jdbcTemplate.query(selectSQL,
                BeanPropertyRowMapper.newInstance(clazz), params);
    }

    public static List<Map<String, Object>> selectList(JdbcTemplate jdbcTemplate, String selectSQL, Object... params) {
        return jdbcTemplate.queryForList(selectSQL, params);
    }

    private static void deleteData(JdbcTemplate jdbcTemplate, List<String> idValues, String tableName, String deleteId, String sqlPatch) {
        int size = idValues.size();
        if (SQL_PATH_NOT_IN.equals(sqlPatch) && size > IN_SIZE) {
            throw new RuntimeException("SQL_PATH_NOT_IN in的个数不能超过" + IN_SIZE);
        }

        int count;
        if (size % IN_SIZE == 0) {
            count = size / IN_SIZE;
        } else {
            count = size / IN_SIZE + 1;
        }

        for (int i = 1; i <= count; i++) {
            int lastIndex = (i == count) ? size : i * IN_SIZE;
            List<String> deleteIds = idValues.subList((i - 1) * IN_SIZE, lastIndex);
            // remove old records
            String inSql = String.join(",", Collections.nCopies(deleteIds.size(), "?"));
            String deleteSQL = String.format("DELETE FROM " + tableName + " WHERE " + deleteId + " " + sqlPatch + "(%s)", inSql);
            int updateCount = jdbcTemplate.update(deleteSQL, deleteIds.toArray());
            log.info(">>>>>>>>>>>Deleted count: {}", updateCount);
        }
    }
}
