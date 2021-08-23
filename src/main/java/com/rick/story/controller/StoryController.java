package com.rick.story.controller;

import com.rick.common.model.Result;
import com.rick.oss.model.ImageObject;
import com.rick.story.entity.Story;
import com.rick.story.service.StoryService;
import com.rick.story.service.bo.StoryBO;
import com.rick.util.WebAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 18:06:00
 */
@RestController
@RequestMapping("stories")
@RequiredArgsConstructor
@CrossOrigin
public class StoryController {

    private final StoryService storyService;

    @PostMapping
    public Story post(StoryBO storyBO, @RequestParam(required = false, name = "files") MultipartFile[] files) throws IOException {
        List<ImageObject> imageObjectList =  new ArrayList<>();
        if (files != null) {
            for (MultipartFile file : files) {
                imageObjectList.add(new ImageObject(file.getInputStream(),
                        StringUtils.getFilenameExtension(file.getOriginalFilename())));
            }
        }

        return storyService.post(storyBO, imageObjectList);
    }

    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Long id) {
        storyService.deleteById(id);
        return Result.success();
    }

    @GetMapping("{id}")
    public Result<Story> findById(@PathVariable Long id) {
        return Result.success(storyService.findById(id));
    }

    @GetMapping
    public Result<Story> list(@RequestParam(defaultValue = "0") long offsetStoryId) {
        return Result.success(storyService.listStory(offsetStoryId, null));
    }

    @GetMapping("tags")
    public Result<Story> listTags(@RequestParam(defaultValue = "0") long offsetStoryId) {
        return Result.success(storyService.listTags());
    }
}
