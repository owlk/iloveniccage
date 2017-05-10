package training.com.niccage.activity.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import training.com.niccage.NicCageApplication;
import training.com.niccage.R;
import training.com.niccage.activity.movies.NicCageMoviesActivity;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.NicCageApis;
import training.com.niccage.rest.model.NicCageDetails;


public class NicCageDetailsActivity extends AppCompatActivity {
    @BindView(R.id.biographyTextView)
    TextView biographyTextView;
    @BindView(R.id.birthdayTextView)
    TextView birthdayTextView;
    @BindView(R.id.nameTextView)
    TextView nameTextView;
    @BindView(R.id.nicMoviesButton)
    Button nicMoviesButton;
    @BindView(R.id.profileImageView)
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_nic_cage_details);
        ButterKnife.bind(this);

        nicMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NicCageDetailsActivity.this.startActivity(new Intent(NicCageDetailsActivity.this, NicCageMoviesActivity.class));
            }
        });

        NicCageCache cache = ((NicCageApplication) getApplication()).getCache();
        cache.subscribeToNicCageDetails(new NicCageCache.Subscriber<NicCageDetails>() {
            @Override
            public void call(final NicCageDetails data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        biographyTextView.setText(data.getBiography());
                        nameTextView.setText(data.getName());
                        birthdayTextView.setText(data.getBirthday());
                        Glide.with(NicCageDetailsActivity.this)
                                .load(NicCageApis.BASE_IMAGE_URL + data.getProfilePath())
                                .into(profileImageView);
                    }
                });
            }
        });

        cache.loadNicCageDetails();
    }

    @Override
    protected void onDestroy() {
        ((NicCageApplication) getApplication()).getCache().ubsubscribeToNicCageDetails();
        super.onDestroy();
    }
}
