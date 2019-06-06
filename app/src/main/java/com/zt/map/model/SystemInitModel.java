package com.zt.map.model;

import com.zt.map.MyApplication;
import com.zt.map.R;
import com.zt.map.entity.db.system.Sys_Appendages;
import com.zt.map.entity.db.system.Sys_Color;
import com.zt.map.entity.db.system.Sys_Direction;
import com.zt.map.entity.db.system.Sys_Embedding;
import com.zt.map.entity.db.system.Sys_Features;
import com.zt.map.entity.db.system.Sys_Icon;
import com.zt.map.entity.db.system.Sys_LineType;
import com.zt.map.entity.db.system.Sys_Manhole;
import com.zt.map.entity.db.system.Sys_Material;
import com.zt.map.entity.db.system.Sys_Pressure;
import com.zt.map.entity.db.system.Sys_TGCL;
import com.zt.map.entity.db.system.Sys_Table;
import com.zt.map.entity.db.system.Sys_Type;
import com.zt.map.entity.db.system.Sys_Type_Child;
import com.zt.map.entity.db.system.Sys_UseStatus;
import com.zt.map.util.FileReadOpen;
import com.zt.map.util.Table;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.faker.repaymodel.mvp.BaseMVPModel;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

public class SystemInitModel extends BaseMVPModel {
    private final String[] vs_name = new String[]{"材料", "附属物", "管线类型", "井盖材质", "埋设方式", "使用状况", "方向", "特征", "颜色", "压力", "套管材质"};


    public void initType(final CommotListener<Boolean> listener) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback() {
            @Override
            protected Object jobContent() throws Exception {
                int count = LitPalUtils.selectCount(Sys_Table.class);
                if (count > 0) {
                    return true;
                }
                InputStream inputStream = MyApplication.getContext().getResources().openRawResource(R.raw.setting);
                FileReadOpen.Data d = FileReadOpen.readText(inputStream);
                Map<String, Map<String, List<Table>>> map = d.getMap();
                Map<String, String> fatherMap = d.getFatherMap();//类型数据
                List<Sys_Table> tabs = new ArrayList<>();
                for (String key : fatherMap.keySet()) {
                    Sys_Table tab = new Sys_Table();
                    tab.setName(fatherMap.get(key));
                    tab.setCode(key);
                    tabs.add(tab);
                }
                // TODO: 2019/5/22 此处应使用反射和类赋值
                for (Sys_Table tab : tabs) {
                    Map<String, List<Table>> vmap = map.get(tab.getCode());
                    for (String name : vmap.keySet()) {
                        List<Table> vtab = vmap.get(name);
                        if (name.equals(vs_name[0])) {
                            List<Sys_Material> materials = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Material material = new Sys_Material();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                materials.add(material);
                            }
                            tab.setMaterials(materials);
                            LitPalUtils.saveAll(materials);

                        } else if (name.equals(vs_name[1])) {

                            List<Sys_Appendages> appendages = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Appendages material = new Sys_Appendages();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                appendages.add(material);
                            }
                            tab.setAppendages(appendages);
                            LitPalUtils.saveAll(appendages);

                        } else if (name.equals(vs_name[2])) {

                            List<Sys_LineType> lineTypes = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_LineType material = new Sys_LineType();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                lineTypes.add(material);
                            }
                            tab.setLineTypes(lineTypes);
                            LitPalUtils.saveAll(lineTypes);

                        } else if (name.equals(vs_name[3])) {

                            List<Sys_Manhole> manholes = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Manhole material = new Sys_Manhole();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                manholes.add(material);
                            }
                            tab.setManholes(manholes);
                            LitPalUtils.saveAll(manholes);

                        } else if (name.equals(vs_name[4])) {

                            List<Sys_Embedding> embeddings = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Embedding material = new Sys_Embedding();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                embeddings.add(material);
                            }
                            tab.setEmbeddings(embeddings);
                            LitPalUtils.saveAll(embeddings);

                        } else if (name.equals(vs_name[5])) {

                            List<Sys_UseStatus> useStatuses = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_UseStatus material = new Sys_UseStatus();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                useStatuses.add(material);
                            }
                            tab.setUseStatuses(useStatuses);
                            LitPalUtils.saveAll(useStatuses);

                        } else if (name.equals(vs_name[6])) {

                            List<Sys_Direction> directions = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Direction material = new Sys_Direction();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                directions.add(material);
                            }
                            tab.setDirection(directions);
                            LitPalUtils.saveAll(directions);
                        } else if (name.equals(vs_name[7])) {

                            List<Sys_Features> features = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Features material = new Sys_Features();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                features.add(material);
                            }
                            tab.setFeatures(features);
                            LitPalUtils.saveAll(features);
                        } else if (name.equals(vs_name[8])) {
                            List<Sys_Color> colors = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Color material = new Sys_Color();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                if (t.getValue() != null) {
                                    String[] rgb = t.getValue().split(",");
                                    if (rgb.length == 3) {
                                        material.setR(rgb[0]);
                                        material.setG(rgb[1]);
                                        material.setB(rgb[2]);
                                    }
                                }
                                colors.add(material);
                            }
                            tab.setColors(colors);
                            LitPalUtils.saveAll(colors);
                        } else if (name.equals(vs_name[9])) {

                            List<Sys_Pressure> features = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_Pressure material = new Sys_Pressure();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                features.add(material);
                            }
                            tab.setPressures(features);
                            LitPalUtils.saveAll(features);
                        } else if (name.equals(vs_name[10])) {

                            List<Sys_TGCL> features = new ArrayList<>();
                            for (Table t : vtab) {
                                Sys_TGCL material = new Sys_TGCL();
                                material.setFatherCode(tab.getCode());
                                material.setName(t.getName());
                                features.add(material);
                            }
                            tab.setTgcls(features);
                            LitPalUtils.saveAll(features);
                        }
                    }
                }
                LitPalUtils.saveAll(tabs);
                //保存图片
                InputStream iconinputStream = MyApplication.getContext().getResources().openRawResource(R.raw.icon);
                Map<String, String> maps = FileReadOpen.readIcon(iconinputStream);
                List<Sys_Icon> icons = new ArrayList<>();
                if (maps != null) {
                    for (String key : maps.keySet()) {
                        Sys_Icon sys_icon = new Sys_Icon();
                        sys_icon.setName(key);
                        sys_icon.setValue(maps.get(key));
                        icons.add(sys_icon);
                    }
                }
                LitPalUtils.saveAll(icons);

                InputStream typeStream = MyApplication.getContext().getResources().openRawResource(R.raw.type);
                Map<String, Map<String, String>> params = FileReadOpen.readType(typeStream);
                List<Sys_Type> types = new ArrayList<>();
                List<Sys_Type_Child> child_types = new ArrayList<>();

                for (String typeKey : params.keySet()) {
                    Map<String, String> childMap = params.get(typeKey);
                    Sys_Type type = new Sys_Type();
                    type.setName(typeKey);
                    types.add(type);
                    for (String c_key : childMap.keySet()) {
                        String value = childMap.get(c_key);
                        Sys_Type_Child child = new Sys_Type_Child();
                        child.setName(c_key);
                        child.setValue(value);
                        child.setFatherCode(typeKey);
                        child_types.add(child);
                    }

                }
                LitPalUtils.saveAll(types);
                LitPalUtils.saveAll(child_types);
                return true;
            }

            @Override
            protected void jobEnd(Object o) {
                listener.result((Boolean) o);
            }
        });


    }

}
