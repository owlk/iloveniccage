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

        final NicCageCache cache = ((NicApplication) getApplication()).getCache();

        cache.getNicCageDetails(new NicCageCache.nicCageCacheCallback<NicCageDetails>() {
            @Override
            public void call(NicCageDetails data) {
                setDetails(data);
            }
        });
    }

    private void setDetails(final NicCageDetails nicCageDetails) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.biographyTextView)).setText(nicCageDetails.getBiography());
                ((TextView) findViewById(R.id.nameTextView)).setText(nicCageDetails.getName());
                ((TextView) findViewById(R.id.birthdayTextView)).setText(nicCageDetails.getBirthday());
                Glide.with(MainActivity.this)
                        .load(getString(R.string.image_url) + nicCageDetails.getProfilePath())
                        .into((ImageView) findViewById(R.id.profileImageView));
            }
        });
    }
}
