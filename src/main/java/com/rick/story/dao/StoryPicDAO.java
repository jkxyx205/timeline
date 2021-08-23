package com.rick.story.dao;

import com.rick.common.dao.BaseDAO;
import com.rick.story.entity.StoryPic;

import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 19:13:00
 */
public interface StoryPicDAO extends BaseDAO<StoryPic> {

    void deleteByStoryId(long storyId);

    List<StoryPic> selectByStoryId(long storyId);

    List<StoryPic> selectByStoryIds(Collection<Long> storyIds);
}
