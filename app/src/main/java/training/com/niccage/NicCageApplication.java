package training.com.niccage;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.NicCageApis;

public class NicCageApplication extends Application {
    private final NicCageCache cache = new NicCageCache(new Retrofit.Builder()
            .baseUrl(NicCageApis.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NicCageApis.class));

    public NicCageCache getCache() {
        return cache;
    }
}
