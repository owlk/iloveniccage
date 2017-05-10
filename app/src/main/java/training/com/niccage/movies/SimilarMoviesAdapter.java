package training.com.niccage.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import training.com.niccage.R;
import training.com.niccage.rest.model.SimilarMovies;
import training.com.niccage.rest.model.TmdbMovie;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM_TYPE = 1;
    private static final int LOADING_ITEM_TYPE = 2;
    private static final int NO_MORE_ITEMS_ITEM_TYPE = 3;

    private final List<TmdbMovie> movies = new LinkedList<>();
    private final int totalPages;
    private int page = 0;
    private PaginationListener paginationListener;

    public SimilarMoviesAdapter(SimilarMovies movies) {
        this.movies.addAll(movies.getResults());
        totalPages = movies.getTotalPages();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == movies.size() - 1) {
            return page == totalPages ? NO_MORE_ITEMS_ITEM_TYPE : LOADING_ITEM_TYPE;
        } else {
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL_ITEM_TYPE:
                return new SimilarMovieViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_nic_movie, parent, false));
            case LOADING_ITEM_TYPE:
                return new LoadingMoreViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_loading_more, parent, false));
            case NO_MORE_ITEMS_ITEM_TYPE:
            default:
                return new NoMoreItemsViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_no_more, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NORMAL_ITEM_TYPE:
                SimilarMovieViewHolder smvh = (SimilarMovieViewHolder)holder;
                TmdbMovie movie = movies.get(position);

                Context context = smvh.posterImageView.getContext();
                String posterUrl = context.getString(R.string.image_url) + movie.getPosterPath();
                Glide.with(context).load(posterUrl).into(smvh.posterImageView);

                smvh.titleTextView.setText(movie.getTitle());

                smvh.releaseDateTextView.setText(movie.getReleaseDate());
                break;
            case LOADING_ITEM_TYPE:
                paginationListener.lastItemReached(page);
                break;
            case NO_MORE_ITEMS_ITEM_TYPE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setPaginationListener(PaginationListener paginationListener) {
        this.paginationListener = paginationListener;
    }

    public void addNextPage(SimilarMovies newMovies) {
        movies.addAll(newMovies.getResults());
        page++;
        notifyDataSetChanged();
    }

    private static class SimilarMovieViewHolder extends RecyclerView.ViewHolder {
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

    private static class LoadingMoreViewHolder extends RecyclerView.ViewHolder {
        public LoadingMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class NoMoreItemsViewHolder extends RecyclerView.ViewHolder {
        public NoMoreItemsViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface PaginationListener {
        void lastItemReached(int page);
    }
}
