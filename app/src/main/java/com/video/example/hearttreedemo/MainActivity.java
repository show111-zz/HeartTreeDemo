package com.video.example.hearttreedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.video.example.hearttreedemo.heart.TreeView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view  = new TreeView(this);
        setContentView(view);
    }
}
