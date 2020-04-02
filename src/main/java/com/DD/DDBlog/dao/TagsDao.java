package com.DD.DDBlog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagsDao {
    int deleteTagsByAid(Long aid);

    int saveTags(@Param("tags") String[] tags);

    List<Long> getTagsIdByTagName(@Param("tagNames") String[] tagNames);

    int saveTags2ArticleTags(@Param("tagIds") List<Long> tagIds, @Param("aid") Long aid);
}
