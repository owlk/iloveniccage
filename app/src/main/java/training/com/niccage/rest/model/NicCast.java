package training.com.niccage.rest.model;

import com.google.gson.annotations.SerializedName;

class NicCast {

    private boolean adult;

    @SerializedName("poster_path")
    private String posterPath;

    private String title;


    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
