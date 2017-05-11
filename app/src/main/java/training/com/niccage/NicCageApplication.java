package training.com.niccage;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.NicCageApi;

public class NicCageApplication extends Application {
    private final NicCageCache cache = new NicCageCache(new Retrofit.Builder()
            .baseUrl(NicCageApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(NicCageApi.class));

    public NicCageCache getCache() {
        return cache;
    }
}
