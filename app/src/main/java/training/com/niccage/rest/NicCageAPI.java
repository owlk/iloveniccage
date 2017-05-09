package training.com.niccage.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import training.com.niccage.BuildConfig;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicMovies;

public interface NicCageAPI {
    @GET("person/2963?api_key=" + BuildConfig.API_KEY)
    Call<NicCageDetails> getNickCage();

    @GET("person/2963/movie_credits?api_key=" + BuildConfig.API_KEY)
    Call<NicMovies> getNicMovies();


}
