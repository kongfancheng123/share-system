package com.agioe.tool.data.tcp;


import com.agioe.tool.data.tcp.payload.ControlParameter;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 控制队列
 *
 * @author yshen
 * @since 2018/10/10
 */
public class ControlQueue {

    /**
     * 阻塞队列
     */
    private static BlockingQueue<List<ControlParameter>> linkedQueue = new LinkedBlockingQueue<>(1000);


    /**
     * 消息入队
     *
     * @param parameterList
     * @return
     */
    public static void push(List<ControlParameter> parameterList) {
        //队列满了就抛出异常，不阻塞
//        try {
            linkedQueue.add(parameterList);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println();
    }

    /**
     * 消息出队
     *
     * @return
     */
    public static List<ControlParameter> poll() {
        List<ControlParameter> result = null;
        try {
            result = linkedQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
