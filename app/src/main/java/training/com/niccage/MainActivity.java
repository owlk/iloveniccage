package training.com.niccage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import training.com.niccage.cache.NicCageCache;
import training.com.niccage.movies.NicsMoviesActivity;
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

        NicCageCache cache = ((NicApplication) getApplication()).getCache();
        cache.subscribeToNicCageDetails(new NicCageCache.Subscriber<NicCageDetails>() {
            @Override
            public void call(final NicCageDetails data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.biographyTextView)).setText(data.getBiography());
                        ((TextView) findViewById(R.id.nameTextView)).setText(data.getName());
                        ((TextView) findViewById(R.id.birthdayTextView)).setText(data.getBirthday());
                        Glide.with(MainActivity.this)
                                .load(getString(R.string.image_url) + data.getProfilePath())
                                .into((ImageView) findViewById(R.id.profileImageView));
                    }
                });
            }
        });

        cache.loadNicCageDetails();
    }

    @Override
    protected void onDestroy() {
        ((NicApplication) getApplication()).getCache().ubsubscribeToNicCageDetails();
        super.onDestroy();
    }
}
