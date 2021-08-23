package com.rick.story.dao.impl;

import com.rick.common.dao.BaseDAO;
import com.rick.story.dao.StoryTagDAO;
import com.rick.story.entity.StoryTag;
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
public class StoryTagDAOImpl implements BaseDAO<StoryTag>, StoryTagDAO {

    private final JdbcTemplate jdbcTemplate;

    private final static String INSERT_SQL = "INSERT INTO story_tag(title, story_id) VALUES (?, ?)";

    private final static String DELETE_SQL = "DELETE FROM story_tag WHERE story_id = ?";

    private final static String SELECT_SQL = "SELECT id, title, story_id FROM story_tag ";

    private final static String SELECT_BY_ID_SQL = SELECT_SQL + "WHERE story_id = ?";

    private final static String SELECT_BY_IDS_SQL = SELECT_SQL + "WHERE story_id in ";

    @Override
    public long insert(StoryTag StoryTag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public StoryTag selectById(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void batchInsert(Collection<StoryTag> storyTagList) {
        if (CollectionUtils.isEmpty(storyTagList)) {
            return;
        }

        List<Object[]> params = new ArrayList<>(storyTagList.size());
        for (StoryTag storyTag : storyTagList) {
            params.add(new Object[] { storyTag.getTitle(), storyTag.getStoryId() });
        }

        jdbcTemplate.batchUpdate(INSERT_SQL, params);
    }

    @Override
    public void deleteByStoryId(long storyId) {
        jdbcTemplate.update(DELETE_SQL, storyId);
    }

    @Override
    public List<StoryTag> selectByStoryId(long storyId) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL,
                BeanPropertyRowMapper.newInstance(StoryTag.class), storyId);
    }

    @Override
    public List<StoryTag> selectByStoryIds(Collection<Long> storyIds) {
        return jdbcTemplate.query(SELECT_BY_IDS_SQL + SQLUtils.formatInSQLPlaceHolder(storyIds.size()),
                BeanPropertyRowMapper.newInstance(StoryTag.class), storyIds.toArray());
    }
}
