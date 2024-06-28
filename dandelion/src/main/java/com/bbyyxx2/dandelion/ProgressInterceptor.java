package com.bbyyxx2.dandelion;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * 描述: 下载进度拦截器
 */
@Deprecated
public class ProgressInterceptor implements Interceptor {

    private OnProgress onProgress;

    public ProgressInterceptor(OnProgress onProgress) {
        this.onProgress = onProgress;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        long contentLengthLong = originalResponse.body().contentLength();
        long downloaded = 0L;
        originalResponse.body().byteStream().reset(); // 重置流位置以便多次读取
        StringBuilder progress = new StringBuilder();
        while (downloaded < contentLengthLong) {
            byte[] buffer = new byte[2048];
            int read = originalResponse.body().byteStream().read(buffer);
            if (read != -1) {
                downloaded += read;
                progress.append("#").append(downloaded).append("/").append(contentLengthLong);
                onProgress.onProgress((int) (downloaded * 100 / contentLengthLong));
            }
        }
        System.out.println(progress.toString()); // 打印下载进度
        return originalResponse;
    }

    interface OnProgress {
        void onProgress(int progress);
    }
}
