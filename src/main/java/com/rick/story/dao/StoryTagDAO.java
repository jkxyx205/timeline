package com.rick.story.dao;

import com.rick.common.dao.BaseDAO;
import com.rick.story.entity.StoryTag;

import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 19:13:00
 */
public interface StoryTagDAO extends BaseDAO<StoryTag> {

    void deleteByStoryId(long storyId);

    List<StoryTag> selectByStoryId(long storyId);

    List<StoryTag> selectByStoryIds(Collection<Long> storyIds);
}
