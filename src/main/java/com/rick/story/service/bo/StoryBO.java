package com.rick.story.service.bo;

import lombok.Data;

import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 17:16:00
 */
@Data
public class StoryBO {

    private String text;

    private List<String> tagList;

    private String lat;

    private String lng;
}
