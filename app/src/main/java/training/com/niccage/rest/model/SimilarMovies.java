package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SimilarMovies {
    @SerializedName("page")
    private Integer page = 0;
    @SerializedName("results")
    private List<TmdbMovie> results = new ArrayList<>();
    @SerializedName("total_pages")
    private Integer totalPages = 0;
    @SerializedName("total_results")
    private Integer totalResults = 0;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<TmdbMovie> getResults() {
        return results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
