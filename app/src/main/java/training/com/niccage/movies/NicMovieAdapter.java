package training.com.niccage.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import training.com.niccage.R;
import training.com.niccage.rest.model.NicCast;

public class NicMovieAdapter extends RecyclerView.Adapter<NicMovieAdapter.NicMovieViewHolder> {
    private final List<NicCast> cast;

    public NicMovieAdapter(List<NicCast> cast) {
        this.cast = cast;
    }

    @Override
    public NicMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(NicMovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    public static class NicMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView releaseDateTextView;

        public NicMovieViewHolder(View v) {
            super(v);
            posterImageView = (ImageView)v.findViewById(R.id.posterImageView);
            titleTextView = (TextView)v.findViewById(R.id.titleTextView);
            releaseDateTextView = (TextView)v.findViewById(R.id.releaseDateTextView);
        }
    }
}
