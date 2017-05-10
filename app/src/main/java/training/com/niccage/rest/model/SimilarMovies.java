package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarMovies {

    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<TmdbMovie> results;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public List<TmdbMovie> getResults() {
        return results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }
}
