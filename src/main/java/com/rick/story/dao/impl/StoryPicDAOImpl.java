package com.rick.story.dao.impl;

import com.rick.common.dao.BaseDAO;
import com.rick.story.dao.StoryPicDAO;
import com.rick.story.entity.StoryPic;
import com.rick.util.SQLUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:28:00
 */
@Repository
@RequiredArgsConstructor
public class StoryPicDAOImpl implements BaseDAO<StoryPic>, StoryPicDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String INSERT_SQL = "INSERT INTO story_pic(url, story_id) VALUES (?, ?)";

    private final static String DELETE_SQL = "DELETE FROM story_pic WHERE story_id = ?";

    private final static String SELECT_SQL = "SELECT id, url, story_id FROM story_pic ";

    private final static String SELECT_BY_ID_SQL = SELECT_SQL + "WHERE story_id = ?";

    private final static String SELECT_BY_IDS_SQL = SELECT_SQL + "WHERE story_id in ";

    @Override
    public long insert(StoryPic storyPic) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StoryPic selectById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StoryPic> selectByIds(Collection<Long> ids) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void batchInsert(Collection<StoryPic> storyPicList) {
        if (CollectionUtils.isEmpty(storyPicList)) {
            return;
        }

        List<Object[]> params = new ArrayList<>(storyPicList.size());
        for (StoryPic storyPic : storyPicList) {
            params.add(new Object[] { storyPic.getUrl(), storyPic.getStoryId() });
        }

        jdbcTemplate.batchUpdate(INSERT_SQL, params);
    }

    @Override
    public void deleteByStoryId(long storyId) {
        jdbcTemplate.update(DELETE_SQL, storyId);
    }

    @Override
    public List<StoryPic> selectByStoryId(long storyId) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL,
                BeanPropertyRowMapper.newInstance(StoryPic.class), storyId);
    }

    @Override
    public List<StoryPic> selectByStoryIds(Collection<Long> storyIds) {
        return jdbcTemplate.query(SELECT_BY_IDS_SQL + SQLUtils.formatInSQLPlaceHolder(storyIds.size()),
                BeanPropertyRowMapper.newInstance(StoryPic.class), storyIds.toArray());
    }
}
