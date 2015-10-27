package com.diectme.dailylifecalculatetool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.directme.dailylifecalculatetool.R;
import com.directme.dailylifecalculatetool.activity.PeriodizationActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 目录项目的适配器
 *
 * @author WangQiuWei
 * @email jackiewqw@gmail.com
 * @since 2015-10-27
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.NormalTextViewHolder> {


    private final LayoutInflater mLayoutInflater;
    private static Context mContext;
    private String[] mCatelogItem;

    public ItemAdapter(Context context) {
        mCatelogItem = context.getResources().getStringArray(R.array.catelog_item_array);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public NormalTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTextViewHolder(mLayoutInflater.inflate(R.layout.item_text, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalTextViewHolder holder, int position) {
        holder.mTextView.setText(mCatelogItem[position]);
    }

    @Override
    public int getItemCount() {
        return mCatelogItem == null ? 0 : mCatelogItem.length;
    }

    public static class NormalTextViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view)
        TextView mTextView;

        NormalTextViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mContext.startActivity(new Intent(mContext, PeriodizationActivity.class));
                }
            });
        }
    }
}
