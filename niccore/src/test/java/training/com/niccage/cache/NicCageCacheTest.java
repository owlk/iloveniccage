package training.com.niccage.cache;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import training.com.niccage.rest.NicCageApi;
import training.com.niccage.rest.model.NicCageDetails;
import training.com.niccage.rest.model.NicCageMovies;
import training.com.niccage.rest.model.SimilarMovies;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NicCageCacheTest {

    @Mock
    private NicCageApi nicCageApi;

    private NicCageCache nicCageCache;

    @Before
    public void buildCache() {
        nicCageCache = new NicCageCache(nicCageApi);
    }

    @Test
    public void nicCageDetailsSubscriberCalled() {
        givenNicCageDetailsSubscriber();

        whenRequestNicCageDetailsCallSubscriber();

        nicCageCache.loadNicCageDetails();

        verifyNicCageDetailsSubscriberCalled();
    }

    @Test
    public void nicCageMoviesSubscriberCalled() {
        givenNicCageMoviesSubscriber();

        whenRequestNicCageMoviesCallSubscriber();

        nicCageCache.loadNicCageMovies();

        verifyNicCageMoviesSubscriberCalled();
    }

    @Test
    public void similarMoviesSubscriberCalled() {
        givenSimilarMoviesSubscriber();

        whenRequestSimilarMoviesCallSubscriber();

        nicCageCache.loadSimilarMovies(1);

        verifySimilarMoviesSubscriberCalled();
    }

    private void givenNicCageDetailsSubscriber() {
        nicCageCache.subscribeToNicCageDetails(mock(NicCageCache.Subscriber.class));
    }

    private void givenNicCageMoviesSubscriber() {
        nicCageCache.subscribeToNicCageMoviesList(mock(NicCageCache.Subscriber.class));
    }

    private void givenSimilarMoviesSubscriber() {
        nicCageCache.subscribeToSimilarMovies(mock(NicCageCache.Subscriber.class));
    }

    private void whenRequestNicCageDetailsCallSubscriber() {
        Call<NicCageDetails> call = mock(Call.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<NicCageDetails>) invocation.getArguments()[0]).onResponse(null, Response.success(new NicCageDetails()));
                return null;
            }
        }).when(call).enqueue(any(Callback.class));

        when(nicCageApi.getNicCageDetails()).thenReturn(call);
    }

    private void whenRequestNicCageMoviesCallSubscriber() {
        Call<NicCageMovies> call = mock(Call.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<NicCageMovies>) invocation.getArguments()[0]).onResponse(null, Response.success(new NicCageMovies()));
                return null;
            }
        }).when(call).enqueue(any(Callback.class));

        when(nicCageApi.getNicCageMovies()).thenReturn(call);
    }

    private void whenRequestSimilarMoviesCallSubscriber() {
        Call<SimilarMovies> call = mock(Call.class);

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Callback<SimilarMovies>) invocation.getArguments()[0]).onResponse(null, Response.success(new SimilarMovies()));
                return null;
            }
        }).when(call).enqueue(any(Callback.class));

        when(nicCageApi.getSimilarMovies(any(Integer.class), any(Integer.class), any(String.class))).thenReturn(call);
    }

    private void verifyNicCageDetailsSubscriberCalled() {
        verify(nicCageCache.getNicCageDetailsSubscriber()).call(any(NicCageDetails.class));
    }

    private void verifyNicCageMoviesSubscriberCalled() {
        verify(nicCageCache.getNicCageMoviesSubscriber()).call(any(NicCageMovies.class));
    }

    private void verifySimilarMoviesSubscriberCalled() {
        verify(nicCageCache.getSimilarMoviesSubscriber()).call(any(SimilarMovies.class));
    }
}
