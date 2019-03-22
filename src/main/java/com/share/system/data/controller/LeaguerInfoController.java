package com.share.system.data.controller;

import com.share.system.data.entity.LeaguerInfo;
import com.share.system.data.entity.WebResponse;
import com.share.system.data.service.LeaguerInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
            return WebResponse.success();
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
     * 查找所有会员
     */
    @RequestMapping(value = "/getLeaguers", method = RequestMethod.GET)
    @ResponseBody
    public WebResponse getLeaguers() {
        List<LeaguerInfo> leaguerInfos = leaguerInfoService.selectAll();
        return WebResponse.success(leaguerInfos);
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
    @RequestMapping(value = "/deleteLeaguer", method = RequestMethod.GET)
    @ResponseBody
    public WebResponse deleteLeaguer(@RequestParam Integer id) {
        Integer integer = leaguerInfoService.deleteLeaguerInfo(id);
        return WebResponse.success();
    }
}
