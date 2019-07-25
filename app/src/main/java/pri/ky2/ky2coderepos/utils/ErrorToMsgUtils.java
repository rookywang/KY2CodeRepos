package pri.ky2.ky2coderepos.utils;

import android.accounts.NetworkErrorException;

import com.alibaba.fastjson.JSONException;
import com.blankj.utilcode.util.NetworkUtils;
import com.google.gson.JsonParseException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import pri.ky2.ky2coderepos.net.NetResponse;
import retrofit2.HttpException;

/**
 * 错误信息转换工具
 *
 * @author wangkaiyan
 * @date 2019/07/25
 */
public class ErrorToMsgUtils {
    /**
     * 把网络请求中抛出的错误转换成msg以便回调给上层
     * TODO: 提示码与提示信息得改
     */
    public static NetResponse throwableToMsg(Throwable e) {
        NetResponse response = new NetResponse();
        if (!NetworkUtils.isConnected() || e instanceof UnknownHostException || e instanceof NetworkErrorException) {
            //网络不可用
            response.setRlt_code("100");
            response.setRlt_msg("网络不可用");
        } else if (e instanceof HttpException || e instanceof java.net.ConnectException || e instanceof SocketException) {
            //连接异常
            response.setRlt_code("200");
            response.setRlt_msg("连接异常");
        } else if (e instanceof SocketTimeoutException) {
            //连接超时
            response.setRlt_code("300");
            response.setRlt_msg("连接超时");
        } else if (e instanceof SSLHandshakeException) {
            //SSL 异常
            response.setRlt_code("400");
            response.setRlt_msg("SSL 异常");
        } else if (e instanceof JsonParseException || e instanceof JSONException) {
            //解析异常，说明服务器返回内容错误，显示为服务器异常
            response.setRlt_code("500");
            response.setRlt_msg("解析异常");
        } else {
            response.setRlt_code("000");
            response.setRlt_msg(e.getMessage());
        }
        return response;
    }
}
