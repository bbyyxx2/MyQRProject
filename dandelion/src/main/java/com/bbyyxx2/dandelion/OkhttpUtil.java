package com.bbyyxx2.dandelion;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpUtil {

    private OkHttpClient client = new OkHttpClient();
    private FormBody.Builder formBody = new FormBody.Builder();
    private Request.Builder request = new Request.Builder();
    private final Gson gson = new Gson();
    private Class tClass;

    private String url;
    private Context context;
    /**
     * 蒲公英2.0api
     */
    private String baseUrl = "https://www.pgyer.com/apiv2/";

    private static OkhttpUtil instance = null;

    public static synchronized OkhttpUtil getInstance(Context context){
        if (instance == null){
            instance = new OkhttpUtil(context);
        }
        return instance;
    }

    public OkhttpUtil(Context context) {
        this.context = context;
    }

    public OkhttpUtil addBody(String key, String value){
        formBody.add(key, value);
        Log.d(getClass().getSimpleName(),"addBody: " + key + ":" + value);
        return this;
    }

    public OkhttpUtil setUrl(String url){
        this.url = baseUrl + url;
        request.url(this.url);
        Log.d(getClass().getSimpleName(), "url:" + url);
        return this;
    }

    public OkhttpUtil setUrl(String url, boolean isAll){
        if (isAll){
            this.url = url;
        } else {
            this.url = baseUrl + url;
        }
        request.url(this.url);
        return this;
    }

    /**
     * 设置需要转化的对象
     * null时为String
     * @param tClass
     * @return
     */
    public OkhttpUtil setConvert(Class tClass) {
        this.tClass = tClass;
        return this;
    }

    public OkhttpUtil setConvert(Object o) {
        this.tClass = o.getClass();
        return this;
    }

    public void buildRequest(String type, OnRequest onRequest){
        client.newCall(request.method(type, formBody.build()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onRequest.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String body = response.body().string();
                    Log.d(getClass().getSimpleName(), "onResponse:" + body);
                    if (tClass != null) {
                        onRequest.onResponse(response.code(), gson.fromJson(body, tClass));
                    } else {
                        onRequest.onResponse(response.code(), body);
                    }
                }else {
                    onRequest.onErrorCode(response.code());
                }
            }
        });
    }

    public interface OnRequest<T> {
        void onFailure(Exception e);

        void onResponse(int code, T t);

        void onErrorCode(int code);
    }
}
