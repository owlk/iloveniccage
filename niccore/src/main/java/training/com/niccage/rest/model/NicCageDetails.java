package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

public class NicCageDetails {
    @SerializedName("biography")
    private String biography;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("name")
    private String name;
    @SerializedName("profile_path")
    private String profilePath;

    public String getBiography() {
        return biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
