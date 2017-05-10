package training.com.niccage.rest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMovies;
import training.com.niccage.rest.model.SimilarMovies;

public interface NicCageAPI {
    NicCageAPI API = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NicCageAPI.class);

    String API_KEY = "5d0d42ca46c6abc45ddc0db8f6eac7cb";

    @GET("person/2963?api_key=" + API_KEY)
    Call<NicCageDetails> getNickCage();

    @GET("person/2963/movie_credits?api_key=" + API_KEY)
    Call<NicCageMovies> getNicMovies();

    @GET("movie/{movie_id}/similar")
    Call<SimilarMovies> getSimilarMovies(
            @Path("movie_id") int movieId,
            @Query("page") Integer page,
            @Query("api_key") String apiKey);
}
