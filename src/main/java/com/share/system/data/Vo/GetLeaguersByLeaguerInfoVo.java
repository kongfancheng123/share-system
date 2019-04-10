package com.share.system.data.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class GetLeaguersByLeaguerInfoVo {
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
     * 最近预约时间
     */
    private String recentAppointmentTime;
    /**
     * 最近租赁时间
     */
    private String recentLeaseTime;
}
