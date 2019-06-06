package com.zt.map.entity.db.tab;

import android.text.TextUtils;

import com.zt.map.entity.db.system.Sys_Color;
import com.zt.map.entity.db.system.Sys_Icon;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

/**
 * 管点表
 */
public class Tab_Marker extends LitePalSupport {
    private long id;
    private long projectId;
    private long typeId;//类型id

    private String tsdh;//图上点号
    private String wtdh;//物探点号
    private String tzd;//特征点
    private String fsw;//附属物
    private double latitude;//x
    private double longitude;//y
    private String dmgc;//地面高程
    private String pxjw;//偏心井位
    private String dldm;//道路代码
    private String msnd;//建设年代
    private String tfh;//图幅号
    private String fzlx;//辅助类型
    private String remarks;//备注
    private Date updateTime;
    private Date createTime;
    private String iconBase;
    private Sys_Color sys_color;

    public Sys_Color getSys_color() {
            if (sys_color!=null){
                return sys_color;
            }
            sys_color = LitPalUtils.selectsoloWhere(Sys_Color.class,"id = ?",String.valueOf(typeId));
            return sys_color;
    }



    public String getIconBase() {
        if (!TextUtils.isEmpty(iconBase)){
            return iconBase;
        }
        if (TextUtils.isEmpty(fsw)){
            return iconBase;
        }
        Sys_Icon icon = LitPalUtils.selectsoloWhere(Sys_Icon.class, "name = ?", fsw);
        if (icon != null) {
            iconBase = icon.getValue();
            return iconBase;
        } else {
            return "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAoHBwkHBgoJCAkLCwoMDxkQDw4ODx4WFxIZJCAmJSMgIyIoLTkwKCo2KyIjMkQyNjs9QEBAJjBGS0U+Sjk/QD3/2wBDAQsLCw8NDx0QEB09KSMpPT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT09PT3/wAARCAAJAAkDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDckk0qTQdQv9S1KaLxHFJKOLhlmhmDERpHFnkfdwMEMDk5zWn/AGl4z/6B8f8A3wP8ak1L/kpWn/8AXMfyauzoA//Z";
        }
    }


    public long getTypeId() {
        return typeId;
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

    public String getTsdh() {
        return tsdh;
    }

    public void setTsdh(String tsdh) {
        this.tsdh = tsdh;
    }

    public String getWtdh() {
        return wtdh;
    }

    public void setWtdh(String wtdh) {
        this.wtdh = wtdh;
    }

    public String getTzd() {
        return tzd;
    }

    public void setTzd(String tzd) {
        this.tzd = tzd;
    }

    public String getFsw() {
        return fsw;
    }

    public void setFsw(String fsw) {
        this.fsw = fsw;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDmgc() {
        return dmgc;
    }

    public void setDmgc(String dmgc) {
        this.dmgc = dmgc;
    }

    public String getPxjw() {
        return pxjw;
    }

    public void setPxjw(String pxjw) {
        this.pxjw = pxjw;
    }

    public String getDldm() {
        return dldm;
    }

    public void setDldm(String dldm) {
        this.dldm = dldm;
    }

    public String getMsnd() {
        return msnd;
    }

    public void setMsnd(String msnd) {
        this.msnd = msnd;
    }

    public String getTfh() {
        return tfh;
    }

    public void setTfh(String tfh) {
        this.tfh = tfh;
    }

    public String getFzlx() {
        return fzlx;
    }

    public void setFzlx(String fzlx) {
        this.fzlx = fzlx;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
