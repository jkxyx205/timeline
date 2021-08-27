package com.rick.story.service.impl;

import com.rick.common.dao.BaseDAO;
import com.rick.oss.client.OssStoryPicClient;
import com.rick.oss.model.ImageObject;
import com.rick.story.dao.StoryCommentDAO;
import com.rick.story.dao.StoryPicDAO;
import com.rick.story.dao.StoryTagDAO;
import com.rick.story.dao.TagDAO;
import com.rick.story.entity.*;
import com.rick.story.service.StoryService;
import com.rick.story.service.bo.StoryBO;
import com.rick.util.DefaultDataUtils;
import com.rick.util.SQLUtils;
import com.rick.util.WebAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:21:00
 */
@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final JdbcTemplate jdbcTemplate;

    private final BaseDAO<Story> storyDAO;

    private final StoryPicDAO storyPicDAO;

    private final StoryTagDAO storyTagDAO;

    private final StoryCommentDAO storyCommentDAO;

    private final TagDAO tagDAO;

    private final OssStoryPicClient ossStoryPicClient;

    private final static String PAGE_SQL = "SELECT id, text, create_time, addr FROM story WHERE id < ? ORDER BY id DESC";

    private final static String UPDATE_ADDR_SQL = "UPDATE story SET addr = ? WHERE id = ?";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Story post(StoryBO storyBO, List<ImageObject> imageObjectList) {
        Story story = new Story();
        story.setText(storyBO.getText());
        story.setAddr(WebAPI.reverseGeocoding(storyBO.getLat(), storyBO.getLng()));
        long storyId = storyDAO.insert(story);
        List<String> picUrlList = ossStoryPicClient.upload(storyId, imageObjectList);

        // insert story pic
        if (!CollectionUtils.isEmpty(picUrlList)) {
            List<StoryPic> storyPicList = new ArrayList<>(picUrlList.size());
            for (String url : picUrlList) {
                storyPicList.add(new StoryPic(url, storyId));
            }

            storyPicDAO.batchInsert(storyPicList);
        }

        // insert story tag
        if (!CollectionUtils.isEmpty(storyBO.getTagList())) {
            List<String> tagList = storyBO.getTagList();
            List<StoryTag> storyTagList = new ArrayList<>(tagList.size());
            for (String tag : tagList) {
                storyTagList.add(new StoryTag(tag, storyId));
            }

            storyTagDAO.batchInsert(storyTagList);
        }

        return findById(storyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(long id) {
        storyDAO.deleteById(id);

        ossStoryPicClient.deleteFiles(storyPicDAO.selectByStoryId(id).stream().map(StoryPic::getUrl).collect(toList()));
        storyPicDAO.deleteByStoryId(id);
        storyTagDAO.deleteByStoryId(id);
        storyCommentDAO.deleteByStoryId(id);
    }

    @Override
    public Story findById(long id) {
        Story story = storyDAO.selectById(id);
        story.setStoryPicList(storyPicDAO.selectByStoryId(id));
        story.setStoryTagList(storyTagDAO.selectByStoryId(id));
        story.setStoryCommentList(storyCommentDAO.selectByStoryId(id));
        return story;
    }

    @Override
    public List<Tag> findAvailableTags() {
        return tagDAO.selectAll();
    }

    @Override
    public List<Story> listStory(long offsetStoryId, String sqlCondition) {
        offsetStoryId = (offsetStoryId == 0 ? Long.MAX_VALUE : offsetStoryId);
        List<Story> storyList = SQLUtils.selectList(jdbcTemplate, Story.class, PAGE_SQL + " LIMIT 0, 10", offsetStoryId);
        if (CollectionUtils.isEmpty(storyList)) {
            return storyList;
        }

        Collection<Long> storyIds = storyList.stream().map(story -> story.getId()).collect(toList());

        List<StoryPic> storyPics = storyPicDAO.selectByStoryIds(storyIds);
        List<StoryTag> storyTags = storyTagDAO.selectByStoryIds(storyIds);
        List<StoryComment> storyComments = storyCommentDAO.selectByStoryIds(storyIds);

        Map<Long, List<StoryPic>> picMap = storyPics.stream()
                .collect(groupingBy(StoryPic::getStoryId, toList()));

        Map<Long, List<StoryTag>> tagMap = storyTags.stream()
                .collect(groupingBy(StoryTag::getStoryId, toList()));

        Map<Long, List<StoryComment>> commentMap = storyComments.stream()
                .collect(groupingBy(StoryComment::getStoryId, toList()));

        for (Story story : storyList) {
            story.setStoryPicList(DefaultDataUtils.defaultEmptyList(picMap.get(story.getId())));
            story.setStoryTagList(DefaultDataUtils.defaultEmptyList(tagMap.get(story.getId())));
            story.setStoryCommentList(DefaultDataUtils.defaultEmptyList(commentMap.get(story.getId())));
        }

        return storyList;
    }

    @Override
    public List<Tag> listTags() {
        return tagDAO.selectAll();
    }

    @Override
    public long addComment(Long id, String text) {
       if (isTagComment(text)) {
           long tagId = storyTagDAO.insert(new StoryTag(text.replace("#", ""), id));
           return -tagId;
       }

       if (isAddressComment(text)) {
           jdbcTemplate.update(UPDATE_ADDR_SQL, text.replace("#", ""), id);
           return 0;
       }

       return storyCommentDAO.insert(new StoryComment(text, id));
    }

    @Override
    public void deleteComment(Long id) {
        storyCommentDAO.deleteById(id);
    }

    private boolean isAddressComment(String text) {
        return text.startsWith("#") && !text.endsWith("#");
    }

    private boolean isTagComment(String text) {
        return text.startsWith("#") && text.endsWith("#");
    }
}
