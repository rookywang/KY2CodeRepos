package pri.ky2.ky2coderepos.net;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ActivityUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import pri.ky2.ky2coderepos.interfaces.ILoadingView;
import pri.ky2.ky2coderepos.utils.ErrorToMsgUtils;
import pri.ky2.ky2coderepos.utils.GsonUtils;
import pri.ky2.ky2coderepos.utils.ToastUtils;

/**
 * 网络请求观察者
 *
 * @param <T> 响应数据 Model
 * @author wangkaiyan
 * @date 2019/07/25
 */
public abstract class NetObserver<T> implements SingleObserver<NetResponse> {

    /**
     * 网络数据的 TAG
     */
    private static final String TAG = "netlog";

    /**
     * 网络请求成功响应码
     * TODO：网络请求成功响应码得改
     */
    private static final String NET_SUCCESS = "123456";

    /**
     * 要解析成的数据类型
     */
    private Class<T> mTClass;

    /**
     * 页面与网络请求管理
     */
    private ILoadingView mILoadingView;

    /**
     * 页面加载框是否显示
     */
    private boolean isLoadingDialogShowing;

    /**
     * 字段（返回数据 data 部分只有单个字段）的名称
     */
    private String mFieldKey;

    /**
     * Header 键值对
     */
    private HashMap<String, String> mHeaders = new HashMap<>();

    /**
     * Body 键值对
     */
    private HashMap<String, String> mParams = new HashMap<>();

    public NetObserver(Class<T> cls, ILoadingView iLoadingView) {
        mTClass = cls;
        mILoadingView = iLoadingView;
    }

    /**
     * 网络请求显示加载框
     *
     * @return
     */
    public boolean showRequestLoadingDialog() {
        return true;
    }

    /**
     * 请求成功
     *
     * @param t 解析的对象
     */
    public abstract void onDataSuccess(T t);

    /**
     * 请求成功，如果解析的数据是 List，可重写此方法
     *
     * @param list 解析的结果 List
     */
    public void onDataSuccess(List<T> list) {
    }

    /**
     * 请求成功，如果解析单个字段可重写此方法，比如 data":{"sign":"abc"}
     *
     * @param value 解析的结果字段值
     */
    public void onDataSuccess(String value) {
    }

    /**
     * 请求成功，返回原始响应数据
     *
     * @param response betResponse
     * @param data     betResponse 的 data 部分
     */
    public void onOriginDataSuccess(String response, String data) {
    }

