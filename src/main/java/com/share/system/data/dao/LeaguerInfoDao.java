package com.share.system.data.dao;

import com.share.system.data.entity.LeaguerInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeaguerInfoDao {
    /**
     * 新增
     *
     * @param leaguerInfo
     * @return
     */
    Integer insertLeaguerInfo(@Param("leaguerInfo") LeaguerInfo leaguerInfo);

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
    List<LeaguerInfo> selectByLeaguerInfo(@Param("leaguerInfo") LeaguerInfo leaguerInfo);

    /**
     * 更新
     *
     * @param leaguerInfo
     * @return
     */
    Integer updateLeaguerInfo(@Param("leaguerInfo") LeaguerInfo leaguerInfo);

    /**
     * 查找所有
     */
    List<LeaguerInfo> selectAll();

    /**
     * 根据id进行查找历史数据
     */
    LeaguerInfo selectByid(Integer id);
}
