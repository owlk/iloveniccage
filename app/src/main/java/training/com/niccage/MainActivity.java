package training.com.niccage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.movies.NicsMoviesActivity;
import training.com.niccage.rest.NicCageAPI;
import training.com.niccage.rest.model.NicCageDetails;

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
                    setBiographyAndImage(nicCageDetails);
                }

                @Override
                public void onFailure(Call<NicCageDetails> call, Throwable t) {

                }
            });
        } else {
            setBiographyAndImage(nicCageDetails);
        }
    }

    private void setBiographyAndImage(final NicCageDetails nicCageDetails) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.nicCageDataTextView)).setText(nicCageDetails.getBiography());

                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(new ImageLoaderConfiguration.Builder(MainActivity.this).build());
                imageLoader.displayImage("https://image.tmdb.org/t/p/w180_and_h180_bestv2" + nicCageDetails.getProfilePath(), (ImageView) findViewById(R.id.nicPic));
            }
        });
    }
}