    /**
     * 请求失败
     *
     * @param code 错误码
     * @param msg  错误信息
     */
    public void onError(String code, String msg) {
        Log.i(TAG, "onError code: " + code + " msg: " + msg);
        if (mILoadingView != null && mILoadingView.isClosed()) {
            return;
        }
        if (mILoadingView != null) {
            try {
                String topName = ActivityUtils.getTopActivity().getClass().getSimpleName();
                if (!TextUtils.isEmpty(topName) && !topName.equals(mILoadingView.getUIName())) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ToastUtils.show(msg);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mILoadingView != null) {
            mILoadingView.addNetRequest(d);
        }
        if (mILoadingView != null && !mILoadingView.isClosed() && showRequestLoadingDialog()) {
            mILoadingView.showLoadingDialog("");
            isLoadingDialogShowing = true;
        }
    }

    @Override
    public void onSuccess(NetResponse netResponse) {
        Log.i(TAG, "NetResponse = " + netResponse);
        handleResponse(netResponse);
        requestEnd();
    }

    @Override
    public void onError(Throwable e) {
        try {
            handleError(null, e);
            requestEnd();
        } catch (Exception exception) {
            e.printStackTrace();
        }
    }

    /**
     * 处理网络请求成功情况
     *
     * @param netResponse 返回数据
     */
    private void handleResponse(NetResponse netResponse) {
        if (handleError(netResponse, null)) {
            return;
        }
        String data = GsonUtils.toJson(netResponse.getData());
        // 如果 data 为空，返回数据为空
        if (TextUtils.isEmpty(data)) {
            onDataSuccess((T) null);
            return;
        }
        // 如果 data 是数组，返回一个 List
        // 如果不是，返回一个 Object
        if (data.startsWith("[")) {
            onDataSuccess(JSON.parseArray(data, mTClass));
        } else {
            onDataSuccess(JSON.parseObject(data, mTClass));
        }
        // 如果 data 是单个字段
        if (!TextUtils.isEmpty(mFieldKey)) {
            JSONObject jsonObject = JSONObject.parseObject(data);
            String value = jsonObject.getString(mFieldKey);
            onDataSuccess(value);
        }
        // 返回整个响应数据以及其中的 data 部分
        onOriginDataSuccess(JSON.toJSONString(netResponse), data);
    }

    /**
     * 处理网络请求失败情况
     *
     * @param netResponse 返回数据
     * @param e           异常
     * @return true：有异常
     */
    private boolean handleError(NetResponse netResponse, Throwable e) {
        if (mILoadingView != null && mILoadingView.isClosed()) {
            Log.i(TAG, "UI is closed");
            return true;
        }
        if (netResponse != null) {
            String responseCode = netResponse.getRlt_code();
            if (NET_SUCCESS.equals(responseCode)) {
                return false;
            }
            String responseMsg = netResponse.getRlt_msg();
            String errMsg = TextUtils.isEmpty(responseMsg) ? "服务器异常" + responseCode : responseMsg;
            //TODO：这里可以做一个登录失效的判断
            onError(responseCode, errMsg);
            return true;
        }
        if (e != null) {
            Log.e(TAG, "exception: " + e);
            NetResponse errResponse = ErrorToMsgUtils.throwableToMsg(e);
            onError(errResponse.getRlt_code(), errResponse.getRlt_code());
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * 请求结束，无论成功失败，都会调用
     */
    private void requestEnd() {
        if (mILoadingView != null && isLoadingDialogShowing) {
            mILoadingView.hideLoadingDialog();
        }
    }

    /**
     * 增加 Header
     */
    public NetObserver<T> addHeader(String key, String value) {
        if (mHeaders == null) {
            mHeaders = new HashMap<>();
        }
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mHeaders.put(key, value);
        }
        return this;
    }

    /**
     * 增加参数
     */
    public NetObserver<T> addParam(String key, String value) {
        if (mParams == null) {
            mParams = new HashMap<>();
        }
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            mParams.put(key, value);
        }
        return this;
    }

    public NetObserver<T> addParam(String key, int value) {
        addParam(key, String.valueOf(value));
        return this;
    }

    public void setParamMap(HashMap<String, String> map) {
        mParams = map;
    }

    /**
     * 当返回数据 data 中只有一个字段时设置字段名称
     *
     * @param key 要解析的字段
     * @return
     */
    public NetObserver setKey(String key) {
        mFieldKey = key;
        return this;
    }

    /**
     * GET 方法
     */
    public void get(String url) {
        NetRequest.doGet(url, this);
    }

    /**
     * POST 方法
     */
    public void post(String url) {
        addToken();
        NetRequest.doPost(url, mHeaders, mParams, this);
    }

    /**
     * POST 方法-Json
     *
     * @param url
     */
    public void postJson(String url) {
        addToken();
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(mParams));
        NetRequest.doPostJson(url, mHeaders, jsonObject.toJSONString(), this);
    }

    /**
     * POST 方法-上传多文件
     */
    public void uploadMultiFiles(String url, List<File> files) {
        addToken();
        NetRequest.doUploadMultiFiles(url, mParams, files, this);
    }

    /**
     * 上传单文件
     */
    public void upload(String url, File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        uploadMultiFiles(url, files);
    }

    /**
     * 将 token 增加到请求参数里面
     */
    private void addToken() {
        if (null == mParams) {
            mParams = new HashMap<>(10);
        }
        if (!mParams.containsKey("access_token")) {
            String token = "";
            // TODO：这里要从数据库获取 Token
            if (!TextUtils.isEmpty(token)) {
                mParams.put("access_token", token);
            }
        }
    }

}
