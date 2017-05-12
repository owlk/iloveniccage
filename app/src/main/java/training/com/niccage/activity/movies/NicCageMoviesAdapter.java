package training.com.niccage.activity.movies;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import training.com.niccage.NicCageApplication;
import training.com.niccage.R;
import training.com.niccage.activity.similarmovies.SimilarMoviesActivity;
import training.com.niccage.cache.NicCageCache;
import training.com.niccage.rest.NicCageApi;
import training.com.niccage.rest.model.Movie;

public class NicCageMoviesAdapter extends RecyclerView.Adapter<NicCageMoviesAdapter.NicMovieViewHolder> {
    private final List<Movie> movies;
    private TrailerListener trailerListener;

    public NicCageMoviesAdapter(List<Movie> movies, TrailerListener trailerListener) {
        this.movies = movies;
        this.trailerListener = trailerListener;
    }

    @Override
    public NicMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_nic_movie_card, parent, false);
        return new NicMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NicMovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);

        Glide.with(holder.posterImageView.getContext())
                .load(NicCageApi.BASE_IMAGE_URL + movie.getPosterPath())
                .into(holder.posterImageView);

        holder.titleTextView.setText(movie.getTitle());

        holder.releaseDateTextView.setText(movie.getReleaseDate());

        holder.similarMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SimilarMoviesActivity.class);
                intent.putExtra("movieId", movie.getId());
                v.getContext().startActivity(intent);
            }
        });
        holder.posterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                NicCageCache cache = ((NicCageApplication) v.getContext().getApplicationContext()).getCache();
                cache.getTrailer(movie.getId())
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                trailerListener.trailerCalled(s);
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class NicMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImageView;
        private TextView titleTextView;
        private TextView releaseDateTextView;
        private Button similarMoviesButton;

        public NicMovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.posterImageView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            releaseDateTextView = (TextView) itemView.findViewById(R.id.releaseDateTextView);
            similarMoviesButton = (Button) itemView.findViewById(R.id.similarMoviesButton);
        }
    }

    public interface TrailerListener {

        void trailerCalled(String url);

    }
}
