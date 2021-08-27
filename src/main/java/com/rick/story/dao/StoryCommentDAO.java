package com.rick.story.dao;

import com.rick.common.dao.BaseDAO;
import com.rick.story.entity.StoryComment;

import java.util.Collection;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 19:13:00
 */
public interface StoryCommentDAO extends BaseDAO<StoryComment> {

    void deleteByStoryId(long storyId);

    List<StoryComment> selectByStoryId(long storyId);

    List<StoryComment> selectByStoryIds(Collection<Long> storyIds);
}
