package com.directme.dailylifecalculatetool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.directme.dailylifecalculatetool.adapter.ItemAdapter;
import com.directme.dailylifecalculatetool.R;
import com.directme.dailylifecalculatetool.model.BaseModel;
import com.directme.dailylifecalculatetool.model.HouseLoan;
import com.directme.dailylifecalculatetool.net.NetTask;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面，包含各个计算的目录入口
 *
 * @author WangQiuWei
 * @email jackiewqw@gmail.com
 * @since 2015-10-27
 */
public class MainActivity extends Activity{

    @Bind(R.id.catalog_view)
    RecyclerView mCatalogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mCatalogView.setLayoutManager(new LinearLayoutManager(this));
        mCatalogView.setAdapter(new ItemAdapter(this));

        new NetTask().execute(HouseLoan.class, new NetTask.ResponseListener<HouseLoan>() {
            @Override
            public void onGetResponse(HouseLoan loan) {

                Toast.makeText(MainActivity.this, "当前商贷的利率为:"+loan.businessRate+"; 当前公积金贷款的利率为:"+loan.fundRate, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }
}
