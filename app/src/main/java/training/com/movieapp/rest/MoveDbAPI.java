package training.com.movieapp.rest;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import training.com.movieapp.BuildConfig;
import training.com.movieapp.rest.model.NicolasTrivia;

public class MoveDbAPI {

    private final Gson gson = new Gson();
    private final OkHttpClient client = new OkHttpClient();

    public NicolasTrivia getNickCage() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/person/2963?api_key=" + BuildConfig.API_KEY)
                .build();

        Response response = client.newCall(request).execute();

        NicolasTrivia nicolasTrivia = gson.fromJson(response.body().string(), NicolasTrivia.class);

        return nicolasTrivia;
    }

}
