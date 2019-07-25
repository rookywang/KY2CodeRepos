package pri.ky2.ky2coderepos.net;

/**
 * 网络请求返回数据基类
 *
 * @author wangkaiyan
 * @date 2019/07/25
 */
public class NetResponse<T> {

    private String rlt_code;
    private String rlt_msg;
    private T data;

    public String getRlt_code() {
        return rlt_code;
    }

    public void setRlt_code(String mRlt_code) {
        rlt_code = mRlt_code;
    }

    public String getRlt_msg() {
        return rlt_msg;
    }

    public void setRlt_msg(String mRlt_msg) {
        rlt_msg = mRlt_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T mData) {
        data = mData;
    }

    @Override
    public String toString() {
        return "NetResponse{" +
                "rlt_code='" + rlt_code + '\'' +
                ", rlt_msg='" + rlt_msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
