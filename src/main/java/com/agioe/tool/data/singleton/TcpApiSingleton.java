package com.agioe.tool.data.singleton;


import com.agioe.tool.data.common.SpringContextUtil;
import com.agioe.tool.data.tcp.api.ControlListener;
import com.agioe.tool.data.tcp.api.DefaultTcpApiInstance;

/**
 * 枚举模式创建单例
 */
public enum  TcpApiSingleton {
    INSTANCE;
    private DefaultTcpApiInstance defaultTcpApiInstance;

    private TcpApiSingleton() {
        ControlListener listener = SpringContextUtil.getBean(ControlListener.class);
        defaultTcpApiInstance = new DefaultTcpApiInstance(listener);
    }

    public DefaultTcpApiInstance getDefaultTcpApiInstance() {
        return defaultTcpApiInstance;
    }



}
