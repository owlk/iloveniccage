package training.com.niccage.rest;

import org.junit.Test;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMovies;
import training.com.niccage.rest.model.SimilarMovies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NicCageApiTest {
    private NicCageApi nicCageApi;

    @Test
    public void getNicCageDetails() throws Exception {
        givenApiBuilt();

        NicCageDetails details = nicCageApi.getNicCageDetails().execute().body();

        assertEquals("Nicolas Cage", details.getName());
    }

    @Test
    public void getNicCageMovies() throws Exception {
        givenApiBuilt();

        NicCageMovies movies = nicCageApi.getNicCageMovies().execute().body();

        assertNotNull(movies);
    }

    @Test
    public void getSimilarMovies() throws Exception {
        givenApiBuilt();

        SimilarMovies similarMovies = nicCageApi.getSimilarMovies(1722, 1, NicCageApi.API_KEY).execute().body();

        assertNotNull(similarMovies);
    }

    private void givenApiBuilt() {
         nicCageApi = new Retrofit.Builder()
                .baseUrl(NicCageApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NicCageApi.class);
    }
}