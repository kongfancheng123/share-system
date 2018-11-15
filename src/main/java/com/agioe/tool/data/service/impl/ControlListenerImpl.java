package com.agioe.tool.data.service.impl;

import com.agioe.tool.data.entity.EquipmentInfo;
import com.agioe.tool.data.service.EquipmentInfoService;
import com.agioe.tool.data.singleton.TcpApiSingleton;
import com.agioe.tool.data.tcp.api.ControlListener;
import com.agioe.tool.data.tcp.api.DefaultTcpApiInstance;
import com.agioe.tool.data.tcp.payload.ControlParameter;
import com.agioe.tool.data.tcp.payload.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component(value = "controlListener")
public class ControlListenerImpl implements ControlListener {

    @Autowired
    private EquipmentInfoService equipmentInfoService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onControlArrive(List<ControlParameter> parameterList) {
        logger.info("收到控制时间:"+System.currentTimeMillis());
        //todo:操作数据库
        if (parameterList.size() > 0) {
            List<SensorData> dataList = new ArrayList<>();
            for (ControlParameter controlParameter : parameterList) {
                //判断是否为遥控
                if(String.valueOf(controlParameter.getCmdType()).equals("0")||String.valueOf(controlParameter.getCmdType()).equals("1")){
                    String key = controlParameter.getKey();
                    String[] split = key.split("_");
                    String parentNodeCode = split[0];
                    String controlActionVal = controlParameter.getControlActionVal();
                    //根据key获取设备
                    EquipmentInfo equipmentInfo = new EquipmentInfo();
                    equipmentInfo.setKeyword(key);
                    equipmentInfo.setParentNodeCode(parentNodeCode);
                    //查询
                    List<EquipmentInfo> equipmentInfos = equipmentInfoService.selectByEquipmentInfo(equipmentInfo);
                    if(equipmentInfos.size()>0){
                        EquipmentInfo equipmentInfo1 = equipmentInfos.get(0);
                        String dataVal="0";
                        if(controlActionVal.equals("0")){//关
                            equipmentInfo1.setControlVal("关");
                            logger.info("进行关闭操作");
                        }else{
                            equipmentInfo1.setControlVal("开");
                            dataVal="1";
                            logger.info("进行开启操作");
                        }
                        Date date= new Date();
                        equipmentInfo1.setControllerUpdate(date);
                        equipmentInfoService.updateEquipmentInfo(equipmentInfo1);
                        logger.info("下发命令已存入数据库");
                        //发送实时数据
                        EquipmentInfo equipmentInfo2=new EquipmentInfo();
                        equipmentInfo2.setEquipmentCode(equipmentInfo1.getEquipmentCode());
                        equipmentInfo2.setParentNodeCode(parentNodeCode);
                        equipmentInfo2.setEquipmentPropertyCode("RS");
                        //获取设备
                        List<EquipmentInfo> equipmentInfos1 = equipmentInfoService.selectByEquipmentInfo(equipmentInfo2);
                        if(equipmentInfos1.size()>0){
                            EquipmentInfo equipmentInfo3 = equipmentInfos1.get(0);
                            equipmentInfo3.setDataVal(dataVal);
                            equipmentInfo3.setDataUpdate(new Date());
                            equipmentInfoService.updateEquipmentInfo(equipmentInfo3);
                            logger.info("对应yx属性值已更改");
                            SensorData sensorData = new SensorData();
                            sensorData.setKey(equipmentInfo3.getKeyword());
                            sensorData.setOrg("");
                            sensorData.setTime(date.getTime());
                            sensorData.setType(Byte.decode(String.valueOf(1)));
                            sensorData.setVal(dataVal);
                            dataList.add(sensorData);
                        }
                    }
                }
            }
            //发送数据
            DefaultTcpApiInstance defaultTcpApiInstance = TcpApiSingleton.INSTANCE.getDefaultTcpApiInstance();
            defaultTcpApiInstance.sendSensorData(dataList);
            logger.info("发送实时数据时间:"+System.currentTimeMillis());
        }
    }
}
