package training.com.niccage.rest;

import org.junit.Test;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.rest.model.NicCageMovies;

import static junit.framework.Assert.assertTrue;


public class MovieDbAPITest {

    private static final NicCageAPI API = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NicCageAPI.class);

    @Test
    public void test() throws IOException {
        NicCageMovies movies = API.getNicMovies().execute().body();

        assertTrue(!movies.getCast().isEmpty());
    }
}
