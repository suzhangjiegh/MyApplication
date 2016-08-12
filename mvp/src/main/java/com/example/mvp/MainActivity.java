package com.example.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp.presenter.MainPresenter;
import com.example.mvp.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    TextView textView;
    ProgressBar progressBar;
    Button button;

    MainPresenter m;
    private void init() {
        textView= (TextView) findViewById(R.id.mvp_main_test);
        progressBar= (ProgressBar) findViewById(R.id.mvp_main_pr);
        button= (Button) findViewById(R.id.mvp_main_bt);


        button.setOnClickListener(this);
        m =new MainPresenter(this);

    }

    @Override
    public void onShowString(final String json) {

        textView.setText(json);


    }

    @Override
    public void onShowProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNoShowProgress() {

        progressBar.setVisibility(View.GONE);


    }
    @Override
    public void onClick(View v) {

        m.addItem();
        m.getString();
    }


}


