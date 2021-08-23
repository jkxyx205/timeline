package com.rick.story.dao.impl;

import com.rick.story.dao.TagDAO;
import com.rick.story.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-18 16:19:00
 */
@Repository
@RequiredArgsConstructor
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT_SQL = "SELECT id, title FROM tag";

    @Override
    public List<Tag> selectAll() {
        return jdbcTemplate.query(SELECT_SQL,
                BeanPropertyRowMapper.newInstance(Tag.class));
    }
}
