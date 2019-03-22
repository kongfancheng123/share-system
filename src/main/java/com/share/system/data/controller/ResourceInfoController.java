package com.share.system.data.controller;

import com.share.system.data.entity.ResourceInfo;
import com.share.system.data.entity.WebResponse;
import com.share.system.data.qo.GetresourceInfosByConditionQo;
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
            ResourceInfo resourceInfo=new ResourceInfo();
            List<ResourceInfo> resourceInfos2=new ArrayList<>();
            if(getresourceInfosByConditionQo.getQuery()==1){//查询租赁
                resourceInfo.setIsLease(1);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByResourceInfo(resourceInfo);
                if(getresourceInfosByConditionQo.getStartTime()!=null&&getresourceInfosByConditionQo.getStartTime()!=""&&
                        getresourceInfosByConditionQo.getEndTime()!=null&&getresourceInfosByConditionQo.getEndTime()!=""){
                    if(resourceInfos1.size()>0){
                        for(ResourceInfo resourceInfo1:resourceInfos1){
                            Date startTime = TimeUtil.stringToDate(getresourceInfosByConditionQo.getStartTime());
                            Date endTime = TimeUtil.stringToDate(getresourceInfosByConditionQo.getEndTime());
                            if(resourceInfo1.getLeaseTime().getTime()>=startTime.getTime()&&
                                    resourceInfo1.getLeaseTime().getTime()<=endTime.getTime()){
                                resourceInfos2.add(resourceInfo1);
                            }
                        }
                    }
                }else{
                    resourceInfos2=resourceInfos1;
                }
            }else if(getresourceInfosByConditionQo.getQuery()==2){//查询归还时间
                resourceInfo.setIsLease(1);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByResourceInfo(resourceInfo);
                if(getresourceInfosByConditionQo.getStartTime()!=null&&getresourceInfosByConditionQo.getStartTime()!=""&&
                        getresourceInfosByConditionQo.getEndTime()!=null&&getresourceInfosByConditionQo.getEndTime()!=""){
                    if(resourceInfos1.size()>0){
                        for(ResourceInfo resourceInfo1:resourceInfos1){
                            Date startTime = TimeUtil.stringToDate(getresourceInfosByConditionQo.getStartTime());
                            Date endTime = TimeUtil.stringToDate(getresourceInfosByConditionQo.getEndTime());
                            if(resourceInfo1.getBackTime().getTime()>=startTime.getTime()&&
                                    resourceInfo1.getBackTime().getTime()<=endTime.getTime()){
                                resourceInfos2.add(resourceInfo1);
                            }
                        }
                    }
                }else{
                    resourceInfos2=resourceInfos1;
                }
            }else if(getresourceInfosByConditionQo.getQuery()==3){//查询预约时间
                resourceInfo.setIsAppointment(1);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByResourceInfo(resourceInfo);
                if(getresourceInfosByConditionQo.getStartTime()!=null&&getresourceInfosByConditionQo.getStartTime()!=""&&
                        getresourceInfosByConditionQo.getEndTime()!=null&&getresourceInfosByConditionQo.getEndTime()!=""){
                    if(resourceInfos1.size()>0){
                        for(ResourceInfo resourceInfo1:resourceInfos1){
                            Date startTime = TimeUtil.stringToDate(getresourceInfosByConditionQo.getStartTime());
                            Date endTime = TimeUtil.stringToDate(getresourceInfosByConditionQo.getEndTime());
                            if(resourceInfo1.getAppointmentTime().getTime()>=startTime.getTime()&&
                                    resourceInfo1.getAppointmentTime().getTime()<=endTime.getTime()){
                                resourceInfos2.add(resourceInfo1);
                            }
                        }
                    }
                }else{
                    resourceInfos2=resourceInfos1;
                }
            }else{//查询所有超期物资
                resourceInfo.setIsLease(1);
                List<ResourceInfo> resourceInfos1= resourceInfoService.selectByResourceInfo(resourceInfo);
                if(resourceInfos1.size()>0){
                    for(ResourceInfo resourceInfo1:resourceInfos1){
                        if(resourceInfo1.getBackTime().getTime()<System.currentTimeMillis()){
                            resourceInfos2.add(resourceInfo1);
                        }
                    }
                }
            }
            return WebResponse.success(resourceInfos2);
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
        resourceInfo.setIsLease(0);
        resourceInfo.setIsOverTime(0);
        resourceInfo.setIsAppointment(0);
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
    @RequestMapping(value = "/deleteResource", method = RequestMethod.GET)
    @ResponseBody
    public WebResponse deleteResource(@RequestParam Integer id) {
        Integer integer = resourceInfoService.deleteResourceInfo(id);
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
    public WebResponse appointResource(@RequestBody ResourceInfo resourceInfo) {
        resourceInfo.setIsAppointment(1);
        resourceInfo.setAppointmentTime(new Date());
        Integer integer = resourceInfoService.updateResourceInfo(resourceInfo);
        return WebResponse.success();
    }

    /**
     * 租赁物资
     */
    @RequestMapping(value = "/leaseResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse leaseResource(@RequestBody ResourceInfo resourceInfo) {
        resourceInfo.setIsLease(1);
        resourceInfo.setLeaseTime(new Date());
        Integer integer = resourceInfoService.updateResourceInfo(resourceInfo);
        return WebResponse.success();
    }

    /**
     * 归还物资
     */
    @RequestMapping(value = "/backResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse backResource(@RequestBody ResourceInfo resourceInfo) {
        resourceInfo.setIsOverTime(0);
        resourceInfo.setIsAppointment(0);
        resourceInfo.setIsLease(0);
        Integer integer = resourceInfoService.updateResourceInfo(resourceInfo);
        return WebResponse.success();
    }

    /**
     * 拷贝信息
     */
    @RequestMapping(value = "/copyResource", method = RequestMethod.POST)
    @ResponseBody
    public WebResponse copyResource(@RequestBody ResourceInfo resourceInfo) {
        Integer integer = resourceInfoService.insertResourceInfo(resourceInfo);
        return WebResponse.success();
    }


}
