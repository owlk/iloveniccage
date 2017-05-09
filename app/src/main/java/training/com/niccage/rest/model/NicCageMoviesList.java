package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NicCageMoviesList {
    @SerializedName("cast")
    private List<NicCageMovie> cast;

    public List<NicCageMovie> getCast() {
        return cast;
    }
}
