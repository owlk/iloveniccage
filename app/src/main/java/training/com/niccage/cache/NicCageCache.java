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
    private NicCageDetails nicCageDetails;
    private Subscriber<NicCageDetails> nicCageDetailsSubscriber;

    private NicCageMoviesList nicCageMoviesList;
    private Subscriber<NicCageMoviesList> nicCageMoviesListSubscriber;

    private LruCache<Integer, SimilarMovies> similarMoviesCache = new LruCache<>(1024 * 1024 * 4);
    private Subscriber<SimilarMovies> similarMoviesSubscriber;

    public void subscribeToNicCageDetails(Subscriber<NicCageDetails> callback) {
        nicCageDetailsSubscriber = callback;
    }

    public void ubsubscribeToNicCageDetails() {
        nicCageDetailsSubscriber = null;
    }

    public void loadNicCageDetails() {
        if (nicCageDetailsSubscriber != null) {
            if (nicCageDetails == null) {
                NicCageAPI.API.getNickCage().enqueue(new Callback<NicCageDetails>() {
                    @Override
                    public void onResponse(Call<NicCageDetails> call, Response<NicCageDetails> response) {
                        nicCageDetails = response.body();
                        nicCageDetailsSubscriber.call(nicCageDetails);
                    }

                    @Override
                    public void onFailure(Call<NicCageDetails> call, Throwable t) {
                    }
                });
            } else {
                nicCageDetailsSubscriber.call(nicCageDetails);
            }
        }
    }

    public void subscribeToNicCageMoviesList(Subscriber<NicCageMoviesList> subscriber) {
        nicCageMoviesListSubscriber = subscriber;
    }

    public void unsubscribeToNicCageMoviesList() {
        nicCageMoviesListSubscriber = null;
    }

    public void loadNicCageMoviesList() {
        if (nicCageMoviesListSubscriber != null) {
            if (nicCageMoviesList == null) {
                NicCageAPI.API.getNicMovies().enqueue(new Callback<NicCageMoviesList>() {
                    @Override
                    public void onResponse(Call<NicCageMoviesList> call, Response<NicCageMoviesList> response) {
                        nicCageMoviesList = response.body();
                        nicCageMoviesListSubscriber.call(nicCageMoviesList);
                    }

                    @Override
                    public void onFailure(Call<NicCageMoviesList> call, Throwable t) {
                    }
                });
            } else {
                nicCageMoviesListSubscriber.call(nicCageMoviesList);
            }
        }
    }

    public void subscribeToSimilarMovies(Subscriber<SimilarMovies> subscriber) {
        similarMoviesSubscriber = subscriber;
    }

    public void unsubscribeToSimilarMovies() {
        similarMoviesSubscriber = null;
    }

    public void loadSimilarMovies(Integer movieId) {
        if (similarMoviesSubscriber != null) {
            SimilarMovies similarMovies = similarMoviesCache.get(movieId);
            if (similarMovies != null) {
                similarMoviesSubscriber.call(similarMovies);
            } else {
                loadMoreSimilarMovies(movieId);
            }
        }
    }

    public void loadMoreSimilarMovies(final Integer movieId) {
        if (similarMoviesSubscriber != null) {
            SimilarMovies similarMovies = similarMoviesCache.get(movieId);

            int page = similarMovies == null ? 1 : similarMovies.getPage() + 1;
            NicCageAPI.API
                    .getSimilarMovies(movieId, page, BuildConfig.API_KEY)
                    .enqueue(new Callback<SimilarMovies>() {
                        @Override
                        public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                            similarMoviesCache.put(movieId, response.body());
                            similarMoviesSubscriber.call(response.body());
                        }

                        @Override
                        public void onFailure(Call<SimilarMovies> call, Throwable t) { }
                    });
        }
    }

    public interface Subscriber<T> {
        void call(T data);
    }
}
