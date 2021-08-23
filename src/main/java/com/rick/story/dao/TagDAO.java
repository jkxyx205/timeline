package com.rick.story.dao;

import com.rick.story.entity.Tag;

import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-17 19:13:00
 */
public interface TagDAO {

    List<Tag> selectAll();

}
