package training.com.niccage;

import android.app.Application;

import training.com.niccage.cache.NicCageCache;

public class NicApplication extends Application {

    private final NicCageCache cache = new NicCageCache();

    public NicCageCache getCache() {
        return cache;
    }
}