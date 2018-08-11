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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.krp.jokesandroidlib.JokeDisplayActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, OnJokeResponseListener {

    private Button tellJokeBtn;
    private ProgressBar progressBar;

    RetrieveJokeAsyncTask jokeAsyncTask;

    private InterstitialAd interstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        MobileAds.initialize(getContext());
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));


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
    public void onJokeReceived(final String joke) {

        progressBar.setVisibility(View.GONE);

        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                navigateToDisplay(joke);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
               navigateToDisplay(joke);
            }
        });

    }

    private void navigateToDisplay(String joke){
        Intent intent = new Intent(getContext(), JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.EXTRA_JOKE_KEY, joke);
        startActivity(intent);
    }
}
