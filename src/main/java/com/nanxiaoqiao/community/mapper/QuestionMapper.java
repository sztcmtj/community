package com.nanxiaoqiao.community.mapper;

import com.nanxiaoqiao.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title, description, tag, gmt_create, gmt_modified, creator) values (#{question.title}, #{question.description}," +
            "#{question.tag}, #{question.gmtCreate}, #{question.gmtModified}, #{question.creator})")
    void create(@Param("question")Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select * from question where creator = #{id} limit #{offset}, #{size}")
    List<Question> listByUserId(@Param("id")Integer id, @Param("offset") Integer offset, @Param("size")Integer size);

    @Select("select count(1) from question")
    int count();

    @Select("select count(1) from question where creator = #{creator}")
    int countByUserId(@Param("creator")Integer creator);

    @Select("select * from question where id = #{questionId}")
    Question getQuestionById(@Param("questionId")Integer questionId);

    @Update("update question set title = #{title}, description=#{description}, tag = #{tag}, " +
            "gmt_modified = #{gmtModified} where id = #{id}")
    void update(Question question);
}
