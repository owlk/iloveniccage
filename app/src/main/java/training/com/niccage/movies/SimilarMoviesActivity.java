package training.com.niccage.movies;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.niccage.NicApplication;
import training.com.niccage.R;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.model.SimilarMovies;

public class SimilarMoviesActivity extends AppCompatActivity {

    @BindView(R.id.similarRecyclerView)
    RecyclerView mRecyclerView;

    private int getColumnCount() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_similar_movies);
        ButterKnife.bind(this);

        final Integer movieId = getIntent().getExtras().getInt("movieId");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, getColumnCount()));

        final SimilarMoviesAdapter adapter = new SimilarMoviesAdapter();
        final NicCageCache cache = ((NicApplication) getApplication()).getCache();
        cache.subscribeToSimilarMovies(new NicCageCache.Subscriber<SimilarMovies>() {
            @Override
            public void call(SimilarMovies data) {
                adapter.addNextPage(data);
            }
        });

        adapter.setPaginationListener(new SimilarMoviesAdapter.PaginationListener() {
            @Override
            public void lastItemReached(int page) {
                cache.loadMoreSimilarMovies(movieId);
            }
        });

        mRecyclerView.setAdapter(adapter);
        cache.loadSimilarMovies(movieId);
    }

    @Override
    protected void onDestroy() {
        ((NicApplication) getApplication()).getCache().unsubscribeToSimilarMovies();
        super.onDestroy();
    }
}
