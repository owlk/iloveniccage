package training.com.niccage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.movies.NicsMoviesActivity;
import training.com.niccage.rest.model.NicCageDetails;

import static training.com.niccage.rest.NicCageAPI.API;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.nicMoviesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this, NicsMoviesActivity.class));
            }
        });

        NicCageDetails nicCageDetails = NicCageCache.getNicCageDetails();
        if (nicCageDetails == null) {
            API.getNickCage().enqueue(new Callback<NicCageDetails>() {
                @Override
                public void onResponse(Call<NicCageDetails> call, Response<NicCageDetails> response) {
                    final NicCageDetails nicCageDetails = response.body();
                    NicCageCache.setNicCageDetails(nicCageDetails);
                    setDetails(nicCageDetails);
                }

                @Override
                public void onFailure(Call<NicCageDetails> call, Throwable t) {

                }
            });
        } else {
            setDetails(nicCageDetails);
        }
    }

    private void setDetails(final NicCageDetails nicCageDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.biographyTextView)).setText(nicCageDetails.getBiography());
                ((TextView) findViewById(R.id.nameTextView)).setText(nicCageDetails.getName());
                ((TextView) findViewById(R.id.birthdayTextView)).setText(nicCageDetails.getBirthday());
                Glide.with(MainActivity.this)
                        .load("https://image.tmdb.org/t/p/w180_and_h180_bestv2" + nicCageDetails.getProfilePath())
                        .into((ImageView) findViewById(R.id.profileImageView));
            }
        });
    }
}
