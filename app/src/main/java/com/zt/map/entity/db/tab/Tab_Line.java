package com.zt.map.entity.db.tab;

import android.graphics.Color;
import android.text.TextUtils;

import com.zt.map.entity.db.system.Sys_Color;
import com.zt.map.entity.db.system.Sys_Table;

import org.litepal.crud.LitePalSupport;

import java.util.Date;
import java.util.List;

import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

/**
 * 管线表
 */
public class Tab_Line extends LitePalSupport {
    private long id;
    private long projectId;

    private long typeId;


    private long startMarkerId;
    private long endMarkerId;

    private double start_latitude;
    private double start_longitude;
    private double end_latitude;
    private double end_longitude;

    private int color =-1;

    // TODO: 2019/5/29 起始物号和终止物号 用markerID去查
    private String Qswh;
    private String Zzwh;


    private String Gxzl;
    private String Qdms;
    private String Zzms;
    private String Msfs;
    private String Gxcl;
    private String Gjdm;
    private String Qsdw;
    private String Dldm;
    private String Jsnd;
    private String Fzlx;
    private String Remarks;
    private String Synd;
    private String Sjly;
    private String lx;
    private String tgcl;
    private String yl;
    private String zks;
    private String yyks;
    private String dlts;
    private String tgcc;
    private String dy;
    private Date updateTime;
    private Date createTime;
    public String getQswh() {
        if (!TextUtils.isEmpty(Qswh)){
            return Qswh;
        }
        Tab_Marker marker = LitPalUtils.selectsoloWhere(Tab_Marker.class,"id = ?",String.valueOf(startMarkerId));
        if (marker==null){
            return null;
        }else {
            Qswh = marker.getWtdh();
            return Qswh;
        }
    }

    public String getZzwh() {
        if (!TextUtils.isEmpty(Zzwh)){
            return Zzwh;
        }
        Tab_Marker marker = LitPalUtils.selectsoloWhere(Tab_Marker.class,"id = ?",String.valueOf(endMarkerId));
        if (marker==null){
            return null;
        }else {
            Zzwh = marker.getWtdh();
            return Zzwh;
        }
    }

    public String getZks() {
        return zks;
    }

    public void setZks(String zks) {
        this.zks = zks;
    }

    public String getYyks() {
        return yyks;
    }

    public void setYyks(String yyks) {
        this.yyks = yyks;
    }

    public String getDlts() {
        return dlts;
    }

    public void setDlts(String dlts) {
        this.dlts = dlts;
    }

    public String getTgcc() {
        return tgcc;
    }

    public void setTgcc(String tgcc) {
        this.tgcc = tgcc;
    }

    public String getDy() {
        return dy;
    }

    public void setDy(String dy) {
        this.dy = dy;
    }

    public long getTypeId() {
        return typeId;
    }

    public int getColor() {
        if (color>0){
            return color;
        }
        Sys_Table tables = LitPalUtils.selectsoloWhere(Sys_Table.class,"id = ?",String.valueOf(typeId));
        if (tables!=null){
            Sys_Color c1 = LitPalUtils.selectsoloWhere(Sys_Color.class,"fatherCode = ?",tables.getCode());
            if (c1!=null){
                color = Color.rgb(Integer.valueOf(c1.getR()),Integer.valueOf(c1.getG()),
                            Integer.valueOf(c1.getB()));
            }else {
                color = 0xAAFF0000;
            }
        }
        return color;
    }

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    public String getTgcl() {
        return tgcl;
    }

    public void setTgcl(String tgcl) {
        this.tgcl = tgcl;
    }

    public String getYl() {
        return yl;
    }

    public void setYl(String yl) {
        this.yl = yl;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }


    public long getStartMarkerId() {
        return startMarkerId;
    }

    public void setStartMarkerId(long startMarkerId) {
        this.startMarkerId = startMarkerId;
    }

    public long getEndMarkerId() {
        return endMarkerId;
    }

    public void setEndMarkerId(long endMarkerId) {
        this.endMarkerId = endMarkerId;
    }

    public double getStart_latitude() {
        return start_latitude;
    }

    public void setStart_latitude(double start_latitude) {
        this.start_latitude = start_latitude;
    }

    public double getStart_longitude() {
        return start_longitude;
    }

    public void setStart_longitude(double start_longitude) {
        this.start_longitude = start_longitude;
    }

    public double getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(double end_latitude) {
        this.end_latitude = end_latitude;
    }

    public double getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(double end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getGxzl() {
        return Gxzl;
    }

    public void setGxzl(String gxzl) {
        Gxzl = gxzl;
    }

    public String getQdms() {
        return Qdms;
    }

    public void setQdms(String qdms) {
        Qdms = qdms;
    }

    public String getZzms() {
        return Zzms;
    }

    public void setZzms(String zzms) {
        Zzms = zzms;
    }

    public String getMsfs() {
        return Msfs;
    }

    public void setMsfs(String msfs) {
        Msfs = msfs;
    }

    public String getGxcl() {
        return Gxcl;
    }

    public void setGxcl(String gxcl) {
        Gxcl = gxcl;
    }

    public String getGjdm() {
        return Gjdm;
    }

    public void setGjdm(String gjdm) {
        Gjdm = gjdm;
    }

    public String getQsdw() {
        return Qsdw;
    }

    public void setQsdw(String qsdw) {
        Qsdw = qsdw;
    }

    public String getDldm() {
        return Dldm;
    }

    public void setDldm(String dldm) {
        Dldm = dldm;
    }

    public String getJsnd() {
        return Jsnd;
    }

    public void setJsnd(String jsnd) {
        Jsnd = jsnd;
    }

    public String getFzlx() {
        return Fzlx;
    }

    public void setFzlx(String fzlx) {
        Fzlx = fzlx;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getSynd() {
        return Synd;
    }

    public void setSynd(String synd) {
        Synd = synd;
    }

    public String getSjly() {
        return Sjly;
    }

    public void setSjly(String sjly) {
        Sjly = sjly;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
