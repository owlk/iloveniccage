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
    public void setUp() {
        nicCageCache = new NicCageCache(nicCageApi);

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

    @Test
    public void testSubscriberCalled() {
        //given
        NicCageCache.Subscriber<NicCageDetails> subscriber = mock(NicCageCache.Subscriber.class);
        nicCageCache.subscribeToNicCageDetails(subscriber);

        //when
        nicCageCache.loadNicCageDetails();

        //then
        verify(subscriber).call(any(NicCageDetails.class));
    }

}
