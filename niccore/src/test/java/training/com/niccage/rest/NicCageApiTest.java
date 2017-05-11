package training.com.niccage.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMovies;
import training.com.niccage.rest.model.SimilarMovies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static training.com.niccage.rest.NicCageApi.API_KEY;


public class NicCageApiTest {
    private NicCageApi nicCageApi;
    private MockWebServer server;

    @Before
    public void startServer() throws Exception {
        server = new MockWebServer();
        server.start();

        nicCageApi = new Retrofit.Builder()
                .baseUrl(server.url("/3/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NicCageApi.class);
    }

    @After
    public void shutDownServer() throws Exception {
        server.shutdown();
    }

    @Test
    public void getNicCageDetails() throws Exception {
        givenServerReturnsNicCageDetails();

        NicCageDetails nicCageDetails = nicCageApi.getNicCageDetails().execute().body();

        assertEquals("Nicolas Cage", nicCageDetails.getName());
    }

    @Test
    public void getNicCageMovies() throws Exception {
        givenServerReturnsNicCageMovies();

        NicCageMovies nicCageMovies = nicCageApi.getNicCageMovies().execute().body();

        assertNotNull(nicCageMovies);
    }

    @Test
    public void getSimilarMovies() throws Exception {
        givenServerReturnsSimilarMovies();

        SimilarMovies similarMovies = nicCageApi.getSimilarMovies(1776, null, API_KEY).execute().body();

        assertNotNull(similarMovies);
    }

    private void givenServerReturnsNicCageDetails() throws Exception {
        server.enqueue(new MockResponse().setBody(getTestResourceAsString("nicCageDetails.json")));
    }

    private void givenServerReturnsNicCageMovies() throws Exception {
        server.enqueue(new MockResponse().setBody(getTestResourceAsString("nicCageMovies.json")));
    }

    private void givenServerReturnsSimilarMovies() throws Exception {
        server.enqueue(new MockResponse().setBody(getTestResourceAsString("movie1722SimilarMovies.json")));
    }

    private static String getTestResourceAsString(String fileName) throws URISyntaxException, IOException {
        return new String(Files.readAllBytes(
                Paths.get(NicCageApiTest.class
                        .getClassLoader()
                        .getResource(fileName)
                        .toURI())), Charset.forName("UTF-8"));
    }
}