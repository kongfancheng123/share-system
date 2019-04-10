package com.share.system.data.dao;

import com.share.system.data.entity.ResourceInfo;
import com.share.system.data.entity.TimeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResourceInfoDao {
    /**
     * 新增
     *
     * @param resourceInfo
     * @return
     */
    Integer insertResourceInfo(@Param("resourceInfo") ResourceInfo resourceInfo);

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
    List<ResourceInfo> selectByResourceInfo(@Param("resourceInfo") ResourceInfo resourceInfo);

    /**
     * 更新
     *
     * @param resourceInfo
     * @return
     */
    Integer updateResourceInfo(@Param("resourceInfo") ResourceInfo resourceInfo);

    /**
     * 查找所有
     */
    List<ResourceInfo> selectAll();

    /**
     * 根据id进行查找
     */
    ResourceInfo selectByid(Integer id);

    /**
    * 根据租赁时间查询
    */
    List<ResourceInfo> selectByLeaseTime(@Param("timeQuery")TimeQuery timeQuery);

    /**
     * 根据预约时间查询
     */
    List<ResourceInfo> selectByAppointmentTime(@Param("timeQuery")TimeQuery timeQuery);

    /**
     * 根据超期时间查询
     */
    List<ResourceInfo> selectByBackTime(@Param("timeQuery")TimeQuery timeQuery);

    /**
     * 归还
     *
     * @param resourceInfo
     * @return
     */
    Integer backResource(@Param("resourceInfo") ResourceInfo resourceInfo);
}
