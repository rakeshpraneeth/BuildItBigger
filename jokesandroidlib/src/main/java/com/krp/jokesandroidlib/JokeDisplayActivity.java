package com.krp.jokesandroidlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.MenuItem;

public class JokeDisplayActivity extends AppCompatActivity {

    AppCompatTextView jokeTv;
    public static final String EXTRA_JOKE_KEY = "extraJokeKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        setTitle(R.string.joke);

        jokeTv = findViewById(R.id.jokeTv);

        if (getIntent() != null) {
            String joke = getIntent().getStringExtra(EXTRA_JOKE_KEY);
            if (!TextUtils.isEmpty(joke)) {
                jokeTv.setText(joke);
            } else {
                jokeTv.setText(getString(R.string.joke_failure));
            }
        }

        if(getSupportActionBar() !=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
