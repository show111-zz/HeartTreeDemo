package com.video.example.hearttreedemo.heart;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by llyzhcllyzhc on 2017/9/5.
 */

public class Snapshot {

    Canvas canvas;
    Bitmap bitmap;

    public Snapshot(Bitmap bitmap){
        this.bitmap = bitmap;
        this.canvas = new Canvas(bitmap);
    }

}
