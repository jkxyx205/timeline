package com.rick.story.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    public StoryTag(String title, Long storyId) {
        this.title = title;
        this.storyId = storyId;
    }
}
