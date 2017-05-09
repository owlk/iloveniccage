package training.com.movieapp.rest;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import training.com.movieapp.BuildConfig;
import training.com.movieapp.rest.model.NicolasTrivia;

public interface MoveDbAPI {

    @GET("person/2963?api_key=" + BuildConfig.API_KEY)
    Call<NicolasTrivia> getNickCage();

}
