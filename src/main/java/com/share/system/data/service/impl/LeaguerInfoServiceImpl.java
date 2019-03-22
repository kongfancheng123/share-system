package com.share.system.data.service.impl;

import com.share.system.data.dao.LeaguerInfoDao;
import com.share.system.data.entity.LeaguerInfo;
import com.share.system.data.service.LeaguerInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LeaguerInfoServiceImpl implements LeaguerInfoService {
    @Resource
    private LeaguerInfoDao leaguerInfoDao;

    @Override
    public Integer insertLeaguerInfo(LeaguerInfo leaguerInfo) {
        return leaguerInfoDao.insertLeaguerInfo(leaguerInfo);
    }

    @Override
    public Integer deleteLeaguerInfo(Integer id) {
        return leaguerInfoDao.deleteLeaguerInfo(id);
    }

    @Override
    public List<LeaguerInfo> selectByLeaguerInfo(LeaguerInfo leaguerInfo) {
        return leaguerInfoDao.selectByLeaguerInfo(leaguerInfo);
    }

    @Override
    public Integer updateLeaguerInfo(LeaguerInfo leaguerInfo) {
        return leaguerInfoDao.updateLeaguerInfo(leaguerInfo);
    }

    @Override
    public List<LeaguerInfo> selectAll() {
        return leaguerInfoDao.selectAll();
    }

    @Override
    public LeaguerInfo selectByid(Integer id) {
        return leaguerInfoDao.selectByid(id);
    }
}
