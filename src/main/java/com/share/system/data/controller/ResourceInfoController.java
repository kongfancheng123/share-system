package com.share.system.data.controller;

import com.github.pagehelper.PageHelper;
import com.share.system.data.Vo.GetresourceInfosByConditionVo;
import com.share.system.data.entity.LeaguerInfo;
import com.share.system.data.entity.ResourceInfo;
import com.share.system.data.entity.TimeQuery;
import com.share.system.data.entity.WebResponse;
import com.share.system.data.page.PageBean;
import com.share.system.data.qo.AppointResourceQo;
import com.share.system.data.qo.BackResourceQo;
import com.share.system.data.qo.GetresourceInfosByConditionQo;
import com.share.system.data.qo.LeaseResourceQo;
import com.share.system.data.service.LeaguerInfoService;
import com.share.system.data.service.ResourceInfoService;
import com.share.system.data.util.TimeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/ResourceInfo")
public class ResourceInfoController {
    @Resource
    private LeaguerInfoService leaguerInfoService;

    @Resource
    private ResourceInfoService resourceInfoService;

    /**
     * 查看所有资源
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/getresourceInfos", method = RequestMethod.GET)
    @ResponseBody
    public WebResponse getresourceInfos() {
        List<ResourceInfo> resourceInfos = resourceInfoService.selectAll();
        return WebResponse.success(resourceInfos);
    }

    /**
     * 条件查询资源信息
     */
    @RequestMapping(value = "/getresourceInfosByCondition", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse getresourceInfosByCondition(@RequestBody GetresourceInfosByConditionQo getresourceInfosByConditionQo) {
        try {
            //先去把所有的已租赁的获取
            ResourceInfo resourceInfo7=new ResourceInfo();
            resourceInfo7.setState(2);
            List<ResourceInfo> resourceInfos = resourceInfoService.selectByResourceInfo(resourceInfo7);
            if(resourceInfos.size()>0){
                for(ResourceInfo resourceInfo9:resourceInfos){
                    if(resourceInfo9.getBackTime()!=null){
                        if(resourceInfo9.getBackTime().getTime()>System.currentTimeMillis()){
                            resourceInfo9.setState(3);
                            resourceInfoService.updateResourceInfo(resourceInfo9);
                        }
                    }
                }
            }

            Integer pageNow = getresourceInfosByConditionQo.getPageNow();
            Integer pageSize = getresourceInfosByConditionQo.getPageSize();
            Integer countNums = 0;
            PageHelper.startPage(pageNow, pageSize);
            TimeQuery timeQuery=new TimeQuery();
            timeQuery.setStartTime(getresourceInfosByConditionQo.getStartTime());
            timeQuery.setEndTime(getresourceInfosByConditionQo.getEndTime());
            ResourceInfo resourceInfo=new ResourceInfo();
            List<ResourceInfo> resourceInfos2=new ArrayList<>();
            List<GetresourceInfosByConditionVo> getresourceInfosByConditionVos=new ArrayList<>();
            if(getresourceInfosByConditionQo.getState()==null){
                countNums= resourceInfoService.selectAll().size();
                PageHelper.startPage(pageNow, pageSize);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectAll();
                resourceInfos2=resourceInfos1;
            } else if(getresourceInfosByConditionQo.getState()==2){//查询租赁信息
                ResourceInfo resourceInfo1=new ResourceInfo();
                resourceInfo1.setState(2);
                countNums= resourceInfoService.selectByResourceInfo(resourceInfo1).size();
                PageHelper.startPage(pageNow, pageSize);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByLeaseTime(timeQuery);
                resourceInfos2=resourceInfos1;
            }else if(getresourceInfosByConditionQo.getState()==3){//查询超期信息
                ResourceInfo resourceInfo1=new ResourceInfo();
                resourceInfo1.setState(3);
                countNums= resourceInfoService.selectByResourceInfo(resourceInfo1).size();
                PageHelper.startPage(pageNow, pageSize);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByBackTime(timeQuery);
                resourceInfos2=resourceInfos1;
            }else if(getresourceInfosByConditionQo.getState()==1){//查询预约信息
                ResourceInfo resourceInfo1=new ResourceInfo();
                resourceInfo1.setState(1);
                countNums= resourceInfoService.selectByResourceInfo(resourceInfo1).size();
                PageHelper.startPage(pageNow, pageSize);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByAppointmentTime(timeQuery);
                resourceInfos2=resourceInfos1;
            }else {//查询可预约信息
                ResourceInfo resourceInfo1=new ResourceInfo();
                resourceInfo1.setState(0);
                countNums= resourceInfoService.selectByResourceInfo(resourceInfo1).size();
                resourceInfo.setState(0);
                PageHelper.startPage(pageNow, pageSize);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByResourceInfo(resourceInfo);
                resourceInfos2=resourceInfos1;
            }
            if(resourceInfos2.size()>0){
                for(ResourceInfo resourceInfo2:resourceInfos2){
                    GetresourceInfosByConditionVo getresourceInfosByConditionVo=new GetresourceInfosByConditionVo();
                    getresourceInfosByConditionVo.setId(resourceInfo2.getId());
                    getresourceInfosByConditionVo.setResourceName(resourceInfo2.getResourceName());
                    getresourceInfosByConditionVo.setResourceDesc(resourceInfo2.getResourceDesc());
                    if (resourceInfo2.getState()==0) {
                        getresourceInfosByConditionVo.setState("可预约");
                    }
                    if (resourceInfo2.getState()==1) {
                        getresourceInfosByConditionVo.setState("已预约");
                    }
                    if (resourceInfo2.getState()==2) {
                        getresourceInfosByConditionVo.setState("已租赁");
                    }
                    if (resourceInfo2.getState()==3) {
                        getresourceInfosByConditionVo.setState("已超时");
                    }

                    if(resourceInfo2.getAppointmentTime()!=null){
                        getresourceInfosByConditionVo.setAppointmentTime(TimeUtil.format(resourceInfo2.getAppointmentTime(),"yyyy-MM-dd HH:mm:ss"));
                    }
                    if(resourceInfo2.getLeaseTime()!=null){
                        getresourceInfosByConditionVo.setLeaseTime(TimeUtil.format(resourceInfo2.getLeaseTime(),"yyyy-MM-dd HH:mm:ss"));
                    }
                    if(resourceInfo2.getBackTime()!=null){
                        getresourceInfosByConditionVo.setBackTime(TimeUtil.format(resourceInfo2.getBackTime(),"yyyy-MM-dd HH:mm:ss"));
                    }
                    if(resourceInfo2.getUserId()!=null){
                        LeaguerInfo leaguerInfo = leaguerInfoService.selectByid(resourceInfo2.getUserId());
                        getresourceInfosByConditionVo.setUserName(leaguerInfo.getLeaguerName());
                    }
                    getresourceInfosByConditionVos.add(getresourceInfosByConditionVo);

                }
            }
            PageBean<GetresourceInfosByConditionVo> pageData = new PageBean<>(pageNow, pageSize, countNums);
            pageData.setItems(getresourceInfosByConditionVos);
            return WebResponse.success(pageData);
        } catch (Exception e) {
            e.printStackTrace();
            return WebResponse.error(400,"查询失败");
        }


    }

    /**
     * 添加物资
     */
    @RequestMapping(value = "/addResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse addResource(@RequestBody ResourceInfo resourceInfo) {
        resourceInfo.setState(0);
        Integer integer = resourceInfoService.insertResourceInfo(resourceInfo);
        return WebResponse.success();
    }

    /**
     * 更新物资
     */
    @RequestMapping(value = "/updateResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse updateResource(@RequestBody ResourceInfo resourceInfo) {
        Integer integer = resourceInfoService.updateResourceInfo(resourceInfo);
        return WebResponse.success();
    }

    /**
     * 删除物资
     */
    @RequestMapping(value = "/deleteResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse deleteResource(@RequestBody ResourceInfo resourceInfo) {
        Integer integer = resourceInfoService.deleteResourceInfo(resourceInfo.getId());
        return WebResponse.success();
    }

    /**
     * 条件查询物资
     */
    @RequestMapping(value = "/selectResourcesByQuery", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse selectResourcesByQuery(@RequestBody ResourceInfo resourceInfo) {
        List<ResourceInfo> resourceInfos = resourceInfoService.selectByResourceInfo(resourceInfo);
        return WebResponse.success(resourceInfos);
    }

    /**
     * 预约物资
     */
    @RequestMapping(value = "/appointResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse appointResource(@RequestBody AppointResourceQo appointResourceQo) {
        //先查询,再更新
        ResourceInfo resourceInfo = resourceInfoService.selectByid(appointResourceQo.getId());
        resourceInfo.setState(1);
        resourceInfo.setAppointmentTime(new Date());
        resourceInfo.setUserId(appointResourceQo.getUserId());
        Integer integer = resourceInfoService.updateResourceInfo(resourceInfo);
        LeaguerInfo leaguerInfo = leaguerInfoService.selectByid(appointResourceQo.getUserId());
        leaguerInfo.setRecentAppointmentTime(new Date());
        leaguerInfoService.updateLeaguerInfo(leaguerInfo);
        return WebResponse.success();
    }

    /**
     * 租赁物资
     */
    @RequestMapping(value = "/leaseResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse leaseResource(@RequestBody LeaseResourceQo leaseResourceQo) {
        if(leaseResourceQo.getBackTime()!=null){
            //先查询,再更新
            ResourceInfo resourceInfo = resourceInfoService.selectByid(leaseResourceQo.getId());
            resourceInfo.setState(2);
            resourceInfo.setLeaseTime(new Date());
            try {
                resourceInfo.setBackTime(TimeUtil.stringToDate(leaseResourceQo.getBackTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Integer integer = resourceInfoService.updateResourceInfo(resourceInfo);
            LeaguerInfo leaguerInfo = leaguerInfoService.selectByid(resourceInfo.getUserId());
            leaguerInfo.setRecentLeaseTime(new Date());
            leaguerInfoService.updateLeaguerInfo(leaguerInfo);
            return WebResponse.success();
        }else{
            return WebResponse.error(400,"请选择归还时间");
        }

    }

    /**
     * 归还物资
     */
    @RequestMapping(value = "/backResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse backResource(@RequestBody ResourceInfo resourceInfo) {
        ResourceInfo resourceInfo1 = resourceInfoService.selectByid(resourceInfo.getId());
        resourceInfo1.setBackTime(null);
        resourceInfo1.setLeaseTime(null);
        resourceInfo1.setUserId(null);
        resourceInfo1.setState(0);
        resourceInfo1.setAppointmentTime(null);
        resourceInfoService.backResource(resourceInfo1);
        return WebResponse.success();
    }

    /**
     * 拷贝信息
     */
    @RequestMapping(value = "/copyResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse copyResource(@RequestBody ResourceInfo resourceInfo) {
        resourceInfo.setState(0);
        Integer integer = resourceInfoService.insertResourceInfo(resourceInfo);
        return WebResponse.success();
    }


}
