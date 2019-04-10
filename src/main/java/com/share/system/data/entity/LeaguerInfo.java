package com.share.system.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LeaguerInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 会员名称
     */
    private String leaguerName;
    /**
     * 会员密码
     */
    private String password;
    /**
     * 是否为管理员
     */
    private Integer isSuperUser;
    /**
     * 最近预约时间
     */
    private Date recentAppointmentTime;
    /**
     * 最近租赁时间
     */
    private Date recentLeaseTime;
}
