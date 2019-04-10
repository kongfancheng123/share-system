package com.share.system.data.qo;

import lombok.Data;

import java.util.Date;

/**
 * Create by fchkong on 2019/3/21.
 */
@Data
public class GetresourceInfosByConditionQo {
    /**
     * 查询条件
     * 1代表查询租赁时间,2代表查询归还时间,3代表预约时间,4代表所有超期物资
     */
    private Integer state;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 当前页
     */
    private Integer pageNow;
    /**
     * 每页显示数量
     */
    private Integer pageSize;


}
