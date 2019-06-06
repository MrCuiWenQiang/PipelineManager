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
public class LineActivity extends BaseMVPAcivity<LineContract.View, LinePresenter> implements LineContract.View, View.OnClickListener {

    private static final String KEY_ID = "KEY_ID";
    private static final String PROJECT_ID = "PROJECT_ID";
    private static final String TYPE_ID = "TYPE_ID";


    public static final String KEY_STARTLATLNG_X = "KEY_STARTLATLNG_X";
    public static final String KEY_STARTLATLNG_Y = "KEY_STARTLATLNG_Y";
    public static final String KEY_ENDLATLNG_X = "KEY_ENDLATLNG_X";
    public static final String KEY_ENDLATLNG_Y = "KEY_ENDLATLNG_Y";


    private EditText tvGxzl;
    private ImageView ivLoadGxzl;
    private EditText tvQswh;
    private EditText tvZzwh;
    private EditText tvQdms;
    private EditText tvZzms;
    private EditText tvMsfs;
    private ImageView ivLoadMsfs;
    private EditText tvGxcl;
    private EditText tvGjdm;
    private EditText tvQsdw;
    private ImageView ivLoadQsdw;
    private EditText tvDldm;
    private ImageView ivLoadDldm;
    private EditText tvJsnd;
    private EditText tvFzlx;
    private EditText tvRemarks;
    private ImageView ivLoadRemarks;
    private EditText tvSynd;
    private EditText tvSjly;
    private EditText tvLx;
    private ImageView ivLoadLx;
    private EditText tvTgcl;
    private ImageView ivLoadTgcl;
    private EditText tvYl;
    private ImageView ivLoadYl;
    private EditText tvsy;
    private ImageView ivLoadsy;
    private LinearLayout ll_lx;
    private LinearLayout ll_tgcl;
    private LinearLayout ll_yl;
    private LinearLayout ll_sy;

    private TextView tvSave;
    private TextView tvExit;

    private LinearLayout ll_zks;
    private LinearLayout ll_yyks;
    private LinearLayout ll_dlts;
    private LinearLayout ll_tgcc;

