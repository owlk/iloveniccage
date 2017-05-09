package training.com.niccage.cache;

import training.com.niccage.rest.model.NicCageDetails;

public class NicCageCache {
    private static NicCageDetails nicCageDetails = null;

    public static NicCageDetails getNicCageDetails() {
        return nicCageDetails;
    }

    public static void setNicCageDetails(NicCageDetails nicCageDetails) {
        NicCageCache.nicCageDetails = nicCageDetails;
    }
}
