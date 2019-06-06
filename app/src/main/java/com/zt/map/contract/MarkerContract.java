package com.zt.map.contract;

import com.zt.map.entity.db.PhotoInfo;
import com.zt.map.entity.db.tab.Tab_Marker;

import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPModel;

public class MarkerContract {
    public interface View {
        void query_tzd(String[] items);
        void query_fsw(String[] items);
        void query_remarks(String[] items);
        void fail(String msg);

        void save_success();
        void save_Fail(String msg);

        void queryMarker_success(Tab_Marker marker);
        void queryMarker_fail(String mag);

        void queryTopType(Tab_Marker marker);

        void getName(String name);
        void createPhoto();

        void queryProjectName(String project);
    }

    public interface Presenter {
        void save(Tab_Marker tab,List<PhotoInfo> resultList);

        void queryMarker(long id);

        void query_tzd(long typeId);
        void query_fsw(long typeId);
        void query_remarks(long typeId);
        void queryTopType(long project,long typeId);

        void getName(long project,long typeId);

        void insertMarker(Tab_Marker tab,long lineId);//插入点

        void queryIsPhoto(long projectId);
    }

    public interface Model {
        void queryMarker(long id,BaseMVPModel.CommotListener<Tab_Marker> listener);
    }
}
