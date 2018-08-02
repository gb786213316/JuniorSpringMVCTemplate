package com.haomostudio.JuniorSpringMVCTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.haomo.plugin.Page;
import com.haomostudio.JuniorSpringMVCTemplate.dao.*;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmUserExample;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUserService;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.MybatisExampleHelper;
import com.haomostudio.JuniorSpringMVCTemplate.vo.HmUserVO;
import org.apache.ibatis.binding.MapperProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description :
 * @author: gongbin
 * @date: 2018/8/2 18:49
 */
@Service("hmUserService")
public class HmUserServiceImpl implements HmUserService{
    protected static final Logger LOG = LoggerFactory.getLogger(HmUserServiceImpl.class);

    @Autowired
    OrganizationMapper organizationMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    HmUserMapper hmUserMapper;

    @Autowired
    AuthTokenMapper authTokenMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleMenuMapper roleMenuMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    FunctionAuthorityMapper functionAuthorityMapper;

    @Autowired
    RoleFunctionMapper roleFunctionMapper;

    @Autowired
    CcNotificationSettingMapper ccNotificationSettingMapper;

    @Autowired
    ViewHmUserDepartmentRoleMapper viewHmUserDepartmentRoleMapper;

    @Autowired
    HmLogMapper hmLogMapper;

    @Autowired
    CcLoggingMapper ccLoggingMapper;


    @Override
    public HmUser login(HmUser item) {
        return hmUserMapper.selectOne(item);
    }

    @Override
    public int create(HmUser item) {
        return hmUserMapper.insertSelective(item);
    }



    @Override
    public int update(HmUser item) {
        return hmUserMapper.updateByPrimaryKeySelective(item);
    }

    @Override
    public HmUser get(String id) {
        return hmUserMapper.selectByPrimaryKey(id);
    }

    private void assignRelates(String relates, List<HmUserVO> modelVOList){
        JSONObject relatesObj = JSON.parseObject(relates);
        for (String table: relatesObj.keySet()) {
            for(HmUserVO modelVO: modelVOList){
                List<String> joinCols = (List<String>) relatesObj.get(table);
                HmUserExample tableExampleObj = new HmUserExample();
                Map<String, Map<String, Map<String, Object>>> tableFilters = new HashMap<>();
                for(String joinCol: joinCols){
                    Map<String, Map<String, Object>> column = new HashMap<>();
                    Method m = MybatisExampleHelper.getMethod(modelVO.getSuperior(),
                        "get"+ CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, joinCol));
                    Map<String, Object> condition = new HashMap();
                    try{
                        if (m.invoke(modelVO.getSuperior())==null){
                            return;
                        }
                        condition.put("equalTo", (String)JSON.parse(JSON.toJSONString(m.invoke(modelVO.getSuperior()))));
                        column.put(joinCol, condition);
                        tableFilters.put(table, column);
                    }
                    catch (InvocationTargetException e) {
                        LOG.error(e.toString());
                    }
                    catch (IllegalAccessException e){
                        LOG.error(e.toString());
                    }
                }

                MybatisExampleHelper.assignWhereClause(tableExampleObj, tableExampleObj.or(), table, JSON.toJSONString(tableFilters));
                tableExampleObj.setOrderByClause(MybatisExampleHelper.createOrderClause("id", "asc"));

                try{
                    Field mapperFieldDef = this.getClass()
                    .getDeclaredField(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table)+"Mapper");
                    mapperFieldDef.setAccessible(true);
                    Object fieldValue = mapperFieldDef.get(this);
                    MapperProxy proxy = (MapperProxy) Proxy.getInvocationHandler(fieldValue);
                    Method sbewr = MybatisExampleHelper.getMethod(fieldValue, "selectByExample");
                    Page page = new Page(0,1000);
                    tableExampleObj.setPage(page);
                    Object[] args = new Object[]{tableExampleObj};
                    modelVO.getRelates().put(table, (List<Object>)(proxy.invoke(proxy, sbewr, args)));
                }
                catch (Throwable e){
                    LOG.error(e.toString());
                }
            }
        }
    }



    @Override
    public Object getListWithPagingAndFilter(
        Integer pageNo, Integer pageSize,
        String sortItem, String sortOrder,
        String filters, String includes, String refers, String relates){
        HmUserExample exampleObj = new HmUserExample();
        Page page = new Page((pageNo - 1) * pageSize, pageSize);
        exampleObj.setPage(page);
        MybatisExampleHelper.assignWhereClause(exampleObj, exampleObj.or(), "HmUser", filters);
        exampleObj.setOrderByClause(MybatisExampleHelper.createOrderClause(sortItem, sortOrder));

        List<HmUser> modelList = hmUserMapper.selectByExample(exampleObj);

        List<HmUserVO> modelVOList = modelList.stream().map(model -> new HmUserVO(model)).collect(Collectors.toList());

        if((includes != null || refers != null)&& modelList.size() > 0){
            modelVOList.stream().forEach(status->{
                if (includes != null){
                    this.assignIncludes(includes,status);
                }
                if (refers != null){
                    this.assignRefers(refers,status);
                }
            });
        }

        if(relates != null && modelList.size() > 0){
            this.assignRelates(relates, modelVOList);
        }

        if(includes == null && refers == null && relates == null){
            return modelList;
        }
        else{
            return modelVOList;
        }
    }

    @Override
    public Long countListWithPagingAndFilter(String filters){
        HmUserExample exampleObj = new HmUserExample();
        MybatisExampleHelper.assignWhereClause(exampleObj, exampleObj.or(), "HmUser", filters);
        return hmUserMapper.countByExample(exampleObj);
    }
    
    private void assignIncludes(String includes, HmUserVO status){
            JSONObject includesObj = JSON.parseObject(includes);
            Map<String,Object> map = new HashMap();
            if (includesObj!=null && includesObj.keySet().size() >0){
                includesObj.keySet().stream().forEach(table->{
                    Object className = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table);
                    JSONObject jsonObject = includesObj.getJSONObject(
                            CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, table));
                    JSONArray columnCondition = jsonObject.getJSONArray("includes");
                    if (columnCondition != null && columnCondition.toArray().length >0){
                        Arrays.stream(columnCondition.toArray()).forEach(obj -> {
                            String ffName = "get"+CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, obj.toString());
                            Method m = MybatisExampleHelper.getMethod(status.getSuperior(), ffName);
                            MybatisExampleHelper.dealSearchIncludes(m,status.getSuperior(),
                                    className,map,table,this.getClass().getPackage().getName());
    
                        });
                    }
                });
            }
            status.setIncludes(map);
        }
    
        private void assignRefers(String refers, HmUserVO status){
            JSONObject refersObj = JSON.parseObject(refers);
            Map<String,List<Object>> map = new HashMap();
            if (refersObj!=null && refersObj.keySet().size() > 0){
                refersObj.keySet().stream().forEach(table -> {
                    JSONObject jsonObject = refersObj.getJSONObject(
                            CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, table));
                    JSONArray columnCondition = jsonObject.getJSONArray("includes");
                    if (columnCondition != null && columnCondition.toArray().length > 0){
                        Arrays.stream(columnCondition.toArray()).forEach(obj -> {
                            MybatisExampleHelper.dealSearchRefers(status.getSuperior().getId(),
                                    table,map,obj,CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, table),
                                    this.getClass().getPackage().getName());
                        });
                    }
                });
            }
    
            status.setRefers(map);
        }
}
