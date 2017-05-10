package training.com.niccage.rest;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import training.com.niccage.BuildConfig;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMoviesList;
import training.com.niccage.rest.model.SimilarMovies;

public interface NicCageAPI {

    NicCageAPI API = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NicCageAPI.class);

    @GET("person/2963?api_key=" + BuildConfig.API_KEY)
    Call<NicCageDetails> getNickCage();

    @GET("person/2963/movie_credits?api_key=" + BuildConfig.API_KEY)
    Call<NicCageMoviesList> getNicMovies();

    @GET("movie/{movie_id}/similar")
    Call<SimilarMovies> getSimilarMovies(@Path("movie_id") int movieId, @Query("page") Integer page, @Query("api_key") String apiKey);


}
