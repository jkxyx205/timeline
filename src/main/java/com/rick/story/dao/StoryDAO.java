package com.rick.story.dao;

import com.rick.common.dao.BaseDAO;
import com.rick.story.entity.Story;

import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 19:13:00
 */
public interface StoryDAO extends BaseDAO<Story> {
    List<Story> selectStarStory();
}
