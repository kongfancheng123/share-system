package com.share.system.data.qo;

import lombok.Data;

@Data
public class GetLeaguersByLeaguerInfoQo {
    /**
     * 会员名称
     */
    private String leaguerName;
    /**
     * 当前页
     */
    private Integer pageNow;
    /**
     * 每页显示数量
     */
    private Integer pageSize;
}
