package com.directme.dailylifecalculatetool.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.directme.dailylifecalculatetool.api.ToolApi;
import com.directme.dailylifecalculatetool.model.BaseModel;
import com.directme.dailylifecalculatetool.model.HouseLoan;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 网络任务
 *
 * @author WangQiuWei
 * @email jackiewqw@gmail.com
 * @since 2015-10-29
 */
public class NetTask{

    static ExecutorService threadPool = Executors.newFixedThreadPool(5);
    static Handler mHandler = new Handler(Looper.getMainLooper());
    static Gson mGson = new Gson();

    public interface ResponseListener<T extends BaseModel>{
        void onGetResponse(T result);
        void onError(String errorMsg);
    }

    private ResponseListener  mResponseListener = null;

    public <T extends BaseModel> void execute(final Type tp, ResponseListener listener) {
        mResponseListener = listener;

        Runnable runable = new Runnable() {
            @Override
            public void run() {
                try {

                    String result = requestGet();
                    //onGetResponse((T) mGson.fromJson(result, tp));
                    final T t=(T) mGson.fromJson(result, T.class);
                    if( mResponseListener != null ){
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mResponseListener.onGetResponse(t);
                            }
                        });
                    }
                } catch (Exception e) {
                    onGetError(e.getMessage());
                }
            }
        };

        threadPool.execute(runable);
    }

    private void onGetError(final String errMsg){
        if( mResponseListener != null ){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mResponseListener.onError(errMsg);
                }
            });
        }
    }

//    private void onGetResponse(final T t){
//
//    }

    private String requestGet() throws Exception{

        HttpURLConnection conn = null;
        try {
            URL mURL = new URL(ToolApi.HOST);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                return getStringFromInputStream(is);
            }else{
                throw new Exception("connect status error");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 根据流返回一个字符串信息         *
     *
     * @param is
     * @return
     * @throws IOException
     */
    private String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 模板代码 必须熟练
        byte[] buffer = new byte[1024];
        int len = -1;
        // 一定要写len=is.read(buffer)
        // 如果while((is.read(buffer))!=-1)则无法将数据写入buffer中
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        is.close();
        String state = os.toString();// 把流中的数据转换成字符串,采用的编码是utf-8(模拟器默认编码)
        os.close();
        return state;
    }
}
