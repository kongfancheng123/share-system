package com.share.system.data.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ResourceInfo {
    /**
     * id
     */
    private Integer id;
    /**
     * 资源编码
     */
    private String resourceCode;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 是否被预约
     */
    private Integer isAppointment;
    /**
     * 预约时间
     */
    private Date appointmentTime;
    /**
     * 是否被租赁
     */
    private Integer isLease;
    /**
     * 被租赁时间
     */
    private Date leaseTime;
    /**
     * 应归还日期
     */
    private Date backTime;
    /**
     * 是否超期
     */
    private Integer isOverTime;
    /**
     * 操作人id
     */
    private Integer userId;
}
