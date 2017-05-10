package training.com.niccage.cache;

import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMoviesList;

public class NicCageCache {
    private NicCageDetails nicCageDetails = null;
    private NicCageMoviesList nicCageMovies = null;

    public NicCageDetails getNicCageDetails() {
        return nicCageDetails;
    }

    public void setNicCageDetails(NicCageDetails nicCageDetails) {
        this.nicCageDetails = nicCageDetails;
    }

    public NicCageMoviesList getNicCageMovies() {
        return nicCageMovies;
    }

    public void setNicCageMovies(NicCageMoviesList nicCageMovies) {
        this.nicCageMovies = nicCageMovies;
    }
}
