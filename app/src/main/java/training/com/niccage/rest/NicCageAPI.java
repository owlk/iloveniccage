package training.com.niccage.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import training.com.niccage.BuildConfig;
import training.com.niccage.rest.model.NicCageDetails;

public interface NicCageAPI {
    @GET("person/2963?api_key=" + BuildConfig.API_KEY)
    Call<NicCageDetails> getNickCage();
}
