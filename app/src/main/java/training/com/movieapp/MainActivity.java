package training.com.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.movieapp.rest.MoveDbAPI;
import training.com.movieapp.rest.model.NicolasTrivia;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MoveDbAPI api = retrofit.create(MoveDbAPI.class);


        findViewById(R.id.requestNicCageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.getNickCage().enqueue(new Callback<NicolasTrivia>() {


                    @Override
                    public void onResponse(Call<NicolasTrivia> call, Response<NicolasTrivia> response) {
                        final NicolasTrivia nicolasTrivia = response.body();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) findViewById(R.id.nicCageDataTextView)).setText(nicolasTrivia.getBiography());

                                ImageLoader imageLoader = ImageLoader.getInstance();
                                imageLoader.init( new ImageLoaderConfiguration.Builder(MainActivity.this).build());
                                imageLoader.displayImage("https://image.tmdb.org/t/p/w180_and_h180_bestv2" + nicolasTrivia.getProfilePath(), (ImageView) findViewById(R.id.nicPic));
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<NicolasTrivia> call, Throwable t) {

                    }
                });
            }
        });
    }
}
