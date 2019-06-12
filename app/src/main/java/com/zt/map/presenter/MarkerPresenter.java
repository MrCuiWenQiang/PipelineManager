package com.zt.map.presenter;

import com.zt.map.contract.MarkerContract;
import com.zt.map.entity.db.PhotoInfo;
import com.zt.map.entity.db.system.Sys_Appendages;
import com.zt.map.entity.db.system.Sys_Features;
import com.zt.map.entity.db.system.Sys_LineType;
import com.zt.map.entity.db.system.Sys_Manhole;
import com.zt.map.entity.db.system.Sys_Table;
import com.zt.map.entity.db.system.Sys_UseStatus;
import com.zt.map.entity.db.tab.Tab_Line;
import com.zt.map.entity.db.tab.Tab_Marker;
import com.zt.map.entity.db.tab.Tab_Project;
import com.zt.map.entity.db.tab.Tab_marker_photo;
import com.zt.map.model.MarkerModel;
import com.zt.map.model.SystemQueryModel;
import com.zt.map.util.out.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import cn.faker.repaymodel.mvp.BaseMVPModel;
import cn.faker.repaymodel.mvp.BaseMVPPresenter;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

public class MarkerPresenter extends BaseMVPPresenter<MarkerContract.View> implements MarkerContract.Presenter {

    private SystemQueryModel queryModel = new SystemQueryModel();
    private MarkerModel markerModel;

    private String[] tzds;
    private String[] fsws;
    private String[] remarks = new String[]{"图边点", "出图点"};


