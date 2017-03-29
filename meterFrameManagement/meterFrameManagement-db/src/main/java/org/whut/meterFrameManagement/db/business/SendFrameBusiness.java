package org.whut.meterFrameManagement.db.business;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.communicationframe.convert.*;
import org.whut.meterFrameManagement.communicationframe.enums.ValveCtrStyle;
import org.whut.meterFrameManagement.communicationframe.frames.FrameFactory;
import org.whut.meterFrameManagement.db.entity.TSend;
import org.whut.meterFrameManagement.db.service.TSendService;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/29.
 */
@Component
public class SendFrameBusiness {

    @Autowired
    TSendService tSendService;

    public List<TSend> getSendFrame(String meterID){
        return tSendService.getSendFrame(meterID);
    }
    public void addSendFrame(String meterID,String frameString,Timestamp timestamp){

        tSendService.addSendFrame(meterID,frameString,timestamp);
    }
    public void deleteSendFrame(int id){
        tSendService.deleteSendFrame(id);
    }

    /**
     * 将json转成对应的帧字符串并存数据库
     * @param jsonString
     */
    public void makeSendFrame(String jsonString){
        //从json字符串中解析出命令码,表号
        String funCodeStr = getFunCode(jsonString);
        String meterID = getMeterID(jsonString);
        System.out.println("表号："+meterID+"，命令码："+funCodeStr);
        int funCode = Integer.parseInt(funCodeStr,16);
        byte[] sendBytes = new byte[0];
        String sendString = "";
        try {
            switch (funCode){
                case 0x01:
                    ObjectMapper objectMapper = new ObjectMapper();
                    ValveControlParam valveControlParam = objectMapper.readValue(jsonString,ValveControlParam.class);
                    sendBytes = FrameFactory.getValveControlFrame(valveControlParam.getMeterID()
                            , valveControlParam.getKey(), valveControlParam.getFrameID()
                            , ValveCtrStyle.允许开启, null, valveControlParam.getTimeCorrection());
                    break;
                case 0x03:
                    objectMapper = new ObjectMapper();
                    valveControlParam = objectMapper.readValue(jsonString,ValveControlParam.class);
                    sendBytes = FrameFactory.getValveControlFrame(valveControlParam.getMeterID()
                            , valveControlParam.getKey(), valveControlParam.getFrameID()
                            ,ValveCtrStyle.临时关闭,null, valveControlParam.getTimeCorrection());
                    break;
                case 0x05:
                    objectMapper = new ObjectMapper();
                    ReadMeterParam rmp = objectMapper.readValue(jsonString,ReadMeterParam.class);
                    sendBytes = FrameFactory.getMeterDataFrame(rmp.getMeterID(),rmp.getKey(),
                            rmp.getFrameID(),rmp.getDate(),rmp.getTimeCorrection());
                    break;
                case 0x06:
                    objectMapper = new ObjectMapper();
                    RunTimeGasParam rgp = objectMapper.readValue(jsonString,RunTimeGasParam.class);
                    sendBytes = FrameFactory.getMeterUseSetFrame(rgp.getMeterID(),rgp.getKey()
                            ,rgp.getFrameID(),rgp.getCzfs1(),rgp.getZyql(),rgp.getCzfs2(),rgp.getBzq()
                            ,rgp.getCzfs3(),rgp.getSzq(),rgp.getFs(),rgp.getLszqyl()
                            ,rgp.getTimeCorrection());
                    break;
                case 0x07:
                    objectMapper = new ObjectMapper();
                    ChargeModeParam cmp = objectMapper.readValue(jsonString,ChargeModeParam.class);
                    sendBytes = FrameFactory.getSetChargingModeFrame(cmp.getMeterID(),cmp.getKey()
                            ,cmp.getFrameID(),cmp.getSfms());
                    break;
                case 0x08:
                    objectMapper = new ObjectMapper();
                    ChangePriceParam cpp = objectMapper.readValue(jsonString,ChangePriceParam.class);
                    sendBytes = FrameFactory.getChangePriceFrame(cpp.getMeterID(),cpp.getKey()
                            ,cpp.getFrameID(),cpp.getP0(),cpp.getP1(),cpp.getP2(),cpp.getP3()
                            ,cpp.getA1(),cpp.getA2(),cpp.getA3(),cpp.getBeginDT(),cpp.getClen()
                            ,cpp.getAtDT(),cpp.getTimeCorrection());
                    break;
                case 0x0A:
                    objectMapper = new ObjectMapper();
                    ChangeMoneyParam changeMoneyParam = objectMapper.readValue(jsonString,ChangeMoneyParam.class);
                    sendBytes = FrameFactory.getChangeMoneyFrame(changeMoneyParam.getMeterID()
                            ,changeMoneyParam.getKey(),changeMoneyParam.getFrameID()
                            ,changeMoneyParam.getMoney(),changeMoneyParam.getCzfs()
                            ,changeMoneyParam.getHxbj(),changeMoneyParam.getTimeCorrection());
                    break;
                case 0x0C:
                    objectMapper = new ObjectMapper();
                    ValveControlParam vcp = objectMapper.readValue(jsonString,ValveControlParam.class);
                    sendBytes = FrameFactory.getValveControlFrame(vcp.getMeterID(),vcp.getKey()
                            ,vcp.getFrameID(),ValveCtrStyle.强制关闭
                            ,vcp.getAtDT(),vcp.getTimeCorrection());
                    break;
                case 0x0D:
                    objectMapper = new ObjectMapper();
                    ChangeServerNumParam csnp = objectMapper.readValue(jsonString,ChangeServerNumParam.class);
                    sendBytes = FrameFactory.getSetServerNoFrame(csnp.getMeterID(),csnp.getKey()
                            ,csnp.getFrameID(),csnp.getTimeCorrection(),csnp.getN()
                            ,csnp.getServerNum());
                    break;
                case 0x0F:
                    objectMapper = new ObjectMapper();
                    SetIPAndPortParam setIPPort = objectMapper.readValue(jsonString,SetIPAndPortParam.class);
                    sendBytes = FrameFactory.getSetIPAndPortFrame(setIPPort.getMeterID()
                            ,setIPPort.getKey(),setIPPort.getFrameID()
                            ,setIPPort.getServerIP(),setIPPort.getServerPort()
                            ,setIPPort.getTimeCorrection());
                    break;
                case 0x10:
                    objectMapper = new ObjectMapper();
                    SetBeatHeartRateParam sbhrp = objectMapper.readValue(jsonString,SetBeatHeartRateParam.class);
                    sendBytes = FrameFactory.getSetBeatHeartRateFrame(sbhrp.getMeterID(),sbhrp.getKey()
                            ,sbhrp.getFrameID(),sbhrp.getRate(),sbhrp.getTimeCorrection());
                    break;
                case 0x11:
                    objectMapper = new ObjectMapper();
                    SetBeatHeartParam sbhp = objectMapper.readValue(jsonString,SetBeatHeartParam.class);
                    sendBytes = FrameFactory.getSetBeatHeartFrame(sbhp.getMeterID(), sbhp.getKey()
                            , sbhp.getFrameID(), sbhp.getTimeCorrection());
                    break;
                case 0x12:
                    objectMapper = new ObjectMapper();
                    Meter2ConcentratorParam m2cp = objectMapper.readValue(jsonString,Meter2ConcentratorParam.class);
                    sendBytes = FrameFactory.getSetMeter2ConcentratorFrame(m2cp.getConcentratorID()
                            ,m2cp.getMeterID(),m2cp.getKey()
                            ,m2cp.getFrameID(),m2cp.getTimeCorrection());
                    break;
                case 0x16:
                    objectMapper = new ObjectMapper();
                    SetKeyParam setKeyParam = objectMapper.readValue(jsonString,SetKeyParam.class);
                    sendBytes = FrameFactory.getSetKeyFrame(setKeyParam.getMeterID(),
                            setKeyParam.getKey(),setKeyParam.getnKey(),setKeyParam.getFrameID(),
                            setKeyParam.getTimeCorrection());
                    break;
                case 0x18:
                    objectMapper = new ObjectMapper();
                    Meter2ConcentratorParam meter2ConcentratorParam = objectMapper.readValue(jsonString,Meter2ConcentratorParam.class);
                    sendBytes = FrameFactory.getRecallMeter2ConcentratorFrame(
                            meter2ConcentratorParam.getConcentratorID(),
                            meter2ConcentratorParam.getMeterID(),
                            meter2ConcentratorParam.getKey(),
                            meter2ConcentratorParam.getFrameID(),
                            meter2ConcentratorParam.getTimeCorrection());
                    break;
                case 0x1E:
                    objectMapper = new ObjectMapper();
                    ReadMeterParam readMeterParam = objectMapper.readValue(jsonString,ReadMeterParam.class);
                    sendBytes = FrameFactory.getMeterDataFrame(readMeterParam.getMeterID()
                            ,readMeterParam.getKey(), readMeterParam.getFrameID()
                            ,readMeterParam.getDate(),readMeterParam.getTimeCorrection());
                    break;
                case 0x1F:
                    objectMapper = new ObjectMapper();
                    ChangePriceParam changePriceParam = objectMapper.readValue(jsonString,ChangePriceParam.class);
                    sendBytes = FrameFactory.getChangePriceFrame(
                            changePriceParam.getMeterID(),changePriceParam.getKey()
                            ,changePriceParam.getFrameID(),changePriceParam.getP0()
                            ,changePriceParam.getP1(),changePriceParam.getP2(),changePriceParam.getP3()
                            ,changePriceParam.getA1(),changePriceParam.getA2(),changePriceParam.getA3()
                            ,changePriceParam.getBeginDT(),changePriceParam.getClen()
                            ,changePriceParam.getAtDT(),changePriceParam.getTimeCorrection());
                    break;
                case 0x20:
                    objectMapper = new ObjectMapper();
                    ValveControlParam valveControlParam1 = objectMapper.readValue(jsonString,ValveControlParam.class);
                    sendBytes = FrameFactory.getValveControlFrame(valveControlParam1.getMeterID()
                            ,valveControlParam1.getKey()
                            ,valveControlParam1.getFrameID(),ValveCtrStyle.定时关闭
                            ,valveControlParam1.getAtDT()
                            ,valveControlParam1.getTimeCorrection());
                    break;
                case 0x21:
                    objectMapper = new ObjectMapper();
                    ChangeOverDraftParam codp = objectMapper.readValue(jsonString,ChangeOverDraftParam.class);
                    sendBytes = FrameFactory.getChangeOverdraftFrame(codp.getMeterID(),codp.getKey()
                            ,codp.getFrameID(),codp.getType()
                            ,codp.getTimeCorrection());
                    break;
                case 0x22:
                    objectMapper = new ObjectMapper();
                    StartCycleParam startCycleParam = objectMapper.readValue(jsonString,StartCycleParam.class);
                    sendBytes = FrameFactory.getCommunicationUpCycleFrame(
                            startCycleParam.getMeterID(),
                            startCycleParam.getKey(),
                            startCycleParam.getFrameID(),
                            startCycleParam.getQdzq(),
                            startCycleParam.getTimeCorrection());
                    break;
                case 0x23:
                    objectMapper = new ObjectMapper();
                    MeterOpenParam meterOpenParam = objectMapper.readValue(jsonString,MeterOpenParam.class);
                    sendBytes = FrameFactory.getMeterOpenFrame(meterOpenParam.getMeterID()
                            ,meterOpenParam.getKey(),meterOpenParam.getFrameID()
                            ,meterOpenParam.getMoney(),meterOpenParam.getP0()
                            ,meterOpenParam.getP1(),meterOpenParam.getP2()
                            ,meterOpenParam.getP3(),meterOpenParam.getA1()
                            ,meterOpenParam.getA2(),meterOpenParam.getA3()
                            ,meterOpenParam.getNkey(),meterOpenParam.getBeginDT()
                            ,meterOpenParam.getClen(),meterOpenParam.getCbr()
                            ,meterOpenParam.getBzql(),meterOpenParam.getSzql());
                    break;
                case 0x25:
                    objectMapper = new ObjectMapper();
                    SetCBRParam setCBRParam = objectMapper.readValue(jsonString,SetCBRParam.class);
                    sendBytes = FrameFactory.getMeterSetCBRFrame(setCBRParam.getMeterID(),
                            setCBRParam.getKey(),setCBRParam.getFrameID(),
                            setCBRParam.getTimeCorrection(),setCBRParam.getCbr());
                    break;
                case 0x26:
                    objectMapper = new ObjectMapper();
                    ReadMeterTimerParam rmtp = objectMapper.readValue(jsonString,ReadMeterTimerParam.class);
                    sendBytes = FrameFactory.getTimerDataFrame(rmtp.getMeterID(),rmtp.getKey()
                            ,rmtp.getFrameID(),rmtp.getAtDT()
                            ,rmtp.getTimeCorrection());
                    break;
                case 0x27:
                    objectMapper = new ObjectMapper();
                    ReadMeterTimerParam readMeterTimerParam = objectMapper.readValue(jsonString,ReadMeterTimerParam.class);
                    sendBytes = FrameFactory.getTimerDataOfChangePriceFrame(
                            readMeterTimerParam.getMeterID()
                            , readMeterTimerParam.getKey()
                            , readMeterTimerParam.getFrameID()
                            , readMeterTimerParam.getAtDT()
                            , readMeterTimerParam.getTimeCorrection());

                    break;
                case 0x29:
                    objectMapper = new ObjectMapper();
                    SetBeatHeartParam setBeatHeartParam1 = objectMapper.readValue(jsonString,SetBeatHeartParam.class);
                    sendBytes = FrameFactory.getReadCycleInfoFrame(
                            setBeatHeartParam1.getMeterID(),
                            setBeatHeartParam1.getKey(),
                            setBeatHeartParam1.getFrameID(),
                            setBeatHeartParam1.getTimeCorrection());
                    break;

                case 0x2E:
                    objectMapper = new ObjectMapper();
                    NotifyTXZTParam nTXZTP = objectMapper.readValue(jsonString,NotifyTXZTParam.class);
                    sendBytes = FrameFactory.getNotifyTXZTFrame(nTXZTP.getMobileID()
                            ,nTXZTP.getMeterID(),nTXZTP.getKey()
                            ,nTXZTP.getFrameID(),nTXZTP.getTxzt()
                            ,nTXZTP.getTimeCorrection());
                    break;
                default:
                    break;
            }
            sendString = Hex.BytesToHexString(sendBytes);
            addSendFrame(meterID,sendString,new Timestamp(new Date().getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从json字符串中解析出命令码
     * @param json
     * @return
     */
    public static String getFunCode(String json){
        String indexStr = "\"funCode\":\"";
        int funCodeIndex = json.indexOf(indexStr)+indexStr.length();
        String funCodeStr = json.substring(funCodeIndex,funCodeIndex+2);
        return funCodeStr;
    }

    /**
     * 从json字符串中解析出表号
     * @param json
     * @return
     */
    public static String getMeterID(String json) {
        String indexStr = "\"meterID\":\"";
        int meterIDIndex = json.indexOf(indexStr)+indexStr.length();
        String meterID = json.substring(meterIDIndex,meterIDIndex+13);
        return meterID;
    }
}