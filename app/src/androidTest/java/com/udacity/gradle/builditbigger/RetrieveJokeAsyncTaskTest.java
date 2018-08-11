package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Rakesh Praneeth.
 */
@RunWith(AndroidJUnit4.class)
public class RetrieveJokeAsyncTaskTest {

    @Test
    public void shouldCallOnJokeReceived() throws InterruptedException{
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        new RetrieveJokeAsyncTask(new OnJokeResponseListener() {
            @Override
            public void onJokeReceived(String output) {
                assertNotNull(output);
                assertThat(output,is(any(String.class)));
                countDownLatch.countDown();
            }
        }).execute();
        countDownLatch.await();
    }
}
