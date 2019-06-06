package com.zt.map.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.faker.repaymodel.util.error.ErrorUtil;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author dmrfcoder
 * @date 2018/8/9
 */
public class ExcelUtil {

    public static synchronized Object CloneAttribute(Object clone, Object beCloned) {
        Field[] fieldClone = null;
        Field[] fieldBeCloned = null;
        Map<String, Field> map = new HashMap<String, Field>();
        try {
            Class<?> classClone = clone.getClass();
            Class<?> classBecloned = beCloned.getClass();

            Annotation[] ats = classBecloned.getAnnotations();

            fieldClone = classClone.getDeclaredFields();
            fieldBeCloned = classBecloned.getDeclaredFields();

            for (int t = 0; t < fieldBeCloned.length; t++) {
                map.put(fieldBeCloned[t].getName(), fieldBeCloned[t]);
            }

            for (int i = 0; i < fieldClone.length; i++) {
                String fieldCloneName = fieldClone[i].getName();
                Field fie = map.get(fieldCloneName);
                if (fie != null) {
                    try {
                        Method method1 = classClone.getMethod(getMethodName(fieldCloneName));
                        Method method2 = classBecloned.getMethod(setMethodName(fieldCloneName), fie.getType());
                        method2.invoke(beCloned, method1.invoke(clone));
                    }catch (Exception e){
                        continue;
                    }

                }
            }
        } catch (Exception e) {
            ErrorUtil.showError(e);
        } finally {
            fieldClone = null;
            fieldBeCloned = null;
            map.clear();
        }
        return beCloned;
    }

    private static String getMethodName(String fieldName) {
        String head = fieldName.substring(0, 1).toUpperCase();
        String tail = fieldName.substring(1);
        return "get" + head + tail;
    }

    private static String setMethodName(String fieldName) {
        String head = fieldName.substring(0, 1).toUpperCase();
        String tail = fieldName.substring(1);
        return "set" + head + tail;
    }

}
