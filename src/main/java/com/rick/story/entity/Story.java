package com.rick.story.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:09:00
 */
@Data
public class Story {

    private Long id;

    private String text;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant createTime;

    private String addr;

    private List<StoryPic> storyPicList;

    private List<StoryTag> storyTagList;
}
