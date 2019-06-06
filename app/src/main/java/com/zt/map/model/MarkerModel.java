package com.zt.map.model;

import com.zt.map.contract.MarkerContract;
import com.zt.map.entity.db.tab.Tab_Marker;

import cn.faker.repaymodel.mvp.BaseMVPModel;
import cn.faker.repaymodel.util.db.DBThreadHelper;
import cn.faker.repaymodel.util.db.litpal.LitPalUtils;

public class MarkerModel extends BaseMVPModel implements MarkerContract.Model {
    @Override
    public void queryMarker(final long id, final CommotListener<Tab_Marker> listener) {
        DBThreadHelper.startThreadInPool(new DBThreadHelper.ThreadCallback<Tab_Marker>() {

            @Override
            protected Tab_Marker jobContent() throws Exception {
                return LitPalUtils.selectsoloWhere(Tab_Marker.class,"id = ? ",String.valueOf(id));
            }

            @Override
            protected void jobEnd(Tab_Marker marker) {
                listener.result(marker);
            }
        });
    }
}
