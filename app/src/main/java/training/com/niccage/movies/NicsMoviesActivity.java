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
import training.com.niccage.rest.model.NicMovies;

public class NicsMoviesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nics_movies);

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.nicRecyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        NicCageAPI.API.getNicMovies().enqueue(new Callback<NicMovies>() {
            @Override
            public void onResponse(Call<NicMovies> call, Response<NicMovies> response) {
                mRecyclerView.setAdapter(new NicAdapter(response.body().getCast()));
            }

            @Override
            public void onFailure(Call<NicMovies> call, Throwable t) {

            }
        });

    }
}
