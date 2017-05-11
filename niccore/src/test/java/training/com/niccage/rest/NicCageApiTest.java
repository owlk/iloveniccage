package training.com.niccage.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMovies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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

    private void givenServerReturnsNicCageDetails() {
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
    }

    private void givenServerReturnsNicCageMovies() {
        server.enqueue(new MockResponse().setBody(
                "{\n" +
                        "  \"cast\": [\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Captain Antonio Corelli\",\n" +
                        "      \"credit_id\": \"52fe4310c3a36847f8037b5f\",\n" +
                        "      \"id\": 1722,\n" +
                        "      \"original_title\": \"Captain Corelli's Mandolin\",\n" +
                        "      \"poster_path\": \"/kKDQgPvxf0hjjlnKPp3oGblDsZR.jpg\",\n" +
                        "      \"release_date\": \"2001-08-17\",\n" +
                        "      \"title\": \"Captain Corelli's Mandolin\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Yuri Orlov\",\n" +
                        "      \"credit_id\": \"52fe4318c3a36847f8039d57\",\n" +
                        "      \"id\": 1830,\n" +
                        "      \"original_title\": \"Lord of War\",\n" +
                        "      \"poster_path\": \"/nwPUI9WlYtDmE5VO6eEFCfrNXWl.jpg\",\n" +
                        "      \"release_date\": \"2005-09-16\",\n" +
                        "      \"title\": \"Lord of War\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"John McLoughlin\",\n" +
                        "      \"credit_id\": \"52fe431bc3a36847f803a98b\",\n" +
                        "      \"id\": 1852,\n" +
                        "      \"original_title\": \"World Trade Center\",\n" +
                        "      \"poster_path\": \"/hYbKIxMGqm0YaxGBYW52NbkYNAx.jpg\",\n" +
                        "      \"release_date\": \"2006-08-09\",\n" +
                        "      \"title\": \"World Trade Center\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Ronny Cammareri\",\n" +
                        "      \"credit_id\": \"52fe432fc3a36847f8040c31\",\n" +
                        "      \"id\": 2039,\n" +
                        "      \"original_title\": \"Moonstruck\",\n" +
                        "      \"poster_path\": \"/2OIxyH2zMPYBzaECdIlX81Qc398.jpg\",\n" +
                        "      \"release_date\": \"1987-12-18\",\n" +
                        "      \"title\": \"Moonstruck\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Ben Gates\",\n" +
                        "      \"credit_id\": \"52fe4331c3a36847f804144b\",\n" +
                        "      \"id\": 2059,\n" +
                        "      \"original_title\": \"National Treasure\",\n" +
                        "      \"poster_path\": \"/luMoc56LLMWUt60vUNNpwxrbTNt.jpg\",\n" +
                        "      \"release_date\": \"2004-11-19\",\n" +
                        "      \"title\": \"National Treasure\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Vincent Dwyer\",\n" +
                        "      \"credit_id\": \"52fe433bc3a36847f8044403\",\n" +
                        "      \"id\": 2148,\n" +
                        "      \"original_title\": \"The Cotton Club\",\n" +
                        "      \"poster_path\": \"/q8Y86MbAgriwG9OZiomKCOTxrUM.jpg\",\n" +
                        "      \"release_date\": \"1984-12-14\",\n" +
                        "      \"title\": \"The Cotton Club\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Charlie/Donald Kaufman\",\n" +
                        "      \"credit_id\": \"52fe436ac3a36847f8052987\",\n" +
                        "      \"id\": 2757,\n" +
                        "      \"original_title\": \"Adaptation.\",\n" +
                        "      \"poster_path\": \"/5trb1V5f3IsjpZx2GiuUylowl3W.jpg\",\n" +
                        "      \"release_date\": \"2002-12-06\",\n" +
                        "      \"title\": \"Adaptation.\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"adult\": false,\n" +
                        "      \"character\": \"Michael Williams\",\n" +
                        "      \"credit_id\": \"52fe436d9251416c7500ffdf\",\n" +
                        "      \"id\": 10427,\n" +
                        "      \"original_title\": \"Red Rock West\",\n" +
                        "      \"poster_path\": \"/d1TwyCjnJMKIgMw0CgH2nQsKZAN.jpg\",\n" +
                        "      \"release_date\": \"1993-07-08\",\n" +
                        "      \"title\": \"Red Rock West\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"id\": 2963\n" +
                        "}"
        ));
    }
}