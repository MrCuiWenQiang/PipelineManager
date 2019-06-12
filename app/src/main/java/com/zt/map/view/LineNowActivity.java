package com.zt.map.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.zt.map.R;
import com.zt.map.contract.LineContract;
import com.zt.map.entity.db.tab.Tab_Line;
import com.zt.map.entity.db.tab.Tab_Marker;
import com.zt.map.presenter.LinePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.ToastUtility;

/**
 * 管线编辑
 */
public class LineNowActivity extends BaseMVPAcivity<LineContract.View, LinePresenter> implements LineContract.View, View.OnClickListener {

    private static final String KEY_ID = "KEY_ID";
    private static final String PROJECT_ID = "PROJECT_ID";
    private static final String TYPE_ID = "TYPE_ID";


    public static final String KEY_STARTLATLNG_X = "KEY_STARTLATLNG_X";
    public static final String KEY_STARTLATLNG_Y = "KEY_STARTLATLNG_Y";
    public static final String KEY_ENDLATLNG_X = "KEY_ENDLATLNG_X";
    public static final String KEY_ENDLATLNG_Y = "KEY_ENDLATLNG_Y";

    private EditText tvGxlx;
    private ImageView ivLoadGxlx;
    private EditText tvQswh;
    private EditText tvZzwh;
    private EditText tvQdms;
    private EditText tvZzms;
    private EditText tvMsfs;
    private ImageView ivLoadMsfs;
    private EditText tvGjdm;
    private EditText tvGxcl;
    private ImageView ivLoadGxcl;
    private EditText tvLx;
    private ImageView ivLoadLx;
    private EditText tvQdyj;
    private EditText tvZdyj;
    private EditText tvYxzt;
    private ImageView ivLoadYxzt;
    private EditText tvDhgd;
    private ImageView ivLoadDhgd;
    private EditText tvJsnd;
    private EditText tvQsdw;
    private EditText tvSzwz;
    private ImageView ivLoadSzwz;
    private EditText tvSyzt;
    private ImageView ivLoadSyzt;
    private EditText tvTcfs;
    private ImageView ivLoadTcfs;
    private EditText tvRemarks;

    private LinearLayout llLx;
    private LinearLayout llQdyj;
    private LinearLayout llZdyj;
    private LinearLayout llDhgd;


    private TextView tvSave;
    private TextView tvExit;

    private long projectId;
    private long typeId;

    private double start_latitude;
    private double start_longitude;
    private double end_latitude;
    private double end_longitude;

    private long start_marker;
    private long end__marker;

    private String[] tgcls;
    private String[] pressures;
    private String[] directions;
    private String[] dy;


    public static Bundle newInstance(Long projectId, long typeId) {
        Bundle args = new Bundle();
        args.putLong(PROJECT_ID, projectId);
        args.putLong(TYPE_ID, typeId);
        return args;
    }

