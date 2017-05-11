package training.com.niccage.rest;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.rest.model.NicCageDetails;

import static org.junit.Assert.assertEquals;


public class NicCageApiTest {
    private NicCageApi nicCageApi;
    private MockWebServer server;

    @After
    public void shutDownServer() throws IOException {
        server.shutdown();
    }

    @Test
    public void getNicCageDetails() throws Exception {
        givenMockServerApiBuilt();

        NicCageDetails nicCageDetails = nicCageApi.getNicCageDetails().execute().body();

        assertEquals("Nicolas Cage", nicCageDetails.getName());
    }


    private void givenMockServerApiBuilt() throws IOException {
        server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(
                "{\n" +
                        "  \"adult\": false,\n" +
                        "  \"also_known_as\": [\n" +
                        "    \"Nicolas Coppola\",\n" +
                        "    \"\\u5c3c\\u53e4\\u62c9\\u65af\\u00b7\\u51ef\\u5947\",\n" +
                        "    \"\\u041d\\u0438\\u043a\\u043e\\u043b\\u0430\\u0441 \\u041a\\u0435\\u0439\\u0434\\u0436\"\n" +
                        "  ],\n" +
                        "  \"biography\": \"Bio.\",\n" +
                        "  \"birthday\": \"1964-01-07\",\n" +
                        "  \"deathday\": \"\",\n" +
                        "  \"gender\": 2,\n" +
                        "  \"homepage\": \"\",\n" +
                        "  \"id\": 2963,\n" +
                        "  \"imdb_id\": \"nm0000115\",\n" +
                        "  \"name\": \"Nicolas Cage\",\n" +
                        "  \"place_of_birth\": \"Long Beach, California, USA\",\n" +
                        "  \"popularity\": 15.352389,\n" +
                        "  \"profile_path\": \"\\/fW37Gbk5PJZuXvyZwtcr0cMwPKY.jpg\"\n" +
                        "}"
        ));
        server.start();

        nicCageApi = new Retrofit.Builder()
                .baseUrl(server.url("/3/").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NicCageApi.class);
    }

}