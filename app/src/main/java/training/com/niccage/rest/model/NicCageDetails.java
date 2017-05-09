package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

public class NicCageDetails {
    @SerializedName("biography")
    private String biography;
    @SerializedName("profile_path")
    private String profilePath;

    public String getBiography() {
        return biography;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
