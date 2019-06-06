package com.zt.map.util;

import android.graphics.Point;
import android.view.MotionEvent;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;

public class MapUtil {

    private static Point point;
    private static BaiduMap mBaiduMap;

    public static void init(BaiduMap BaiduMap){
        mBaiduMap = BaiduMap;
        point= new Point();
    }

    public static BaiduMap getmBaiduMap() {
        return mBaiduMap;
    }



    /**
     * 屏幕坐标点转地图坐标点
     *
     * @param motionEvent
     */
    public static LatLng fromLocation(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        return fromLocation(x, y);
    }

    public static LatLng fromLocation(float x, float y) {
        point.x = (int) x;
        point.y = (int) y;
        return mBaiduMap.getProjection().fromScreenLocation(point);
    }


}
