package pri.ky2.ky2coderepos.net;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Retrofit 请求方法
 *
 * @author wangkaiyan
 * @date 2019/07/25
 */
public interface ApiService {

    /**
     * GET 请求
     */
    @GET
    Single<NetResponse> get(@Url String url);

    /**
     * GET 请求
     */
    @GET
    Single<ResponseBody> getResponse(@Url String url);

    /**
     * POST 请求-表单
     */
    @FormUrlEncoded
    @POST
    Single<NetResponse> post(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> params);

    /**
     * POST 请求-上传文件
     */
    @Multipart
    @POST
    Single<NetResponse> uploadMultiFiles(@Url String url, @QueryMap() Map<String, String> params, @PartMap() Map<String, RequestBody> files);

    /**
     * post请求-json
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Single<NetResponse> postJson(@Url String url, @HeaderMap Map<String, String> headers, @Body RequestBody body);
}
