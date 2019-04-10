package com.share.system.data.entity;

import lombok.Data;

@Data
public class TimeQuery {
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
}
