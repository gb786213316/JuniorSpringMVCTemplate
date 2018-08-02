package com.haomostudio.JuniorSpringMVCTemplate.service;

import com.haomostudio.JuniorSpringMVCTemplate.po.AuthToken;

import java.util.List;

/**
* Created by hxgqh on 2016/1/7.
*/
public interface AuthTokenService {

    int create(AuthToken item);

    int delete(String id);

    int update(AuthToken item);

    AuthToken get(String id);

    AuthToken getOneByToken(AuthToken token);

    /**
    * 获取列表
    * @param pageNo 整数,如1
    * @param pageSize 整数,如10
    * @param sortItem 格式为"id, name"
    * @param sortOrder 格式为"asc, desc"
    * @param filters JSON字符串,格式为
    *                {
    *                  table:
    *                  {
    *                    column1: {
    *                      like: '%abc%',
    *                      notLike: ''
    *                      between: [1, 10],
    *                      notBetween: [1, 10]
    *                      isNull: true,   // 只能为true
    *                      isNotNull: true,    // 只能为true
    *                      equalTo: "abc",
    *                      notEqualTo: "abc",
    *                      greaterThan: 10,
    *                      greaterThanOrEqualTo: 10,
    *                      lessThan: 10,
    *                      lessThanOrEqualTo: 10,
    *                      in: [],
    *                      notIn: []
    *                    }
    *                  }
    *                }
    * @return 列表
    */
    List<AuthToken> getListWithPagingAndFilter(Integer pageNo, Integer pageSize,
                                               String sortItem, String sortOrder,
                                               String filters);

    /**
    * 获取列表
    * @param pageNo 整数,如1
    * @param pageSize 整数,如10
    * @param sortItem 格式为"id, name"
    * @param sortOrder 格式为"asc, desc"
    * @param filters JSON字符串, 用来过滤列表的数据, 格式为
    *                {
    *                  table:
    *                  {
    *                    column1: {
    *                      like: '%abc%',
    *                      notLike: ''
    *                      between: [1, 10],
    *                      notBetween: [1, 10]
    *                      isNull: true,   // 只能为true
    *                      isNotNull: true,    // 只能为true
    *                      equalTo: "abc",
    *                      notEqualTo: "abc",
    *                      greaterThan: 10,
    *                      greaterThanOrEqualTo: 10,
    *                      lessThan: 10,
    *                      lessThanOrEqualTo: 10,
    *                      in: [],
    *                      notIn: []
    *                    }
    *                  }
    *                }
    * @param includes JSON字符串, 用来将本表的外链字段(table_id类似的字段)指向的外链表的完整行数据返回, 格式为
    *                 {
    *                   'include_table1': {
    *                     includes: ['include_table11', 'include_table12']
    *                   },
    *                   'include_table2': {
    *                     includes: ['include_table21', 'include_table22']
    *                   }
    *                 }
    * @param refers JSON字符串, 用来将其他表的外链字段为本表的表数据返回, 格式为
    *                 {
    *                   'refer_table1': {
    *                     includes: ['include_table11', 'include_table12']
    *                   },
    *                   'refer_table2': {
    *                     includes: ['include_table21', 'include_table22']
    *                   }
    *                 }
    * @param relates JSON字符串, 用来将其他有间接关系的表(所谓间接关系, 一定是跟本表的某个字段名一致, 且指向同一张表)
    *                 {
    *                   'relate_table1': ['column1', 'column2'],
    *                   'relate_table1': ['column3', 'column4']
    *                 }
    * @return 列表
    */
    Object getListWithPagingAndFilter(Integer pageNo, Integer pageSize,
                                      String sortItem, String sortOrder,
                                      String filters,
                                      String includes,
                                      String refers,
                                      String relates);

    /**
    * 获取列表数量
    * @param filters JSON字符串, 用来过滤列表的数据, 格式为
    *                {
    *                  table:
    *                  {
    *                    column1: {
    *                      like: '%abc%',
    *                      notLike: ''
    *                      between: [1, 10],
    *                      notBetween: [1, 10]
    *                      isNull: true,   // 只能为true
    *                      isNotNull: true,    // 只能为true
    *                      equalTo: "abc",
    *                      notEqualTo: "abc",
    *                      greaterThan: 10,
    *                      greaterThanOrEqualTo: 10,
    *                      lessThan: 10,
    *                      lessThanOrEqualTo: 10,
    *                      in: [],
    *                      notIn: []
    *                    }
    *                  }
    *                }
    * @return 列表计数
    */
    Long countListWithPagingAndFilter(String filters);
}
