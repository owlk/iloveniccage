package training.com.movieapp.rest;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import training.com.movieapp.BuildConfig;

public class MoveDbAPI {

    private OkHttpClient client = new OkHttpClient();

    public ResponseBody getNickCage() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/person/2963?api_key=" + BuildConfig.API_KEY)
                .build();

        Response response = client.newCall(request).execute();

        return response.body();
    }

}
