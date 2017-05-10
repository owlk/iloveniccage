package training.com.niccage.activity.similarmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import training.com.niccage.R;
import training.com.niccage.rest.NicCageApis;
import training.com.niccage.rest.model.Movie;
import training.com.niccage.rest.model.SimilarMovies;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int NORMAL_ITEM_TYPE = 1;
    private static final int LOADING_ITEM_TYPE = 2;
    private static final int NO_MORE_ITEMS_ITEM_TYPE = 3;

    private final SimilarMovies similarMovies = new SimilarMovies();
    private PaginationListener paginationListener;

    @Override
    public int getItemViewType(int position) {
        if (position == similarMovies.getResults().size() - 1) {
            return similarMovies.getPage().equals(similarMovies.getTotalPages())
                    ? NO_MORE_ITEMS_ITEM_TYPE
                    : LOADING_ITEM_TYPE;
        } else {
            return NORMAL_ITEM_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL_ITEM_TYPE:
                return new SimilarMovieViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_movie_card, parent, false));
            case LOADING_ITEM_TYPE:
                return new LoadingMoreViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_loading_more_card, parent, false));
            case NO_MORE_ITEMS_ITEM_TYPE:
            default:
                return new NoMoreItemsViewHolder(LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.list_item_no_more_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case NORMAL_ITEM_TYPE:
                SimilarMovieViewHolder smvh = (SimilarMovieViewHolder)holder;
                Movie movie = similarMovies.getResults().get(position);

                Glide.with(smvh.posterImageView.getContext())
                        .load(NicCageApis.BASE_IMAGE_URL + movie.getPosterPath())
                        .into(smvh.posterImageView);

                smvh.titleTextView.setText(movie.getTitle());

                smvh.releaseDateTextView.setText(movie.getReleaseDate());
                break;
            case LOADING_ITEM_TYPE:
                paginationListener.lastItemReached(similarMovies.getPage() + 1);
                break;
            case NO_MORE_ITEMS_ITEM_TYPE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return similarMovies.getResults().size();
    }

    public void setPaginationListener(PaginationListener paginationListener) {
        this.paginationListener = paginationListener;
    }

    public void addNextPage(SimilarMovies newMovies) {
        if (newMovies != null && newMovies.getResults() != null) {
            similarMovies.getResults().addAll(newMovies.getResults());
            similarMovies.setPage(newMovies.getPage());
            similarMovies.setTotalPages(newMovies.getTotalPages());
            similarMovies.setTotalResults(newMovies.getTotalResults());
            notifyDataSetChanged();
        }
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