    public static void newInstance(Activity activity, long lineId, int requestCode) {
        Bundle args = new Bundle();
        args.putLong(KEY_ID, lineId);
        Intent intent = new Intent(activity, LineNowActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_now_line;
    }

    @Override
    protected void initContentView() {
        tvGxlx = findViewById(R.id.tv_gxlx);
        ivLoadGxlx = findViewById(R.id.iv_load_gxlx);
        tvQswh = findViewById(R.id.tv_qswh);
        tvZzwh = findViewById(R.id.tv_zzwh);
        tvQdms = findViewById(R.id.tv_qdms);
        tvZzms = findViewById(R.id.tv_zzms);
        tvMsfs = findViewById(R.id.tv_msfs);
        ivLoadMsfs = findViewById(R.id.iv_load_msfs);
        tvGjdm = findViewById(R.id.tv_gjdm);
        tvGxcl = findViewById(R.id.tv_gxcl);
        ivLoadGxcl = findViewById(R.id.iv_load_gxcl);
        llLx = findViewById(R.id.ll_lx);
        llDhgd = findViewById(R.id.ll_dhgd);
        tvLx = findViewById(R.id.tv_lx);
        ivLoadLx = findViewById(R.id.iv_load_lx);
        llQdyj = findViewById(R.id.ll_qdyj);
        tvQdyj = findViewById(R.id.tv_qdyj);
        llZdyj = findViewById(R.id.ll_zdyj);
        tvZdyj = findViewById(R.id.tv_zdyj);
        tvYxzt = findViewById(R.id.tv_yxzt);
        ivLoadYxzt = findViewById(R.id.iv_load_yxzt);
        tvDhgd = findViewById(R.id.tv_dhgd);
        ivLoadDhgd = findViewById(R.id.iv_load_dhgd);
        tvJsnd = findViewById(R.id.tv_jsnd);
        tvQsdw = findViewById(R.id.tv_qsdw);
        tvSzwz = findViewById(R.id.tv_szwz);
        ivLoadSzwz = findViewById(R.id.iv_load_szwz);
        tvSyzt = findViewById(R.id.tv_syzt);
        ivLoadSyzt = findViewById(R.id.iv_load_syzt);
        tvTcfs = findViewById(R.id.tv_tcfs);
        ivLoadTcfs = findViewById(R.id.iv_load_tcfs);
        tvRemarks = findViewById(R.id.tv_remarks);


        tvSave = findViewById(R.id.tv_save);
        tvExit = findViewById(R.id.tv_exit);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setLeftTitle("绘制管线",R.color.white);
        setToolBarBackgroundColor(R.color.blue);

        long lineId = getIntent().getLongExtra(KEY_ID, -1);
        if (lineId > 0) {
            showLoading();
            mPresenter.queryLine(lineId);
        } else {
            projectId = getIntent().getLongExtra(PROJECT_ID, -1);
            typeId = getIntent().getLongExtra(TYPE_ID, -1);

            start_latitude = getIntent().getDoubleExtra(KEY_STARTLATLNG_X, -1);
            start_longitude = getIntent().getDoubleExtra(KEY_STARTLATLNG_Y, -1);
            end_latitude = getIntent().getDoubleExtra(KEY_ENDLATLNG_X, -1);
            end_longitude = getIntent().getDoubleExtra(KEY_ENDLATLNG_Y, -1);


            showLoading();
            mPresenter.queryTopType(projectId, typeId);
            mPresenter.queryStartAndEndMarer(projectId, typeId, start_latitude, start_longitude, end_latitude, end_longitude);
//            mPresenter.queryUncertainData(typeId);
            mPresenter.queryShowType(typeId);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        ivLoadMsfs.setOnClickListener(this);
        ivLoadGxlx.setOnClickListener(this);

        ivLoadLx.setOnClickListener(this);

        tvSave.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        ivLoadSyzt.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_load_gxlx:{
                mPresenter.queryType(typeId);
                break;
            }

            case R.id.iv_load_msfs: {
                mPresenter.queryMsfs(typeId);
                break;
            }
            case R.id.iv_load_lx: {
                selectValue(tvLx, directions);
                break;
            }
       case R.id.iv_load_syzt: {
           mPresenter.queryUseStatus(typeId);
                break;
            }

            case R.id.tv_save: {
                save();
                break;
            }
            case R.id.tv_exit: {
                finish();
                break;
            }
        }
    }

    private Tab_Line tab_line;

    private void save() {
        if (tab_line == null) {
            tab_line = new Tab_Line();
            tab_line.setCreateTime(new Date());
        }
        tab_line.setProjectId(projectId);
        tab_line.setTypeId(typeId);

        tab_line.setStartMarkerId(getTAGtoLong(tvQswh));
        tab_line.setEndMarkerId(getTAGtoLong(tvZzwh));

        tab_line.setStart_latitude(start_latitude);
        tab_line.setStart_longitude(start_longitude);
        tab_line.setEnd_latitude(end_latitude);
        tab_line.setEnd_longitude(end_longitude);

        tab_line.setGxlx(getValue(tvGxlx));
        tab_line.setQdms(getValue(tvQdms));
        tab_line.setZzms(getValue(tvZzms));
        tab_line.setMsfs(getValue(tvMsfs));
        tab_line.setGjdm(getValue(tvGjdm));
        tab_line.setGxcl(getValue(tvGxcl));

        tab_line.setLx(getValue(tvLx));

        tab_line.setQdyj(getValue(tvQdyj));
        tab_line.setZdyj(getValue(tvZdyj));
        tab_line.setYxzt(getValue(tvYxzt));
        tab_line.setDhgd(getValue(tvDhgd));
        tab_line.setJsnd(getValue(tvJsnd));
        tab_line.setQsdw(getValue(tvQsdw));
        tab_line.setSzwz(getValue(tvSzwz));
        tab_line.setSyzt(getValue(tvSyzt));
        tab_line.setTcfs(getValue(tvTcfs));

        tab_line.setRemarks(getValue(tvRemarks));

        tab_line.setUpdateTime(new Date());

        showLoading();
        mPresenter.save(tab_line);
    }

    @Override
    public void saveSuccess() {
        dimiss();
        finish();
    }

    @Override
    public void saveFail(String msg) {
        dimiss();
        ToastUtility.showToast(msg);
    }

    @Override
    public void queryLine_Success(Tab_Line tabLine) {
        dimiss();
        this.tab_line = tabLine;

        projectId = tabLine.getProjectId();
        typeId = tabLine.getTypeId();

        start_latitude = tabLine.getStart_latitude();
        start_longitude = tabLine.getStart_longitude();
        end_latitude = tabLine.getEnd_latitude();
        end_longitude = tabLine.getEnd_longitude();


        tvQswh.setText(tabLine.getQswh());
        tvQswh.setTag(tabLine.getStartMarkerId());
        tvZzwh.setText(tabLine.getZzwh());
        tvZzwh.setTag(tabLine.getEndMarkerId());

        tvQdms.setText(tabLine.getQdms());
        tvZzms.setText(tabLine.getZzms());
        tvMsfs.setText(tabLine.getMsfs());
        tvGjdm.setText(tabLine.getGjdm());
        tvGxcl.setText(tabLine.getGxcl());
        tvLx.setText(tabLine.getLx());

        tvQdyj.setText(tabLine.getQdyj());
        tvZdyj.setText(tabLine.getZdyj());
        tvYxzt.setText(tabLine.getYxzt());
        tvDhgd.setText(tabLine.getDhgd());
        tvJsnd.setText(tabLine.getJsnd());
        tvQsdw.setText(tabLine.getQsdw());
        tvSzwz.setText(tabLine.getSzwz());
        tvSyzt.setText(tabLine.getSyzt());
        tvTcfs.setText(tabLine.getTcfs());

        tvRemarks.setText(tabLine.getRemarks());

//        mPresenter.queryUncertainData(typeId);
        mPresenter.queryShowType(typeId);
    }

    @Override
    public void queryLine_Fail(String msg) {
        ToastUtility.showToast(msg);
    }

    @Override
    public void query_remarks(String[] items) {
        selectValue(tvRemarks, items);
    }

    @Override
    public void query_LineType(String[] items) {
        selectValue(tvGxlx, items);
    }

    @Override
    public void query_msfs(String[] items) {
        selectValue(tvMsfs, items);
    }

    @Override
    public void queryTopType(Tab_Line tabLine) {
        dimiss();
        if (tabLine == null) return;

        tvQdms.setText(tabLine.getQdms());
        tvZzms.setText(tabLine.getZzms());
        tvMsfs.setText(tabLine.getMsfs());
        tvGjdm.setText(tabLine.getGjdm());
        tvGxcl.setText(tabLine.getGxcl());
        tvLx.setText(tabLine.getLx());

        tvQdyj.setText(tabLine.getQdyj());
        tvZdyj.setText(tabLine.getZdyj());
        tvYxzt.setText(tabLine.getYxzt());
        tvDhgd.setText(tabLine.getDhgd());
        tvJsnd.setText(tabLine.getJsnd());
        tvQsdw.setText(tabLine.getQsdw());
        tvSzwz.setText(tabLine.getSzwz());
        tvSyzt.setText(tabLine.getSyzt());
        tvTcfs.setText(tabLine.getTcfs());
    }

    @Override
    public void queryUncertainData(String[] tgcls, String[] pressures, String[] directions) {
//        this.tgcls = tgcls;
//        this.pressures = pressures;
//        this.directions = directions;
//        if (directions != null && directions.length > 0) {
//            ll_lx.setVisibility(View.VISIBLE);
//        }
//        if (tgcls != null && tgcls.length > 0) {
//            ll_tgcl.setVisibility(View.VISIBLE);
//        }
//        if (pressures != null && pressures.length > 0) {
//            ll_yl.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void showDX() {
//        ll_zks.setVisibility(View.VISIBLE);
//        ll_yyks.setVisibility(View.VISIBLE);
//        ll_dlts.setVisibility(View.VISIBLE);
//        ll_tgcc.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDL(String[] dy) {
        this.dy = dy;
//        ll_sy.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPS(String[] lx) {
        this.directions = lx;
    }




    @Override
    public void showRQ(String[] yl) {
        this.pressures = yl;
//        ll_yl.setVisibility(View.VISIBLE);
    }

    @Override
    public void visiblePS() {
        llLx.setVisibility(View.VISIBLE);
        llQdyj.setVisibility(View.VISIBLE);
        llZdyj.setVisibility(View.VISIBLE);
        llDhgd.setVisibility(View.VISIBLE);
    }

    @Override
    public void queryUseStatus(String[] items) {
        selectValue(tvSyzt,items);
    }

    @Override
    public void fail(String msg) {
        ToastUtility.showToast(msg);
    }

    @Override
    public void queryStartAndEndMarer(Tab_Marker sm, Tab_Marker em) {
        dimiss();
        if (sm != null) {
            start_marker = sm.getId();
            tvQswh.setText(sm.getWtdh());
            tvQswh.setTag(start_marker);
            start_latitude = sm.getLatitude();
            start_longitude = sm.getLongitude();
        }
        if (em != null) {
            end__marker = em.getId();
            tvZzwh.setText(em.getWtdh());
            tvZzwh.setTag(end__marker);

            end_latitude = em.getLatitude();
            end_longitude = em.getLongitude();
        }
    }


    private void selectValue(final TextView tv, final String[] items) {
        if (items==null||items.length<=0){
            ToastUtility.showToast("暂无分类");
            return;
        }
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.v_tablist, new ArrayList<>(Arrays.asList(items)));
        final QMUIListPopup mListPopup = new QMUIListPopup(getContext(), QMUIPopup.DIRECTION_BOTTOM, adapter);
        mListPopup.create(tv.getWidth(), QMUIDisplayHelper.dp2px(getContext(), 200),
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tv.setText(items[position]);
                        mListPopup.dismiss();
                    }
                });
        mListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_CENTER);
        mListPopup.show(tv);
    }
}
