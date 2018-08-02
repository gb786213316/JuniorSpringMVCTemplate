package com.haomostudio.JuniorSpringMVCTemplate.dao;

import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HmUserMapper {
    long countByExample(HmUserExample example);

    int deleteByExample(HmUserExample example);

    int deleteByPrimaryKey(String id);

    int insert(HmUser record);

    int insertSelective(HmUser record);

    List<HmUser> selectByExampleWithRowbounds(HmUserExample example, RowBounds rowBounds);

    List<HmUser> selectByExample(HmUserExample example);

    HmUser selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") HmUser record, @Param("example") HmUserExample example);

    int updateByExample(@Param("record") HmUser record, @Param("example") HmUserExample example);

    int updateByPrimaryKeySelective(HmUser record);

    int updateByPrimaryKey(HmUser record);

    HmUser selectOne(HmUser user);
}