package training.com.niccage.cache;

import android.util.LruCache;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.rest.NicCageAPI;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMoviesList;
import training.com.niccage.rest.model.SimilarMovies;

import static android.R.attr.id;

public class NicCageCache {
    private NicCageDetails nicCageDetails = null;
    private NicCageMoviesList nicCageMovies = null;
    private LruCache<Integer, SimilarMovies> similarMovies = new LruCache<>(1024 * 1024 * 4);

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

    public void getSimilarMovies(Integer movieId, Integer page, final nicCageCacheCallback<SimilarMovies> callback) {
        SimilarMovies similarMovies = this.similarMovies.get(id);

        if (similarMovies == null || similarMovies.getPage() < page) {
            // get all pages missing
        } else {
            // be normal
            callback.call(similarMovies);
        }
    }

    public interface nicCageCacheCallback<T> {
        void call(T data);
    }
}
