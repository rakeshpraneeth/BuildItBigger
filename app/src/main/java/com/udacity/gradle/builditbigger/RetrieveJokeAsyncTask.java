package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Rakesh Praneeth.
 */
public class RetrieveJokeAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private static String BASE_URL = "http://10.0.2.2:8080/_ah/api/";
    private static String TAG = RetrieveJokeAsyncTask.class.getSimpleName();

    private OnJokeResponseListener listener;

    public RetrieveJokeAsyncTask(OnJokeResponseListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(BASE_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null && !result.isEmpty()) {
            listener.onJokeReceived(result);
        }
    }
}