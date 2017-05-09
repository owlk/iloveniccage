package training.com.movieapp;

import training.com.movieapp.rest.model.NicolasTrivia;

public class NicCageCache {
    private static NicolasTrivia nicolasTrivia = null;

    public static NicolasTrivia getNicolasTrivia() {
        return nicolasTrivia;
    }

    public static void setNicolasTrivia(NicolasTrivia nicolasTrivia) {
        NicCageCache.nicolasTrivia = nicolasTrivia;
    }
}
