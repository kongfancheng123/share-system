package com.share.system.data.service.impl;

import com.share.system.data.dao.ResourceInfoDao;
import com.share.system.data.entity.ResourceInfo;
import com.share.system.data.entity.TimeQuery;
import com.share.system.data.service.ResourceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ResourceInfoServiceImpl implements ResourceInfoService {
    @Resource
    private ResourceInfoDao resourceInfoDao;

    @Override
    public Integer insertResourceInfo(ResourceInfo resourceInfo) {
        return resourceInfoDao.insertResourceInfo(resourceInfo);
    }

    @Override
    public Integer deleteResourceInfo(Integer id) {
        return resourceInfoDao.deleteResourceInfo(id);
    }

    @Override
    public List<ResourceInfo> selectByResourceInfo(ResourceInfo resourceInfo) {
        return resourceInfoDao.selectByResourceInfo(resourceInfo);
    }

    @Override
    public Integer updateResourceInfo(ResourceInfo resourceInfo) {
        return resourceInfoDao.updateResourceInfo(resourceInfo);
    }

    @Override
    public List<ResourceInfo> selectAll() {
        return resourceInfoDao.selectAll();
    }

    @Override
    public ResourceInfo selectByid(Integer id) {
        return resourceInfoDao.selectByid(id);
    }

    @Override
    public List<ResourceInfo> selectByLeaseTime(TimeQuery timeQuery) {
        return resourceInfoDao.selectByLeaseTime(timeQuery);
    }

    @Override
    public List<ResourceInfo> selectByAppointmentTime(TimeQuery timeQuery) {
        return resourceInfoDao.selectByAppointmentTime(timeQuery);
    }

    @Override
    public List<ResourceInfo> selectByBackTime(TimeQuery timeQuery) {
        return resourceInfoDao.selectByBackTime(timeQuery);
    }

    @Override
    public Integer backResource(ResourceInfo resourceInfo) {
        return resourceInfoDao.backResource(resourceInfo);
    }
}
