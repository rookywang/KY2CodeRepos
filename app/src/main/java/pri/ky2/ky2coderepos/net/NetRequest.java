package pri.ky2.ky2coderepos.net;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pri.ky2.ky2coderepos.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 配置
 *
 * @author wangkaiyan
 * @date 2019/07/25
 */
public class NetRequest {

    /**
     * 网络日志标签
     */
    private static final String TAG = "netlog";

    /**
     * 网络请求超时时间，15s
     */
    private static final int TIME_OUT_SECOND = 15;

    /**
     * 主机名
     */
    private static final String BASE_URL = BuildConfig.HOST;

    /**
     * Retrofit 实例
     */
    private static Retrofit mRetrofit;

    /**
     * 获取 Retrofit  实例
     */
    private static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (NetRequest.class) {
                if (mRetrofit == null) {
                    mRetrofit = createRetrofit();
                }
            }
        }
        return mRetrofit;
    }

    /**
     * 创建 Retrofit 的实例
     */
    private static Retrofit createRetrofit() {
        // 请求头拦截器
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originRequest = chain.request();
                Request.Builder requestBuilder = originRequest.newBuilder()
                        .addHeader("Accept-Encoding", "gzip")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .method(originRequest.method(), originRequest.body());
                Request newRequest = requestBuilder.build();
                return chain.proceed(newRequest);
            }
        };
        // 日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                // TODO：这里可以加上是否是 Debug 的检验
                if (!TextUtils.isEmpty(message)) {
                    Log.i(TAG, message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 配置 OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECOND, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        // TODO：这里可以加上线上环境证书校验

        // 配置 Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    /**
     * 取消所有网络请求
     */
    public static void cancel(List<Disposable> disposables) {
        if (disposables == null || disposables.isEmpty()) {
            return;
        }
        for (Disposable d : disposables) {
            if (!d.isDisposed()) {
                d.dispose();
                Log.i(TAG, "cancel request");
            }
        }
        disposables.clear();
    }

    /**
     * GET 方法
     */
    public static <T> void doGet(String url, NetObserver<T> netObserver) {
        checkRetrofitNonNull(netObserver);
        getRetrofit().create(ApiService.class)
                .<T>get(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netObserver);
    }

    /**
     * GET 方法-获取原始数据流
     */
    public static void doGetResponse(String url, SingleObserver<ResponseBody> netObserver) {
        checkRetrofitNonNull(netObserver);
        getRetrofit().create(ApiService.class)
                .getResponse(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netObserver);
    }

    /**
     * POST 方法
     */
    public static <T> void doPost(String url, Map<String, String> headers, Map<String, String> params, NetObserver<T> netObserver) {
        checkRetrofitNonNull(netObserver);
        getRetrofit().create(ApiService.class)
                .<T>post(url, headers, params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netObserver);
    }

    /**
     * POST 方法-上传多个文件
     */
    public static <T> void doUploadMultiFiles(String url, Map<String, String> headers, List<File> files, NetObserver<T> netObserver) {
        Map<String, RequestBody> fileMap = new HashMap<>();
        if (files != null && files.size() > 0) {
            for (File file : files) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                fileMap.put("file" + "\";filename=\"" + file.getName(), requestBody);
            }
        } else {
            //没有文件上传的时候构造一个空body
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), "");
            fileMap.put("", requestBody);
        }
    }

    /**
     * POST 方法-Json
     */
    public static <T> void doPostJson(String url, Map<String, String> headers, String json, NetObserver<T> netObserver) {
        checkRetrofitNonNull(netObserver);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        getRetrofit().create(ApiService.class)
                .<T>postJson(url, headers, body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(netObserver);
    }

    /**
     * 检查 Retrofit 实例是否为空
     */
    private static <T> void checkRetrofitNonNull(SingleObserver<T> singleObserver) {
        if (getRetrofit() == null) {
            singleObserver.onError(new Exception("no retrofit object"));
            return;
        }
    }
}
