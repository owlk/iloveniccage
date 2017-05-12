package training.com.niccage.cache;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import training.com.niccage.rest.NicCageApi;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMovies;
import training.com.niccage.rest.model.SimilarMovies;
import training.com.niccage.rest.model.Video;
import training.com.niccage.rest.model.Videos;

import static training.com.niccage.rest.NicCageApi.API_KEY;

public class NicCageCache {
    private final NicCageApi nicCageApi;

    private NicCageDetails nicCageDetails;
    private Subscriber<NicCageDetails> nicCageDetailsSubscriber;

    private NicCageMovies nicCageMovies;
    private Subscriber<NicCageMovies> nicCageMoviesSubscriber;

    private Map<Integer, SimilarMovies> similarMoviesCache = new HashMap<>();
    private Subscriber<SimilarMovies> similarMoviesSubscriber;

    private Map<Integer, String> trailerUrlsCache = new HashMap<>();

    public NicCageCache(NicCageApi nicCageApi) {
        this.nicCageApi = nicCageApi;
    }

    public void subscribeToNicCageDetails(Subscriber<NicCageDetails> callback) {
        nicCageDetailsSubscriber = callback;
    }

    Subscriber<NicCageDetails> getNicCageDetailsSubscriber() {
        return nicCageDetailsSubscriber;
    }

    public void unsubscribeToNicCageDetails() {
        nicCageDetailsSubscriber = null;
    }

    public void loadNicCageDetails() {
        if (nicCageDetailsSubscriber != null) {
            if (nicCageDetails == null) {
                nicCageApi.getNicCageDetails().enqueue(new Callback<NicCageDetails>() {
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

    public void subscribeToNicCageMoviesList(Subscriber<NicCageMovies> subscriber) {
        nicCageMoviesSubscriber = subscriber;
    }

    Subscriber<NicCageMovies> getNicCageMoviesSubscriber() {
        return nicCageMoviesSubscriber;
    }

    public void unsubscribeToNicCageMoviesList() {
        nicCageMoviesSubscriber = null;
    }

    public void loadNicCageMovies() {
        if (nicCageMoviesSubscriber != null) {
            if (nicCageMovies == null) {
                nicCageApi.getNicCageMovies().enqueue(new Callback<NicCageMovies>() {
                    @Override
                    public void onResponse(Call<NicCageMovies> call, Response<NicCageMovies> response) {
                        nicCageMovies = response.body();
                        nicCageMoviesSubscriber.call(nicCageMovies);
                    }

                    @Override
                    public void onFailure(Call<NicCageMovies> call, Throwable t) {
                    }
                });
            } else {
                nicCageMoviesSubscriber.call(nicCageMovies);
            }
        }
    }

    public void subscribeToSimilarMovies(Subscriber<SimilarMovies> subscriber) {
        similarMoviesSubscriber = subscriber;
    }

    Subscriber<SimilarMovies> getSimilarMoviesSubscriber() {
        return similarMoviesSubscriber;
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
            nicCageApi.getSimilarMovies(movieId, page, API_KEY)
                    .enqueue(new Callback<SimilarMovies>() {
                        @Override
                        public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                            // TODO need to append movies to the cache instead of replacing
                            similarMoviesCache.put(movieId, response.body());
                            similarMoviesSubscriber.call(response.body());
                        }

                        @Override
                        public void onFailure(Call<SimilarMovies> call, Throwable t) { }
                    });
        }
    }

    public Observable<String> getTrailer(final int movieId) {
        Observable<String> apiCall = nicCageApi
                .getVideos(movieId)
                .flatMapObservable(new Func1<Videos, Observable<Video>>() {
                    @Override
                    public Observable<Video> call(Videos videos) {
                        return Observable.from(videos.getResults());
                    }
                })
                .map(new Func1<Video, String>() {
                    @Override
                    public String call(Video video) {
                        return "https://www.youtube.com/watch?v=" + video.getYoutubeKey();
                    }
                })
                .take(1)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String url) {
                        trailerUrlsCache.put(movieId, url);
                    }
                });

        Observable<String> cache = Observable.create(new Action1<Emitter<String>>() {
            @Override
            public void call(Emitter<String> stringEmitter) {
                if(trailerUrlsCache.containsKey(movieId)) {
                    stringEmitter.onNext(trailerUrlsCache.get(movieId));
                }
                stringEmitter.onCompleted();
            }
        }, Emitter.BackpressureMode.BUFFER);

        return Observable.concat(cache, apiCall).first();
    }

    public interface Subscriber<T> {
        void call(T data);
    }
}
