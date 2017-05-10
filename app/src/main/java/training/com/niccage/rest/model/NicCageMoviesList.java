package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NicCageMoviesList {
    @SerializedName("cast")
    private List<TmdbMovie> cast;

    public List<TmdbMovie> getCast() {
        return cast;
    }
}
