package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("key")
    private String youtubeKey;

    public String getYoutubeKey() {
        return youtubeKey;
    }

}
