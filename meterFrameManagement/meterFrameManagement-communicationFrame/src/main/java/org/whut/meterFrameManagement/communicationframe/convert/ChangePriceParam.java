package org.whut.meterFrameManagement.communicationframe.convert;

import java.util.Date;

/**
 * Created by zhang_minzhong on 2017/3/7.
 */
public class ChangePriceParam {
    private String meterID;
    private String funCode;
    private byte frameID;
    private String key;
    private double p0;
    private double p1;
    private double p2;
    private double p3;
    private int a1;
    private int a2;
    private int a3;
    private Date beginDT;
    private byte clen;
    private Date atDT;
    private long timeCorrection;

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    public int getA3() {
        return a3;
    }

    public void setA3(int a3) {
        this.a3 = a3;
    }

    public Date getAtDT() {
        return atDT;
    }

    public void setAtDT(Date atDT) {
        this.atDT = atDT;
    }

    public Date getBeginDT() {
        return beginDT;
    }

    public void setBeginDT(Date beginDT) {
        this.beginDT = beginDT;
    }

    public byte getClen() {
        return clen;
    }

    public void setClen(byte clen) {
        this.clen = clen;
    }

    public byte getFrameID() {
        return frameID;
    }

    public void setFrameID(byte frameID) {
        this.frameID = frameID;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public double getP0() {
        return p0;
    }

    public void setP0(double p0) {
        this.p0 = p0;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getP3() {
        return p3;
    }

    public void setP3(double p3) {
        this.p3 = p3;
    }

    public long getTimeCorrection() {
        return timeCorrection;
    }

    public void setTimeCorrection(long timeCorrection) {
        this.timeCorrection = timeCorrection;
    }
}
