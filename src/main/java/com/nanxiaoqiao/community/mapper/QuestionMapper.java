package com.nanxiaoqiao.community.mapper;

import com.nanxiaoqiao.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, tag, gmt_create, gmt_modified, creator) values (#{question.title}, #{question.description}," +
            "#{question.tag}, #{question.gmtCreate}, #{question.gmtModified}, #{question.creator})")
    void create(@Param("question")Question question);
}
