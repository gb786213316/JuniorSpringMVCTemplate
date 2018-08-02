package com.haomostudio.JuniorSpringMVCTemplate.controller;

import com.haomostudio.JuniorSpringMVCTemplate.common.Resp;
import com.haomostudio.JuniorSpringMVCTemplate.po.HmUser;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUserService;
import com.haomostudio.JuniorSpringMVCTemplate.service.HmUtils.Tools;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description:
 * @author: gongbin
 * @date: 2018/8/2 20:34
 * @copyright: 长安新生（深圳）金融投资有限公司
 */
@Api(description = "hm-user-controller")
@Controller
public class HmUserController {

    private static final Logger logger = LoggerFactory.getLogger(HmUserController.class);

    @Autowired
    private HmUserService hmUserService;
    @Autowired
    HttpServletResponse response;
    /**
     * @Description 创建用户
     * @author: gongbin
     * @date: 2018/8/2 20:40
     */
    @RequestMapping(value = "createHmUserUsingPOST_1",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createHmUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "createTime", required = false) String createTime,
            @RequestParam(value = "lastUpdateTime", required = false) String lastUpdateTime,
            @RequestParam(value = "lastLoginTime", required = false) String lastLoginTime,
            @RequestParam(value = "departmentCode", required = false) String departmentCode,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "meetingRemindingTime", required = false) Integer meetingRemindingTime,
            @RequestParam(value = "jobTitle", required = false) String jobTitle,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "phoneNumberSpare", required = false) String phoneNumberSpare,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "community", required = false) String community,
            @RequestParam(value = "enable", required = false) String enable,
            @RequestParam(value = "hide", required = false) String hide,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "code", required = false) String code
    ) {
        HmUser item = new HmUser();
        String id = Tools.getUUID();
        item.setId(id);
        if( username != null ){
            item.setUsername(username);
        }
        if( loginid != null ){
            item.setLoginid(loginid);
        }
        if( password != null ){
            item.setPassword(password);
        }
        if( mobile != null ){
            item.setMobile(mobile);
        }
        if( email != null ){
            item.setEmail(email);
        }
        if( avatar != null ){
            item.setAvatar(avatar);
        }
        item.setCreateTime(new Date());
        item.setLastUpdateTime(new Date());
        if( lastLoginTime != null ){
            item.setLastLoginTime(Tools.convertStringToDate(lastLoginTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( departmentCode != null ){
            item.setDepartmentCode(departmentCode);
        }
        if( roleId != null ){
            item.setRoleId(roleId);
        }
        if( meetingRemindingTime != null ){
            item.setMeetingRemindingTime(meetingRemindingTime);
        }
        if( jobTitle != null ){
            item.setJobTitle(jobTitle);
        }
        if( phoneNumber != null ){
            item.setPhoneNumber(phoneNumber);
        }
        if( phoneNumberSpare != null ){
            item.setPhoneNumberSpare(phoneNumberSpare);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        if( community != null ){
            item.setCommunity(community);
        }
        if( enable != null ){
            item.setEnable(enable);
        }
        if( hide != null ){
            item.setHide(hide);
        }
        if( departmentId != null ){
            item.setDepartmentId(departmentId);
        }
        if( code != null ){
            item.setCode(code);
        }
        hmUserService.create(item);

        return hmUserService.get(id);
    }
    /**
     * @Description :更新用户
     * @author: gongbin
     * @date: 2018/8/2 20:39
     */
    @RequestMapping(value = "getHmUserUsingPUT",
            method = RequestMethod.PUT,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object editHmUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_user_id") String id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "lastLoginTime", required = false) String lastLoginTime,
            @RequestParam(value = "departmentCode", required = false) String departmentCode,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "meetingRemindingTime", required = false) Integer meetingRemindingTime,
            @RequestParam(value = "jobTitle", required = false) String jobTitle,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "phoneNumberSpare", required = false) String phoneNumberSpare,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "community", required = false) String community,
            @RequestParam(value = "enable", required = false) String enable,
            @RequestParam(value = "hide", required = false) String hide,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "code", required = false) String code
    ) {
        HmUser item = hmUserService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }

        if( username != null ){
            item.setUsername(username);
        }
        if( loginid != null ){
            item.setLoginid(loginid);
        }
        if( password != null ){
            item.setPassword(password);
        }
        if( mobile != null ){
            item.setMobile(mobile);
        }
        if( email != null ){
            item.setEmail(email);
        }
        if( avatar != null ){
            item.setAvatar(avatar);
        }
        item.setLastUpdateTime(new Date());
        if( lastLoginTime != null ){
            item.setLastLoginTime(Tools.convertStringToDate(lastLoginTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if( departmentCode != null ){
            item.setDepartmentCode(departmentCode);
        }
        if( roleId != null ){
            item.setRoleId(roleId);
        }
        if( meetingRemindingTime != null ){
            item.setMeetingRemindingTime(meetingRemindingTime);
        }
        if( jobTitle != null ){
            item.setJobTitle(jobTitle);
        }
        if( phoneNumber != null ){
            item.setPhoneNumber(phoneNumber);
        }
        if( phoneNumberSpare != null ){
            item.setPhoneNumberSpare(phoneNumberSpare);
        }
        if( rank != null ){
            item.setRank(rank);
        }
        if( community != null ){
            item.setCommunity(community);
        }
        if( enable != null ){
            item.setEnable(enable);
        }
        if( hide != null ){
            item.setHide(hide);
        }
        if( departmentId != null ){
            item.setDepartmentId(departmentId);
        }
        if( code != null ){
            item.setCode(code);
        }
        hmUserService.update(item);

        return hmUserService.get(id);
    }
    /**
     * @Description :查询某个⽤户信息
     * @author: gongbin
     * @date: 2018/8/2 20:41
     */
    @RequestMapping(value = "getHmUserUsingGET",
            method = {RequestMethod.GET},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmUser(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token") String token,
            @PathVariable(value = "hm_user_id") String id,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "mobile", required = false) String mobile,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "avatar", required = false) String avatar,
            @RequestParam(value = "lastLoginTime", required = false) String lastLoginTime,
            @RequestParam(value = "departmentCode", required = false) String departmentCode,
            @RequestParam(value = "roleId", required = false) String roleId,
            @RequestParam(value = "meetingRemindingTime", required = false) Integer meetingRemindingTime,
            @RequestParam(value = "jobTitle", required = false) String jobTitle,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "phoneNumberSpare", required = false) String phoneNumberSpare,
            @RequestParam(value = "rank", required = false) Integer rank,
            @RequestParam(value = "community", required = false) String community,
            @RequestParam(value = "enable", required = false) String enable,
            @RequestParam(value = "hide", required = false) String hide,
            @RequestParam(value = "departmentId", required = false) String departmentId,
            @RequestParam(value = "code", required = false) String code
    ) {
        HmUser item = hmUserService.get(id);
        if (null == item) {
            response.setStatus(404);
            return Resp.fail("the asset to be edited doesn't exist");
        }
        return item;
    }
    /**
      * @Description :查询'用户表'表中的多条记录
    * @author: gongbin
    * @date: 2018/8/2 20:43
    */
    @RequestMapping(value = "getHmUsersUsingGET_1",
            method = { RequestMethod.GET},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHmUsers(
            HttpServletRequest request,
            @RequestHeader(value = "X-Auth-Token", required = false) String token,
            @RequestParam(value = "pageNo", required = false) Integer pageNo,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortItem", required = false) String sortItem,
            @RequestParam(value = "sortOrder", required = false) String sortOrder,
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "includes", required = false) String includes,
            @RequestParam(value = "refers", required = false) String refers,
            @RequestParam(value = "relates", required = false) String relates
    ) {
        if(pageNo == null){
            pageNo = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        if(sortItem == null){
            sortItem = "id";
        }
        if(sortOrder == null){
            sortOrder = "asc";
        }
        if(filters == null){
            filters = "{}";
        }

        Long total = hmUserService.countListWithPagingAndFilter(filters);
        response.addHeader("total", String.valueOf(total));

        return hmUserService.getListWithPagingAndFilter(
                pageNo, pageSize, sortItem, sortOrder, filters, includes, refers, relates);

    }



}


