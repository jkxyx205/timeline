package com.rick.story.service;

import com.rick.oss.model.ImageObject;
import com.rick.story.entity.Story;
import com.rick.story.entity.Tag;
import com.rick.story.service.bo.StoryBO;

import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:14:00
 */
public interface StoryService {

    /**
     * 发布story
     * @param storyBO
     * @return
     */
    Story post(StoryBO storyBO, List<ImageObject> imageObjectList);

    /**
     * 根据id删除story
     * @param id
     */
    void deleteById(long id);

    /**
     * 根据id查找story
     * @param id
     */
    Story findById(long id);

    /**
     * 所有可选Tag
     * @return
     */
    List<Tag> findAvailableTags();

    /**
     * 根据条件获取story
     * @param offsetStoryId
     * @param sqlCondition
     * @return
     */
    List<Story> listStory(long offsetStoryId, String sqlCondition);

    /**
     * 根据可以Tags
     * @return
     */
    List<Tag> listTags();
}
