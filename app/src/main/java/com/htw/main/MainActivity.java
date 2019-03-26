package com.htw.main;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.htw.news.NewsFragment;

import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNewsFragment();
    }

    private void initHtmlFragment() {
        HtmlFragment htmlFragment = HtmlFragment.newInstance("MainActivity");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_action, htmlFragment);
        fragmentTransaction.commit();
    }

    private void initSocketFragment() {
        SocketFragment fragment = SocketFragment.newInstance("MainActivity");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_action, fragment);
        fragmentTransaction.commit();
    }

    private void initNewsFragment() {
        NewsFragment fragment = NewsFragment.newInstance("MainActivity");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_action, fragment);
        fragmentTransaction.commit();
    }

}