    @Override
    public void save(final Tab_Marker tab, final List<PhotoInfo> resultList) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {

            @Override
            protected Boolean jobContent() throws Exception {
                boolean status = tab.save();
                long makerId = tab.getId();
                long projectId = tab.getProjectId();
                int count = LitPalUtils.selectCount(Tab_marker_photo.class,"markerId=? AND projectId=?",String.valueOf(makerId),
                        String.valueOf(projectId));

                if (resultList != null&&status) {
                    List<Tab_marker_photo> pos = new ArrayList<>();
                    int i=1;
                    for (PhotoInfo info : resultList) {
                        Tab_marker_photo p = new Tab_marker_photo();
                        p.setMarkerId(makerId);
                        p.setProjectId(projectId);
                        p.setName(tab.getWtdh()+"_"+(count+i));
                        p.setPath(info.getPath());
                        pos.add(p);
                        i++;
                    }
                    LitPalUtils.saveAll(pos);
                }

                return status;
            }

            @Override
            protected void jobEnd(Boolean aBoolean) {
                if (getView() == null) {
                    return;
                }
                if (aBoolean) {
                    getView().save_success();
                } else {
                    getView().save_Fail("保存失败");
                }
            }
        });
    }
    private String[] lineTypes;

    @Override
    public void queryMarker(long id) {
        if (markerModel == null) {
            markerModel = new MarkerModel();
        }
        markerModel.queryMarker(id, new BaseMVPModel.CommotListener<Tab_Marker>() {
            @Override
            public void result(Tab_Marker marker) {
                if (getView() == null) {
                    return;
                }
                if (marker != null) {
                    getView().queryMarker_success(marker);
                } else {
                    getView().queryMarker_fail("未查询到该管点");
                }
            }
        });
    }

    String[] status;
    public void queryUseStatus(final long typeId){
        if (status != null) {
            getView().queryUseStatus(status);
            return;
        }

        queryModel.queryUseStatus(typeId, new BaseMVPModel.CommotListener<List<Sys_UseStatus>>() {
            @Override
            public void result(List<Sys_UseStatus> sys_useStatuses) {
                if (getView() == null) {
                    return;
                }
                if (sys_useStatuses == null || sys_useStatuses.size() <= 0) {
                    getView().fail("未获取到选择数据");
                } else {
                    List<String> datas = new ArrayList<>();
                    for (Sys_UseStatus item : sys_useStatuses) {
                        datas.add(item.getName());
                    }
                    status = datas.toArray(new String[datas.size()]);
                    getView().queryUseStatus(status);

                }
            }
        });
    }

    String[] manholes;

    public void querymanhole(final long typeId){
        if (manholes != null) {
            getView().querymanhole(manholes);
            return;
        }
        queryModel.queryManhole(String.valueOf(typeId), new BaseMVPModel.CommotListener<List<Sys_Manhole>>() {
            @Override
            public void result(List<Sys_Manhole> sys_manholes) {
                if (getView() == null) {
                    return;
                }
                if (sys_manholes == null || sys_manholes.size() <= 0) {
                    getView().fail("未获取到选择数据");
                } else {
                    List<String> datas = new ArrayList<>();
                    for (Sys_Manhole item : sys_manholes) {
                        datas.add(item.getName());
                    }
                    manholes = datas.toArray(new String[datas.size()]);
                    getView().querymanhole(manholes);

                }
            }
        });
    }
    public void queryType(final long typeid) {
        if (lineTypes != null) {
            getView().query_LineType(lineTypes);
            return;
        }
        queryModel.queryLineType(String.valueOf(typeid), new BaseMVPModel.CommotListener<List<Sys_LineType>>() {
            @Override
            public void result(List<Sys_LineType> sys_lineTypes) {

                if (getView() == null) {
                    return;
                }
                if (sys_lineTypes == null || sys_lineTypes.size() <= 0) {
                    getView().fail("未获取到选择数据");
                } else {
                    List<String> datas = new ArrayList<>();
                    for (Sys_LineType item : sys_lineTypes) {
                        datas.add(item.getName());
                    }
                    lineTypes = datas.toArray(new String[datas.size()]);
                    getView().query_LineType(lineTypes);

                }

            }
        });
    }
    @Override
    public void query_tzd(long typeId) {
        if (tzds != null) {
            getView().query_tzd(tzds);
            return;
        }
        queryModel.queryAspect(String.valueOf(typeId), new BaseMVPModel.CommotListener<List<Sys_Features>>() {
            @Override
            public void result(List<Sys_Features> sys_features) {
                if (getView() == null) {
                    return;
                }
                if (sys_features == null || sys_features.size() <= 0) {
                    getView().fail("未获取到选择数据");
                } else {
                    List<String> datas = new ArrayList<>();
                    for (Sys_Features item : sys_features) {
                        datas.add(item.getName());
                    }
                    tzds = datas.toArray(new String[datas.size()]);
                    getView().query_tzd(tzds);
                }
            }
        });
    }

    @Override
    public void query_fsw(long typeId) {
        if (fsws != null) {
            getView().query_fsw(fsws);
            return;
        }
        queryModel.queryAppendage(String.valueOf(typeId), new BaseMVPModel.CommotListener<List<Sys_Appendages>>() {
            @Override
            public void result(List<Sys_Appendages> sys_features) {
                if (getView() == null) {
                    return;
                }
                if (sys_features == null || sys_features.size() <= 0) {
                    getView().fail("未获取到选择数据");
                } else {
                    List<String> datas = new ArrayList<>();
                    for (Sys_Appendages item : sys_features) {
                        datas.add(item.getName());
                    }
                    fsws = datas.toArray(new String[datas.size()]);
                    getView().query_fsw(fsws);
                }
            }
        });
    }

    @Override
    public void query_remarks(long typeId) {
        getView().query_remarks(remarks);
    }

    @Override
    public void queryTopType(final long project, final long typeId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Tab_Marker>() {

            @Override
            protected Tab_Marker jobContent() throws Exception {
                List<Tab_Marker> tab_line = LitPalUtils.selectorderWhere("updateTime DESC", Tab_Marker.class, "projectId=? AND typeId = ?", String.valueOf(project), String.valueOf(typeId));
                if (tab_line != null && tab_line.size() > 0) {
                    return tab_line.get(0);
                }
                return null;
            }

            @Override
            protected void jobEnd(Tab_Marker tabLine) {
                if (getView() != null) {
                    getView().queryTopType(tabLine);
                }
            }
        });
    }

    @Override
    public void getName(final long project, final long typeId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<String>() {

            @Override
            protected String jobContent() throws Exception {
                Sys_Table table = LitPalUtils.selectsoloWhere(Sys_Table.class, "id = ?", String.valueOf(typeId));
                StringBuilder name = new StringBuilder();
                name.append(table.getCode());
                int count = LitPalUtils.selectCount(Tab_Marker.class, "projectId=? AND typeId=?"
                        , String.valueOf(project), String.valueOf(typeId));
                if (count > 0) {
                    name.append(count + 1);
                } else {
                    name.append(1);
                }
                return name.toString();
            }

            @Override
            protected void jobEnd(String s) {
                if (getView() != null) {
                    getView().getName(s);
                }
            }
        });
    }

    @Override
    public void insertMarker(final Tab_Marker tab, final long lineId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {

            @Override
            protected Boolean jobContent() throws Exception {
                boolean status = false;
                status = tab.save();
                long new_MarkerId = tab.getId();
                Tab_Line line = LitPalUtils.selectsoloWhere(Tab_Line.class, "id = ?", String.valueOf(lineId));
                Tab_Line endline = new Tab_Line();
                ExcelUtil.CloneAttribute(line, endline);
                endline.setId(-1);
                endline.setStartMarkerId(new_MarkerId);
                endline.setStart_latitude(tab.getLatitude());
                endline.setStart_longitude(tab.getLongitude());
                endline.setEnd_latitude(line.getEnd_latitude());
                endline.setEnd_longitude(line.getEnd_longitude());

                endline.save();


                line.setEndMarkerId(new_MarkerId);
                line.setEnd_latitude(tab.getLatitude());
                line.setEnd_longitude(tab.getLongitude());


                status = line.save();


                return status;
            }

            @Override
            protected void jobEnd(Boolean aBoolean) {
                if (getView() == null) {
                    return;
                }
                if (aBoolean) {
                    getView().save_success();
                } else {
                    getView().save_Fail("保存失败");
                }
            }
        });
    }

    @Override
    public void queryIsPhoto(final long projectId) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Boolean>() {

            @Override
            protected Boolean jobContent() throws Exception {
                Tab_Project project = LitPalUtils.selectsoloWhere(Tab_Project.class, "id = ?", String.valueOf(projectId));
                if (project != null) {
                    return project.isCreatePhoto();
                } else {
                    return false;
                }
            }

            @Override
            protected void jobEnd(Boolean aBoolean) {
                if (getView() != null && aBoolean) {
                    getView().createPhoto();
                }
            }
        });
    }

    public void queryProject(final long projectId){
      DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<String>() {

          @Override
          protected String jobContent() throws Exception {
              Tab_Project project = LitPalUtils.selectsoloWhere(Tab_Project.class,"id=?",String.valueOf(projectId));
              return project==null?"未知":project.getName();
          }

          @Override
          protected void jobEnd(String s) {
              getView().queryProjectName(s);
          }
      });
    }
}
