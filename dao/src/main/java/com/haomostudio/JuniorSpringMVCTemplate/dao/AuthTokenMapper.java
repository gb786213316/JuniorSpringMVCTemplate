package com.haomostudio.JuniorSpringMVCTemplate.dao;

import com.haomostudio.JuniorSpringMVCTemplate.po.AuthToken;
import com.haomostudio.JuniorSpringMVCTemplate.po.AuthTokenExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface AuthTokenMapper {
    long countByExample(AuthTokenExample example);

    int deleteByExample(AuthTokenExample example);

    int deleteByPrimaryKey(String id);

    int insert(AuthToken record);

    int insertSelective(AuthToken record);

    List<AuthToken> selectByExampleWithRowbounds(AuthTokenExample example, RowBounds rowBounds);

    List<AuthToken> selectByExample(AuthTokenExample example);

    AuthToken selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AuthToken record, @Param("example") AuthTokenExample example);

    int updateByExample(@Param("record") AuthToken record, @Param("example") AuthTokenExample example);

    int updateByPrimaryKeySelective(AuthToken record);

    int updateByPrimaryKey(AuthToken record);

    AuthToken selectByToken(AuthToken token);
}