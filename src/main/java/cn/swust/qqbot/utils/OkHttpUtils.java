package cn.swust.qqbot.utils;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class OkHttpUtils {
    private static final OkHttpClient client = new OkHttpClient();
    public static Response DoGet(String url) throws IOException {
        Request request = new Request.Builder()
                .header("referer", "http://cocfz.com/")
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public static void DoPost(String url, RequestBody requestBody) {
        Response response;
        Request request;
        request = new Request.Builder()
                .post(requestBody)
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                assert response.body() != null;
                System.out.println(response.body().string());
            }
        });
    }
}
