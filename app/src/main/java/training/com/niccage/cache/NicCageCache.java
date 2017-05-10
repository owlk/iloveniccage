package training.com.niccage.cache;

import android.util.LruCache;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.BuildConfig;
import training.com.niccage.rest.NicCageAPI;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMoviesList;
import training.com.niccage.rest.model.SimilarMovies;

public class NicCageCache {
    private NicCageDetails nicCageDetails = null;
    private NicCageMoviesList nicCageMovies = null;

    private LruCache<Integer, SimilarMovies> similarMoviesCache = new LruCache<>(1024 * 1024 * 4);
    private NicCageCacheCallback<SimilarMovies> similarMoviesListener;

    public void getNicCageDetails(final NicCageCacheCallback<NicCageDetails> callback) {
        if (nicCageDetails == null) {
            NicCageAPI.API.getNickCage().enqueue(new Callback<NicCageDetails>() {
                @Override
                public void onResponse(Call<NicCageDetails> call, Response<NicCageDetails> response) {
                    nicCageDetails = response.body();
                    callback.call(nicCageDetails);
                }

                @Override
                public void onFailure(Call<NicCageDetails> call, Throwable t) { }
            });
        } else {
            callback.call(nicCageDetails);
        }
    }

    public void getNicCageMovies(final NicCageCacheCallback<NicCageMoviesList> callback) {
        if (nicCageMovies == null) {
            NicCageAPI.API.getNicMovies().enqueue(new Callback<NicCageMoviesList>() {
                @Override
                public void onResponse(Call<NicCageMoviesList> call, Response<NicCageMoviesList> response) {
                    nicCageMovies = response.body();
                    callback.call(nicCageMovies);
                }

                @Override
                public void onFailure(Call<NicCageMoviesList> call, Throwable t) { }
            });
        } else {
            callback.call(nicCageMovies);
        }
    }

    public void subscribe(final Integer movieId, NicCageCacheCallback<SimilarMovies> callback) {
        similarMoviesListener = callback;

        final SimilarMovies similarMovies = similarMoviesCache.get(movieId);
        if (similarMovies == null) {
            NicCageAPI.API
                    .getSimilarMovies(movieId, 1, BuildConfig.API_KEY)
                    .enqueue(new Callback<SimilarMovies>() {
                        @Override
                        public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                            similarMoviesCache.put(movieId, response.body());
                            similarMoviesListener.call(response.body());
                        }

                        @Override
                        public void onFailure(Call<SimilarMovies> call, Throwable t) { }
                    });
        } else {
            similarMoviesListener.call(similarMovies);
        }
    }

    public void loadMoreSimilarMovies(final Integer movieId) {
        if (similarMoviesListener != null) {
            SimilarMovies similarMovies = similarMoviesCache.get(movieId);

            NicCageAPI.API
                    .getSimilarMovies(movieId, similarMovies.getPage() + 1, BuildConfig.API_KEY)
                    .enqueue(new Callback<SimilarMovies>() {
                        @Override
                        public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                            similarMoviesCache.put(movieId, response.body());
                            similarMoviesListener.call(response.body());
                        }

                        @Override
                        public void onFailure(Call<SimilarMovies> call, Throwable t) { }
                    });
        }
    }

    public interface NicCageCacheCallback<T> {
        void call(T data);
    }
}
