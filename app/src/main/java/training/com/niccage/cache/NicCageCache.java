package training.com.niccage.cache;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.rest.NicCageAPI;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMoviesList;

public class NicCageCache {
    private NicCageDetails nicCageDetails = null;
    private NicCageMoviesList nicCageMovies = null;

    public void getNicCageDetails(final nicCageCacheCallback<NicCageDetails> callback) {
        if (nicCageDetails == null) {
            NicCageAPI.API.getNickCage().enqueue(new Callback<NicCageDetails>() {
                @Override
                public void onResponse(Call<NicCageDetails> call, Response<NicCageDetails> response) {
                    nicCageDetails = response.body();
                    callback.call(nicCageDetails);
                }

                @Override
                public void onFailure(Call<NicCageDetails> call, Throwable t) {

                }
            });
        } else {
            callback.call(nicCageDetails);
        }
    }

    public void getNicCageMovies(final nicCageCacheCallback<NicCageMoviesList> callback) {
        if (nicCageMovies == null) {
            NicCageAPI.API.getNicMovies().enqueue(new Callback<NicCageMoviesList>() {
                @Override
                public void onResponse(Call<NicCageMoviesList> call, Response<NicCageMoviesList> response) {
                    nicCageMovies = response.body();
                    callback.call(nicCageMovies);
                }

                @Override
                public void onFailure(Call<NicCageMoviesList> call, Throwable t) {

                }
            });
        } else {
            callback.call(nicCageMovies);
        }
    }

    public interface nicCageCacheCallback<T> {
        void call(T data);
    }
}
