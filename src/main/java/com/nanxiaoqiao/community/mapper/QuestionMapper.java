package com.nanxiaoqiao.community.mapper;

import com.nanxiaoqiao.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, tag, gmt_create, gmt_modified, creator) values (#{question.title}, #{question.description}," +
            "#{question.tag}, #{question.gmtCreate}, #{question.gmtModified}, #{question.creator})")
    void create(@Param("question")Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select count(1) from question")
    int getTotalCount();
}
