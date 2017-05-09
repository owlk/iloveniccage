package training.com.movieapp.rest;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class MovieDbAPITest {

    @Test
    public void test() throws IOException {
        MoveDbAPI api = new MoveDbAPI();

        assertTrue(api.getNickCage().getBiography().contains("Nicolas Cage"));
    }
}