    private EditText tv_zks;
    private EditText tv_yyks;
    private EditText tv_dlts;
    private EditText tv_tgcc;


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
        Intent intent = new Intent(activity, LineActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, requestCode);
    }


    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_line;
    }

    @Override
    protected void initContentView() {
        tvGxzl = findViewById(R.id.tv_gxzl);
        ivLoadGxzl = findViewById(R.id.iv_load_gxzl);
        tvQswh = findViewById(R.id.tv_qswh);
        tvZzwh = findViewById(R.id.tv_zzwh);
        tvQdms = findViewById(R.id.tv_qdms);
        tvZzms = findViewById(R.id.tv_zzms);
        tvMsfs = findViewById(R.id.tv_msfs);
        ivLoadMsfs = findViewById(R.id.iv_load_msfs);
        tvGxcl = findViewById(R.id.tv_gxcl);
        tvGjdm = findViewById(R.id.tv_gjdm);
        tvQsdw = findViewById(R.id.tv_qsdw);
        ivLoadQsdw = findViewById(R.id.iv_load_qsdw);
        tvDldm = findViewById(R.id.tv_dldm);
        ivLoadDldm = findViewById(R.id.iv_load_dldm);
        tvJsnd = findViewById(R.id.tv_jsnd);
        tvFzlx = findViewById(R.id.tv_fzlx);
        tvRemarks = findViewById(R.id.tv_remarks);
        ivLoadRemarks = findViewById(R.id.iv_load_remarks);
        tvSynd = findViewById(R.id.tv_synd);
        tvSjly = findViewById(R.id.tv_sjly);
        tvLx = findViewById(R.id.tv_lx);
        ivLoadLx = findViewById(R.id.iv_load_lx);
        tvTgcl = findViewById(R.id.tv_tgcl);
        ivLoadTgcl = findViewById(R.id.iv_load_tgcl);
        tvYl = findViewById(R.id.tv_yl);
        ivLoadYl = findViewById(R.id.iv_load_yl);
        ll_lx = findViewById(R.id.ll_lx);
        ll_tgcl = findViewById(R.id.ll_tgcl);
        ll_yl = findViewById(R.id.ll_yl);

        tvsy = findViewById(R.id.tv_sy);
        ivLoadsy = findViewById(R.id.iv_load_sy);
        ll_sy = findViewById(R.id.ll_sy);


        tvSave = findViewById(R.id.tv_save);
        tvExit = findViewById(R.id.tv_exit);


        ll_zks = findViewById(R.id.ll_zks);
        ll_yyks = findViewById(R.id.ll_yyks);
        ll_dlts = findViewById(R.id.ll_dlts);
        ll_tgcc = findViewById(R.id.ll_tgcc);

        tv_zks = findViewById(R.id.tv_zks);
        tv_yyks = findViewById(R.id.tv_yyks);
        tv_dlts = findViewById(R.id.tv_dlts);
        tv_tgcc = findViewById(R.id.tv_tgcc);
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
        ivLoadGxzl.setOnClickListener(this);
        ivLoadMsfs.setOnClickListener(this);
        ivLoadRemarks.setOnClickListener(this);

        ivLoadLx.setOnClickListener(this);
        ivLoadTgcl.setOnClickListener(this);
        ivLoadYl.setOnClickListener(this);
        ivLoadsy.setOnClickListener(this);

        tvSave.setOnClickListener(this);
        tvExit.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_load_remarks: {
                mPresenter.query_remarks(typeId);
                break;
            }
            case R.id.iv_load_gxzl: {
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
            case R.id.iv_load_tgcl: {
                selectValue(tvTgcl, tgcls);

                break;
            }
            case R.id.iv_load_yl: {
                selectValue(tvYl, pressures);
                break;
            } case R.id.iv_load_sy: {
                selectValue(tvsy, dy);
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

        tab_line.setGxzl(getValue(tvGxzl));
        tab_line.setQdms(getValue(tvQdms));
        tab_line.setZzms(getValue(tvZzms));
        tab_line.setMsfs(getValue(tvMsfs));
        tab_line.setGxcl(getValue(tvGxcl));
        tab_line.setGjdm(getValue(tvGjdm));
        tab_line.setQsdw(getValue(tvQsdw));
        tab_line.setDldm(getValue(tvDldm));
        tab_line.setJsnd(getValue(tvJsnd));
        tab_line.setFzlx(getValue(tvFzlx));
        tab_line.setRemarks(getValue(tvRemarks));
        tab_line.setSynd(getValue(tvSynd));
        tab_line.setSjly(getValue(tvSjly));

        tab_line.setDy(getValue(tvsy));
        tab_line.setZks(getValue(tv_zks));
        tab_line.setYyks(getValue(tv_yyks));
        tab_line.setDlts(getValue(tv_dlts));
        tab_line.setTgcl(getValue(tv_tgcc));

        if (ll_yl.getVisibility() == View.VISIBLE) {
            tab_line.setYl(getValue(tvYl));
        }

        if (ll_tgcl.getVisibility() == View.VISIBLE) {
            tab_line.setTgcl(getValue(tvTgcl));
        }

        if (ll_lx.getVisibility() == View.VISIBLE) {
            tab_line.setLx(getValue(tvLx));
        }


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


        tvGxzl.setText(tabLine.getGxzl());
        tvQswh.setText(tabLine.getQswh());
        tvQswh.setTag(tabLine.getStartMarkerId());
        tvZzwh.setText(tabLine.getZzwh());
        tvZzwh.setTag(tabLine.getEndMarkerId());
        tvQdms.setText(tabLine.getQdms());
        tvZzms.setText(tabLine.getZzms());
        tvMsfs.setText(tabLine.getMsfs());
        tvGxcl.setText(tabLine.getGxcl());
        tvGjdm.setText(tabLine.getGjdm());
        tvQsdw.setText(tabLine.getQsdw());
        tvDldm.setText(tabLine.getDldm());
        tvJsnd.setText(tabLine.getJsnd());
        tvFzlx.setText(tabLine.getFzlx());
        tvRemarks.setText(tabLine.getRemarks());
        tvSynd.setText(tabLine.getSynd());
        tvSjly.setText(tabLine.getSjly());

        tvYl.setText(tabLine.getYl());
        tvTgcl.setText(tabLine.getTgcl());
        tvLx.setText(tab_line.getLx());

        tvsy.setText(tabLine.getDy());
        tv_zks.setText(tabLine.getZks());
        tv_yyks.setText(tabLine.getYyks());
        tv_dlts.setText(tabLine.getDlts());
        tv_tgcc.setText(tabLine.getTgcl());


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
        selectValue(tvGxzl, items);
    }

    @Override
    public void query_msfs(String[] items) {
        selectValue(tvMsfs, items);
    }

    @Override
    public void queryTopType(Tab_Line tabLine) {
        dimiss();
        if (tabLine == null) return;

        tvGxzl.setText(tabLine.getGxzl());
        tvQdms.setText(tabLine.getQdms());
        tvZzms.setText(tabLine.getZzms());
        tvMsfs.setText(tabLine.getMsfs());
        tvGxcl.setText(tabLine.getGxcl());
        tvGjdm.setText(tabLine.getGjdm());
        tvQsdw.setText(tabLine.getQsdw());
        tvDldm.setText(tabLine.getDldm());
        tvJsnd.setText(tabLine.getJsnd());
        tvFzlx.setText(tabLine.getFzlx());
        tvRemarks.setText(tabLine.getRemarks());
        tvSynd.setText(tabLine.getSynd());
        tvSjly.setText(tabLine.getSjly());
    }

    @Override
    public void queryUncertainData(String[] tgcls, String[] pressures, String[] directions) {
        this.tgcls = tgcls;
        this.pressures = pressures;
        this.directions = directions;
        if (directions != null && directions.length > 0) {
            ll_lx.setVisibility(View.VISIBLE);
        }
        if (tgcls != null && tgcls.length > 0) {
            ll_tgcl.setVisibility(View.VISIBLE);
        }
        if (pressures != null && pressures.length > 0) {
            ll_yl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showDX() {
        ll_zks.setVisibility(View.VISIBLE);
        ll_yyks.setVisibility(View.VISIBLE);
        ll_dlts.setVisibility(View.VISIBLE);
        ll_tgcc.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDL(String[] dy) {
        this.dy = dy;
        ll_sy.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPS(String[] lx) {
        this.directions = lx;
            ll_lx.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRQ(String[] yl) {
        this.pressures = yl;
        ll_yl.setVisibility(View.VISIBLE);
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
