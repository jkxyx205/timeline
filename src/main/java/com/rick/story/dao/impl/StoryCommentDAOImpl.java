package com.rick.story.dao.impl;

import com.rick.common.dao.BaseDAO;
import com.rick.story.dao.StoryCommentDAO;
import com.rick.story.entity.StoryComment;
import com.rick.util.SQLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:28:00
 */
@Repository
@RequiredArgsConstructor
public class StoryCommentDAOImpl implements BaseDAO<StoryComment>, StoryCommentDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String INSERT_SQL = "INSERT INTO story_comment(text, story_id, create_time) VALUES (?, ?, now())";

    private final static String DELETE_SQL = "DELETE FROM story_comment WHERE story_id = ?";

    private final static String DELETE_SQL_BY_ID = "DELETE FROM story_comment WHERE id = ?";

    private final static String SELECT_SQL = "SELECT id, text, story_id, create_time FROM story_comment ";

    private final static String SELECT_BY_ID_SQL = SELECT_SQL + "WHERE story_id = ?";

    private final static String SELECT_BY_IDS_SQL = SELECT_SQL + "WHERE story_id in ";

    @Override
    public long insert(StoryComment storyComment) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_SQL, new String[] { "id"});
            ps.setString(1, storyComment.getText());
            ps.setLong(2, storyComment.getStoryId());
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void deleteById(long id) {
        jdbcTemplate.update(DELETE_SQL_BY_ID, id);
    }

    @Override
    public StoryComment selectById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void batchInsert(Collection<StoryComment> storyCommentList) {
        if (CollectionUtils.isEmpty(storyCommentList)) {
            return;
        }

        List<Object[]> params = new ArrayList<>(storyCommentList.size());
        for (StoryComment StoryComment : storyCommentList) {
            params.add(new Object[] { StoryComment.getText(), StoryComment.getStoryId() });
        }

        jdbcTemplate.batchUpdate(INSERT_SQL, params);
    }

    @Override
    public void deleteByStoryId(long storyId) {
        jdbcTemplate.update(DELETE_SQL, storyId);
    }

    @Override
    public List<StoryComment> selectByStoryId(long storyId) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL,
                BeanPropertyRowMapper.newInstance(StoryComment.class), storyId);
    }

    @Override
    public List<StoryComment> selectByStoryIds(Collection<Long> storyIds) {
        return jdbcTemplate.query(SELECT_BY_IDS_SQL + SQLUtils.formatInSQLPlaceHolder(storyIds.size()),
                BeanPropertyRowMapper.newInstance(StoryComment.class), storyIds.toArray());
    }
}
