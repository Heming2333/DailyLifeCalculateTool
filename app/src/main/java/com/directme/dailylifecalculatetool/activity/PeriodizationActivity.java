package com.directme.dailylifecalculatetool.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.directme.dailylifecalculatetool.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 分期购的计算界面
 */
public class PeriodizationActivity extends Activity {

    @Bind(R.id.edit_total)
    EditText mTotal;

    @Bind(R.id.edit_periodization)
    EditText mPeriodNum;

    @Bind(R.id.edit_rate)
    EditText mRate;

    @Bind(R.id.edit_fee)
    EditText mFee;

    @Bind(R.id.text_each)
    TextView mEachPay;

    @Bind(R.id.text_payment)
    TextView mRealPayment;

    @Bind(R.id.text_save)
    TextView mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodization);

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);
    }

    @OnClick(R.id.calculate)
    public void onCaclulate(){

        double total = getNumValue(mTotal);
        int period = Integer.valueOf(mPeriodNum.getText().toString());
        double fee = getNumValue(mFee);
        double rate = getNumValue(mRate);

        double eachPay = total/period;
        double realPay = total - (total/period)*((rate*0.01)/12)*((period+1)*period/2)+period*fee;
        double save = total - realPay;

        mEachPay.setText(String.format("%.2f", eachPay));
        mRealPayment.setText(String.format("%.2f", realPay));
        mSave.setText(String.format("%.2f", save));

        float loanRate = 0.065f;
        int loanTotal = 630000;
        int loanNum=360;

        double monthPay = loanTotal*loanRate/12*Math.pow(1+loanRate/12, loanNum)/(Math.pow(1+loanRate/12, loanNum)-1);
        mSave.setText(String.format("%.2f", monthPay));

    }

    public double getNumValue(EditText editView){
        if( editView == null || editView.getText().length() == 0  ){
            return 0;
        }

        return Double.valueOf(editView.getText().toString());
    }
}
