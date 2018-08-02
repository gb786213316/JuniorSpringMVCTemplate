package com.haomostudio.JuniorSpringMVCTemplate.service;

import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;

;

/**
 * @Description :
 * @author: gongbin
 * @date: 2018/8/2 18:49
 */
public interface HmUserService {

    HmUser login(HmUser item);

    int create(HmUser item);

    int update(HmUser item);

    HmUser get(String id);


    /**
    * 获取列表
    * @param pageNo
    * @param pageSize
    * @param sortItem
    * @param sortOrder
    * @param filters
    * @param includes
    * @param refers
    * @param relates
    * @return
    */
    Object getListWithPagingAndFilter(Integer pageNo, Integer pageSize,
                                      String sortItem, String sortOrder,
                                      String filters,
                                      String includes,
                                      String refers,
                                      String relates);

    /**
    * 获取列表数量
    * @param filters
    * @return 列表计数
    */
    Long countListWithPagingAndFilter(String filters);
}
