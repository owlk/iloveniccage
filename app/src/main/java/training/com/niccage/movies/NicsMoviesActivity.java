package training.com.niccage.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.niccage.NicApplication;
import training.com.niccage.R;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.model.NicCageMoviesList;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class NicsMoviesActivity extends AppCompatActivity {

    @BindView(R.id.nicRecyclerView)
    RecyclerView nicRecyclerView;

    private int getColumnCount() {
        return getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT ? 1 : 2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nics_movies);
        ButterKnife.bind(this);

        nicRecyclerView.setHasFixedSize(true);
        nicRecyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));

        NicCageCache cache = ((NicApplication) getApplication()).getCache();
        cache.subscribeToNicCageMoviesList(new NicCageCache.Subscriber<NicCageMoviesList>() {
            @Override
            public void call(final NicCageMoviesList data) {
                NicsMoviesActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nicRecyclerView.setAdapter(new NicMovieAdapter(data.getCast()));
                    }
                });
            }
        });

        cache.loadNicCageMoviesList();
    }

    @Override
    protected void onDestroy() {
        ((NicApplication) getApplication()).getCache().unsubscribeToNicCageMoviesList();
        super.onDestroy();
    }
}
