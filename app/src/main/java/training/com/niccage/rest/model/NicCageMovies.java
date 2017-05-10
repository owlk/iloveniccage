package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NicCageMovies {
    @SerializedName("cast")
    private List<Movie> cast;

    public List<Movie> getCast() {
        return cast;
    }
}
