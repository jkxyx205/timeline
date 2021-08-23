package com.rick.story.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:10:00
 */
@Data
@NoArgsConstructor
public class StoryPic {

    private Long id;

    private String url;

    private Long storyId;

    public StoryPic(String url, Long storyId) {
        this.url = url;
        this.storyId = storyId;
    }
}
