package com.rick.story.dao.impl;

import com.rick.common.dao.BaseDAO;
import com.rick.story.entity.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:28:00
 */
@Repository
@RequiredArgsConstructor
public class StoryDAOImpl implements BaseDAO<Story> {

    private final JdbcTemplate jdbcTemplate;

    private final static String INSERT_SQL = "INSERT INTO story(text, create_time, addr) VALUES (?, now(), ?)";

    private final static String DELETE_SQL = "DELETE FROM story WHERE id = ?";

    private final static String SELECT_SQL = "SELECT id, text, create_time, addr FROM story WHERE id = ?";

    @Override
    public long insert(Story story) {
        Assert.notNull(story.getText(), "story text cannot be null!");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_SQL, new String[] { "id"});
            ps.setString(1, story.getText());
            ps.setString(2, story.getAddr());
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void batchInsert(Collection<Story> coll) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_SQL, id);
    }

    @Override
    public Story selectById(long id) {
        return jdbcTemplate.queryForObject(SELECT_SQL,
                BeanPropertyRowMapper.newInstance(Story.class), id);
    }

    @Override
    public List<Story> selectByIds(Collection<Long> ids) {
        throw new UnsupportedOperationException();
    }

}
