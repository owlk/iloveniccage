package training.com.niccage.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import training.com.niccage.R;
import training.com.niccage.rest.model.TmdbMovie;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMovieViewHolder> {
    private final List<TmdbMovie> movies;

    public SimilarMoviesAdapter(List<TmdbMovie> movies) {
        this.movies = movies;
    }

    @Override
    public SimilarMoviesAdapter.SimilarMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_nic_movie, parent, false);
        return new SimilarMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimilarMoviesAdapter.SimilarMovieViewHolder holder, int position) {
        TmdbMovie movie = movies.get(position);

        Context context = holder.posterImageView.getContext();
        String posterUrl = context.getString(R.string.image_url) + movie.getPosterPath();
        Glide.with(context).load(posterUrl).into(holder.posterImageView);

        holder.titleTextView.setText(movie.getTitle());

        holder.releaseDateTextView.setText(movie.getReleaseDate());

        if (position == movies.size() - 1) {
            Toast.makeText(holder.posterImageView.getContext(), "Last item reached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class SimilarMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView releaseDateTextView;

        public SimilarMovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView)itemView.findViewById(R.id.posterImageView);
            titleTextView = (TextView)itemView.findViewById(R.id.titleTextView);
            releaseDateTextView = (TextView)itemView.findViewById(R.id.releaseDateTextView);
        }
    }
}
