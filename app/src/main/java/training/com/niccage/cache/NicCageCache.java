package training.com.niccage.cache;

import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMoviesList;

public class NicCageCache {
    private static NicCageDetails nicCageDetails = null;
    private static NicCageMoviesList nicCageMovies = null;

    public static NicCageDetails getNicCageDetails() {
        return nicCageDetails;
    }

    public static void setNicCageDetails(NicCageDetails nicCageDetails) {
        NicCageCache.nicCageDetails = nicCageDetails;
    }

    public static NicCageMoviesList getNicCageMovies() {
        return nicCageMovies;
    }

    public static void setNicCageMovies(NicCageMoviesList nicCageMovies) {
        NicCageCache.nicCageMovies = nicCageMovies;
    }
}
