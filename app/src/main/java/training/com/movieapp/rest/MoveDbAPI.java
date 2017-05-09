package training.com.movieapp.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import training.com.movieapp.BuildConfig;
import training.com.movieapp.rest.model.NicolasTrivia;

public interface MoveDbAPI {
    @GET("person/2963?api_key=" + BuildConfig.API_KEY)
    Call<NicolasTrivia> getNickCage();
}
