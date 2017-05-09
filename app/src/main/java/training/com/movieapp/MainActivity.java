package training.com.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import training.com.movieapp.rest.MoveDbAPI;
import training.com.movieapp.rest.model.NicolasTrivia;

public class MainActivity extends AppCompatActivity {
    private MoveDbAPI api = new MoveDbAPI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.requestNicCageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.getNickCage(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final NicolasTrivia nicolasTrivia = new Gson().fromJson(response.body().string(), NicolasTrivia.class);
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
                });
            }
        });
    }
}
