package com.zt.map.view;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.zt.map.R;
import com.zt.map.contract.MarkerContract;
import com.zt.map.entity.db.PhotoInfo;
import com.zt.map.entity.db.tab.Tab_Marker;
import com.zt.map.entity.db.tab.Tab_marker_photo;
import com.zt.map.presenter.MarkerPresenter;
import com.zt.map.util.PhotoUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.faker.repaymodel.mvp.BaseMVPAcivity;
import cn.faker.repaymodel.util.LogUtil;
import cn.faker.repaymodel.util.ToastUtility;

public class MarkerActivity extends BaseMVPAcivity<MarkerContract.View, MarkerPresenter> implements MarkerContract.View, View.OnClickListener {

    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_ID = "KEY_ID";
    private static final String PROJECT_ID = "PROJECT_ID";
    private static final String TYPE_ID = "TYPE_ID";
    private static final String LINEID = "lineId";

    public static void newInstance(Activity activity, Long projectId, long typeId, double latitude, double longitude, int requestCode) {
        Bundle args = new Bundle();
        args.putDouble(KEY_LATITUDE, latitude);
        args.putDouble(KEY_LONGITUDE, longitude);
        args.putLong(PROJECT_ID, projectId);
        args.putLong(TYPE_ID, typeId);
        Intent intent = new Intent(activity, MarkerActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void newInstance(Activity activity, Long projectId, long typeId, double latitude, double longitude, long lineId, int requestCode) {
        Bundle args = new Bundle();
        args.putDouble(KEY_LATITUDE, latitude);
        args.putDouble(KEY_LONGITUDE, longitude);
        args.putLong(PROJECT_ID, projectId);
        args.putLong(TYPE_ID, typeId);
        args.putLong(LINEID, lineId);
        Intent intent = new Intent(activity, MarkerActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void newInstance(Activity activity, long makerId, int requestCode) {
        Bundle args = new Bundle();
        args.putLong(KEY_ID, makerId);
        Intent intent = new Intent(activity, MarkerActivity.class);
        intent.putExtras(args);
        activity.startActivityForResult(intent, requestCode);
    }

    private EditText tvDh;
    private EditText tv_wtdh;
    private EditText tvTz;
    private EditText tvFsw;
    private EditText tvX;
    private EditText tvY;
    private EditText tvDmgc;
    private EditText tvPxjw;
    private EditText tvDldm;
    private EditText tvMsnd;
    private EditText tvTfh;
    private EditText tvFzlx;
    private EditText tvRemarks;

    private TextView tvSave;
    private TextView tvExit;

    private ImageView iv_load_tzd;
    private ImageView iv_load_fsw;
    private ImageView iv_load_remarks;

    private long projectId;
    private long typeId;
    private long lineId = -1;

    private Tab_Marker tab;

    private List<PhotoInfo> photos = new ArrayList<>();

    @Override
    protected int getLayoutContentId() {
        return R.layout.ac_marker;
    }

    @Override
    protected void initContentView() {
        setLeftTitle("绘制管点", R.color.white);
        setToolBarBackgroundColor(R.color.blue);

        tvDh = findViewById(R.id.tv_dh);
        tv_wtdh = findViewById(R.id.tv_wtdh);
        tvTz = findViewById(R.id.tv_tz);
        tvFsw = findViewById(R.id.tv_fsw);
        tvX = findViewById(R.id.tv_x);
        tvY = findViewById(R.id.tv_y);
        tvDmgc = findViewById(R.id.tv_dmgc);
        tvPxjw = findViewById(R.id.tv_pxjw);
        tvDldm = findViewById(R.id.tv_dldm);
        tvMsnd = findViewById(R.id.tv_msnd);
        tvTfh = findViewById(R.id.tv_tfh);
        tvFzlx = findViewById(R.id.tv_fzlx);
        tvRemarks = findViewById(R.id.tv_remarks);
        tvSave = findViewById(R.id.tv_save);
        tvExit = findViewById(R.id.tv_exit);

        iv_load_tzd = findViewById(R.id.iv_load_tzd);
        iv_load_fsw = findViewById(R.id.iv_load_fsw);
        iv_load_remarks = findViewById(R.id.iv_load_remarks);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        long markerId = getIntent().getLongExtra(KEY_ID, -1);
        if (markerId > 0) {
            showLoading();
            mPresenter.queryMarker(markerId);
        } else {
            projectId = getIntent().getLongExtra(PROJECT_ID, -1);

            double latitude = getIntent().getDoubleExtra(KEY_LATITUDE, 0);
            double longitude = getIntent().getDoubleExtra(KEY_LONGITUDE, 0);
            tvX.setText(String.valueOf(latitude));
            tvY.setText(String.valueOf(longitude));
            lineId = getIntent().getLongExtra(LINEID, -1);
            typeId = getIntent().getLongExtra(TYPE_ID, -1);
            showLoading();
            mPresenter.queryTopType(projectId, typeId);
            mPresenter.getName(projectId, typeId);
            mPresenter.queryIsPhoto(projectId);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        tvSave.setOnClickListener(this);
        tvExit.setOnClickListener(this);
        iv_load_tzd.setOnClickListener(this);
        iv_load_fsw.setOnClickListener(this);
        iv_load_remarks.setOnClickListener(this);
    }

    private void save() {
        if (tab == null) {
            tab = new Tab_Marker();
            tab.setCreateTime(new Date());
        }

        tab.setProjectId(projectId);
        tab.setTypeId(typeId);

        tab.setTsdh(getValue(tvDh));
        tab.setWtdh(getValue(tv_wtdh));
        tab.setTzd(getValue(tvTz));
        tab.setFsw(getValue(tvFsw));
        tab.setLatitude(getLong(tvX));
        tab.setLongitude(getLong(tvY));
        tab.setDmgc(getValue(tvDmgc));
        tab.setPxjw(getValue(tvPxjw));
        tab.setDldm(getValue(tvDldm));
        tab.setMsnd(getValue(tvMsnd));
        tab.setTfh(getValue(tvTfh));
        tab.setFzlx(getValue(tvFzlx));
        tab.setRemarks(getValue(tvRemarks));

        tab.setUpdateTime(new Date());
        if (lineId == -1) {
            mPresenter.save(tab, photos);
        } else {
            mPresenter.insertMarker(tab, lineId);
        }
    }

    private double getLong(EditText et) {
        String value = getValue(et);
        if (TextUtils.isEmpty(value)) {
            return -1;
        } else {
            return Double.valueOf(value);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_load_tzd: {
                mPresenter.query_tzd(typeId);
                break;
            }
            case R.id.iv_load_fsw: {
                mPresenter.query_fsw(typeId);
                break;
            }
            case R.id.iv_load_remarks: {
                mPresenter.query_remarks(typeId);
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

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void query_tzd(String[] items) {
        selectValue(tvTz, items);
    }

    @Override
    public void query_fsw(String[] items) {
        selectValue(tvFsw, items);
    }

    @Override
    public void query_remarks(String[] items) {
        selectValue(tvRemarks, items);
    }

    @Override
    public void fail(String msg) {
        ToastUtility.showToast(msg);
    }

    @Override
    public void save_success() {
//        setResult();
        finish();
    }

    @Override
    public void save_Fail(String msg) {
        ToastUtility.showToast(msg);
    }

    @Override
    public void queryMarker_success(Tab_Marker marker) {
        tab = marker;
        projectId = marker.getProjectId();
        typeId = marker.getTypeId();
        tvDh.setText(marker.getTsdh());
        tv_wtdh.setText(marker.getWtdh());
        tvTz.setText(marker.getTzd());
        tvFsw.setText(marker.getFsw());
        tvX.setText(String.valueOf(marker.getLatitude()));
        tvY.setText(String.valueOf(marker.getLongitude()));
        tvDmgc.setText(marker.getDmgc());
        tvPxjw.setText(marker.getPxjw());
        tvDldm.setText(marker.getDldm());
        tvMsnd.setText(marker.getMsnd());
        tvTfh.setText(marker.getTfh());
        tvFzlx.setText(marker.getFzlx());
        tvRemarks.setText(marker.getRemarks());
        mPresenter.queryIsPhoto(projectId);
        dimiss();
    }

    @Override
    public void queryMarker_fail(String msg) {
        dimiss();
        showDialog(msg, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public void queryTopType(Tab_Marker marker) {
        dimiss();

        if (marker == null) return;
        tvTz.setText(marker.getTzd());
        tvFsw.setText(marker.getFsw());
        tvDmgc.setText(marker.getDmgc());
        tvPxjw.setText(marker.getPxjw());
        tvDldm.setText(marker.getDldm());
        tvMsnd.setText(marker.getMsnd());
        tvTfh.setText(marker.getTfh());
        tvFzlx.setText(marker.getFzlx());
        tvRemarks.setText(marker.getRemarks());
    }

    @Override
    public void getName(String name) {
        tv_wtdh.setText(name);
    }

    String[] lists = new String[]{"相机", "工程相册"};

    private final String basePath = Environment.getExternalStorageDirectory() + "/guanxian";
    private final int PHOTOCODE = 52;
    private final int PHOTOSELECTCODE = 56;

    @Override
    public void createPhoto() {
        setRightBtn("拍照", R.color.white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MarkerActivity.this, new String[]{
                                    Manifest.permission.CAMERA},
                            0);
                } else {
                    showListDialog(lists, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            switch (which) {
                                case 0: {
                                    mPresenter.queryProject(projectId);
                                    break;
                                }
                                case 1: {
                                    Bundle bundle = PhotoListActivity.newInstance(projectId, true);
                                    Intent intent = new Intent(getContext(), PhotoListActivity.class);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, PHOTOSELECTCODE);
                                    break;
                                }
                            }
                        }
                    });

                }
            }
        });
    }

    @Override
    public void queryProjectName(String project) {
        if (TextUtils.isEmpty(project)){
            ToastUtility.showToast("未查询到项目");
            return;
        }
        PhotoUtil.openZKCamera(MarkerActivity.this,project);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoUtil.TAKE_IMAGE_REQUEST_CODE: {
                if (resultCode == Activity.RESULT_OK){
                    PhotoInfo info = new PhotoInfo();
                    info.setName("");
                    info.setPath(PhotoUtil.fileImagePath.getPath());
                    photos.add(info);
                }
                    break;
            }
            case PHOTOSELECTCODE: {
                if (data==null){
                    return;
                }
                Serializable sera =  data.getSerializableExtra("data");
                if (sera != null) {
                    List<Tab_marker_photo> ps = (List<Tab_marker_photo>) sera;
                    for (Tab_marker_photo p : ps) {
                        PhotoInfo info = new PhotoInfo();
                        info.setName("");
                        info.setPath(p.getPath());
                        photos.add(info);
                    }
                }
            }
        }
    }



}
