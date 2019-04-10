package com.share.system.data.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class GetresourceInfosByConditionVo {
    /**
     * id
     */
    private Integer id;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源描述
     */
    private String resourceDesc;
    /**
     * 预约时间
     */
    private String appointmentTime;
    /**
     * 被租赁时间
     */
    private String leaseTime;
    /**
     * 应归还日期
     */
    private String backTime;
    /**
     * 状态,0是无状态(同样表示已归还,状态清零),1是被预约,2是被租赁,3是已超期
     */
    private String state;
    /**
     * 操作人id
     */
    private String userName;
}
