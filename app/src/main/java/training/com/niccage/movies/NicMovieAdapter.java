package training.com.niccage.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import training.com.niccage.R;
import training.com.niccage.rest.model.NicCageMovie;

public class NicMovieAdapter extends RecyclerView.Adapter<NicMovieAdapter.NicMovieViewHolder> {
    private final List<NicCageMovie> cast;

    public NicMovieAdapter(List<NicCageMovie> cast) {
        this.cast = cast;
    }

    @Override
    public NicMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_nic_movie, parent, false);
        return new NicMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NicMovieViewHolder holder, int position) {
        final NicCageMovie movie = cast.get(position);

        Context context = holder.posterImageView.getContext();
        String posterUrl = context.getString(R.string.image_url) + movie.getPosterPath();
        Glide.with(context).load(posterUrl).into(holder.posterImageView);

        holder.titleTextView.setText(movie.getTitle());

        holder.releaseDateTextView.setText(movie.getReleaseDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SimilarMoviesActivity.class);
                intent.putExtra("movieId", movie.getId());
                v.getContext().startActivity(intent);
            }
        });


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
