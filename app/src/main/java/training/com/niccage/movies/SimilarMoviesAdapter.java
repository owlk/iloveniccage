package training.com.niccage.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMovieViewHolder> {
    @Override
    public SimilarMoviesAdapter.SimilarMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SimilarMoviesAdapter.SimilarMovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SimilarMovieViewHolder extends RecyclerView.ViewHolder {
        public SimilarMovieViewHolder(View itemView) {
            super(itemView);
        }
    }
}
