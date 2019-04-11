package com.share.system.data.controller;

import com.github.pagehelper.PageHelper;
import com.share.system.data.Vo.GetLeaguersByLeaguerInfoVo;
import com.share.system.data.entity.LeaguerInfo;
import com.share.system.data.entity.WebResponse;
import com.share.system.data.page.PageBean;
import com.share.system.data.qo.GetLeaguersByLeaguerInfoQo;
import com.share.system.data.service.LeaguerInfoService;
import com.share.system.data.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/LeaguerInfo")
public class LeaguerInfoController {
    @Resource
    private LeaguerInfoService leaguerInfoService;

    /**
     * 登录
     *
     * @param leaguerInfo
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse login(@RequestBody LeaguerInfo leaguerInfo) {
        List<LeaguerInfo> leaguerInfos = leaguerInfoService.selectByLeaguerInfo(leaguerInfo);
        if(leaguerInfos.size()>0){
            return WebResponse.success(leaguerInfos.get(0));
        }else{
            return WebResponse.error(400,"用户名或者密码错误");
        }

    }

    /**
     * 注册
     *
     * @param leaguerInfo
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse register(@RequestBody LeaguerInfo leaguerInfo) {
        leaguerInfo.setIsSuperUser(0);
        LeaguerInfo leaguerInfo1=new LeaguerInfo();
        leaguerInfo1.setLeaguerName(leaguerInfo.getLeaguerName());
        List<LeaguerInfo> leaguerInfos = leaguerInfoService.selectByLeaguerInfo(leaguerInfo1);
        if(leaguerInfos.size()>0){
            return WebResponse.error(400,"用户名已存在");
        }else{
            Integer integer = leaguerInfoService.insertLeaguerInfo(leaguerInfo);
            return WebResponse.success();
        }
    }

    /**
     * 条件查找所有会员
     */
    @RequestMapping(value = "/getLeaguersByLeaguerInfo", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse getLeaguersByLeaguerInfo(@RequestBody GetLeaguersByLeaguerInfoQo getLeaguersByLeaguerInfoQo) {
        Integer pageNow = getLeaguersByLeaguerInfoQo.getPageNow();
        Integer pageSize = getLeaguersByLeaguerInfoQo.getPageSize();
        LeaguerInfo leaguerInfo8=new LeaguerInfo();
        leaguerInfo8.setIsSuperUser(0);
        Integer countNums = leaguerInfoService.selectByLeaguerInfo(leaguerInfo8).size();
        LeaguerInfo leaguerInfo=new LeaguerInfo();
        leaguerInfo.setIsSuperUser(0);
        leaguerInfo.setLeaguerName(getLeaguersByLeaguerInfoQo.getLeaguerName()==""?null:getLeaguersByLeaguerInfoQo.getLeaguerName());
        PageHelper.startPage(pageNow, pageSize);
        List<LeaguerInfo> leaguerInfos = leaguerInfoService.selectByLeaguerInfo(leaguerInfo);
        List<GetLeaguersByLeaguerInfoVo> getLeaguersByLeaguerInfoVos=new ArrayList<>();
        if(leaguerInfos.size()>0){
            for(LeaguerInfo leaguerInfo1:leaguerInfos){
                GetLeaguersByLeaguerInfoVo getLeaguersByLeaguerInfoVo=new GetLeaguersByLeaguerInfoVo();
                getLeaguersByLeaguerInfoVo.setId(leaguerInfo1.getId());
                getLeaguersByLeaguerInfoVo.setLeaguerName(leaguerInfo1.getLeaguerName());
                getLeaguersByLeaguerInfoVo.setPassword(leaguerInfo1.getPassword());
                if(leaguerInfo1.getRecentAppointmentTime()!=null){
                    getLeaguersByLeaguerInfoVo.setRecentAppointmentTime(TimeUtil.format(leaguerInfo1.getRecentAppointmentTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                if(leaguerInfo1.getRecentLeaseTime()!=null){
                    getLeaguersByLeaguerInfoVo.setRecentLeaseTime(TimeUtil.format(leaguerInfo1.getRecentLeaseTime(),"yyyy-MM-dd HH:mm:ss"));
                }
                getLeaguersByLeaguerInfoVos.add(getLeaguersByLeaguerInfoVo);
            }
        }
        PageBean<GetLeaguersByLeaguerInfoVo> pageData = new PageBean<>(pageNow, pageSize, countNums);
        pageData.setItems(getLeaguersByLeaguerInfoVos);

        return WebResponse.success(pageData);

    }

    /**
     * 添加会员
     */
    @RequestMapping(value = "/addLeaguer", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse addLeaguer(@RequestBody LeaguerInfo leaguerInfo) {
        Integer integer = leaguerInfoService.insertLeaguerInfo(leaguerInfo);
        return WebResponse.success();
    }

    /**
     * 更新会员
     */
    @RequestMapping(value = "/updateLeaguer", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse updateLeaguer(@RequestBody LeaguerInfo leaguerInfo) {
        Integer integer = leaguerInfoService.updateLeaguerInfo(leaguerInfo);
        return WebResponse.success();
    }

    /**
     * 删除会员
     */
    @RequestMapping(value = "/deleteLeaguer", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse deleteLeaguer(@RequestBody LeaguerInfo leaguerInfo) {
        Integer integer = leaguerInfoService.deleteLeaguerInfo(leaguerInfo.getId());
        return WebResponse.success();
    }
}
