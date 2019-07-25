package pri.ky2.ky2coderepos.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * 获取 Gson 实例
 *
 * @author wangkaiyan
 * @date 2019/07/25
 */
public class GsonUtils {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                    if (src == src.longValue()) {
                        return new JsonPrimitive(src.longValue());
                    }
                    return new JsonPrimitive(src);
                }
            }).create();


    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
