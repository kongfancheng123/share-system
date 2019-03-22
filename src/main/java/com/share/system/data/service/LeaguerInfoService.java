package com.share.system.data.service;

import com.share.system.data.entity.LeaguerInfo;

import java.util.List;

public interface LeaguerInfoService {
    /**
     * 新增
     *
     * @param leaguerInfo
     * @return
     */
    Integer insertLeaguerInfo(LeaguerInfo leaguerInfo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Integer deleteLeaguerInfo(Integer id);

    /**
     * 多条件查询
     *
     * @param leaguerInfo
     * @return
     */
    List<LeaguerInfo> selectByLeaguerInfo(LeaguerInfo leaguerInfo);

    /**
     * 更新
     *
     * @param leaguerInfo
     * @return
     */
    Integer updateLeaguerInfo(LeaguerInfo leaguerInfo);

    /**
     * 查找所有
     */
    List<LeaguerInfo> selectAll();

    /**
     * 根据id进行查找历史数据
     */
    LeaguerInfo selectByid(Integer id);
}
