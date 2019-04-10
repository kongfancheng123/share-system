package com.share.system.data.service;

import com.share.system.data.entity.ResourceInfo;
import com.share.system.data.entity.TimeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceInfoService {
    /**
     * 新增
     *
     * @param resourceInfo
     * @return
     */
    Integer insertResourceInfo(ResourceInfo resourceInfo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer deleteResourceInfo(Integer id);

    /**
     * 多条件查询
     *
     * @param resourceInfo
     * @return
     */
    List<ResourceInfo> selectByResourceInfo(ResourceInfo resourceInfo);

    /**
     * 更新
     *
     * @param resourceInfo
     * @return
     */
    Integer updateResourceInfo(ResourceInfo resourceInfo);

    /**
     * 查找所有
     */
    List<ResourceInfo> selectAll();

    /**
     * 根据id进行查找
     */
    ResourceInfo selectByid(Integer id);
    /**
     * 根据租赁信息查询
     */
    List<ResourceInfo> selectByLeaseTime(TimeQuery timeQuery);

    /**
     * 根据预约时间查询
     */
    List<ResourceInfo> selectByAppointmentTime(TimeQuery timeQuery);

    /**
     * 根据超期时间查询
     */
    List<ResourceInfo> selectByBackTime(TimeQuery timeQuery);

    /**
     * 归还
     *
     * @param resourceInfo
     * @return
     */
    Integer backResource(@Param("resourceInfo") ResourceInfo resourceInfo);
}
