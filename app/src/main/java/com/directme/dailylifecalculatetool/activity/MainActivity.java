package com.directme.dailylifecalculatetool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.diectme.dailylifecalculatetool.adapter.ItemAdapter;
import com.directme.dailylifecalculatetool.R;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }
}
