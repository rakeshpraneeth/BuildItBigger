package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.krp.jokesandroidlib.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, OnJokeResponseListener {

    private Button tellJokeBtn;
    private ProgressBar progressBar;

    RetrieveJokeAsyncTask jokeAsyncTask;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tellJokeBtn = view.findViewById(R.id.tellJokeBtn);
        progressBar = view.findViewById(R.id.progressBar);

        tellJokeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tellJokeBtn) {
            if(NetworkHandler.isNetworkAvailable(getContext())) {
                progressBar.setVisibility(View.VISIBLE);
                jokeAsyncTask = new RetrieveJokeAsyncTask(this);
                jokeAsyncTask.execute();
            }else{
                Toast.makeText(getContext(), getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (jokeAsyncTask != null) {
            jokeAsyncTask.cancel(true);
        }
    }

    @Override
    public void onJokeReceived(String joke) {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getContext(), JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.EXTRA_JOKE_KEY, joke);
        startActivity(intent);
    }
}
