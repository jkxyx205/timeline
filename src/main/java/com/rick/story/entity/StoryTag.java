package com.rick.story.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:10:00
 */
@Data
@NoArgsConstructor
public class StoryTag {

    private Long id;

    private String title;

    private Long storyId;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant createTime;

    public StoryTag(String title, Long storyId) {
        this.title = title;
        this.storyId = storyId;
    }
}
