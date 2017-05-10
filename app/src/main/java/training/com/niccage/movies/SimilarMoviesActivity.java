package training.com.niccage.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.BuildConfig;
import training.com.niccage.R;
import training.com.niccage.rest.NicCageAPI;
import training.com.niccage.rest.model.SimilarMovies;


public class SimilarMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movies);

        final int movieId = getIntent().getExtras().getInt("movieId");

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.similarRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final  SimilarMoviesAdapter adapter = new SimilarMoviesAdapter();
        adapter.setPaginationListener(new SimilarMoviesAdapter.PaginationListener() {
            @Override
            public void lastItemReached(int page) {
                NicCageAPI.API.getSimilarMovies(movieId, page, BuildConfig.API_KEY).enqueue(new Callback<SimilarMovies>() {
                    @Override
                    public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                        adapter.addNextPage(response.body());
                    }

                    @Override
                    public void onFailure(Call<SimilarMovies> call, Throwable t) {

                    }
                });
            }
        });

        NicCageAPI.API.getSimilarMovies(movieId, 1, BuildConfig.API_KEY).enqueue(new Callback<SimilarMovies>() {
            @Override
            public void onResponse(Call<SimilarMovies> call, Response<SimilarMovies> response) {
                adapter.addNextPage(response.body());
            }

            @Override
            public void onFailure(Call<SimilarMovies> call, Throwable t) {

            }
        });

        mRecyclerView.setAdapter(adapter);
    }
}
