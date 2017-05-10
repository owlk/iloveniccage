package training.com.niccage.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import training.com.niccage.NicApplication;
import training.com.niccage.R;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.model.NicCageMoviesList;

public class NicsMoviesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nics_movies);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.nicRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final NicCageCache cache = ((NicApplication) getApplication()).getCache();

        cache.getNicCageMovies(new NicCageCache.NicCageCacheCallback<NicCageMoviesList>() {
            @Override
            public void call(final NicCageMoviesList data) {
                NicsMoviesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setAdapter(new NicMovieAdapter(data.getCast()));
                    }
                });
            }
        });
    }
}
