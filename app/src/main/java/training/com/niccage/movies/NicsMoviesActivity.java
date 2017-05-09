package training.com.niccage.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.R;
import training.com.niccage.rest.NicCageAPI;
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

        NicCageAPI.API.getNicMovies().enqueue(new Callback<NicCageMoviesList>() {
            @Override
            public void onResponse(Call<NicCageMoviesList> call, Response<NicCageMoviesList> response) {
                mRecyclerView.setAdapter(new NicMovieAdapter(response.body().getCast()));
            }

            @Override
            public void onFailure(Call<NicCageMoviesList> call, Throwable t) {

            }
        });

    }
}
