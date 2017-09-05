package com.video.example.hearttreedemo.heart;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by llyzhcllyzhc on 2017/9/5.
 */

public class TreeView extends View{

    private static Tree tree;

    public TreeView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(tree == null){
            tree = new Tree(getWidth(),getHeight());
        }
        tree.draw(canvas);
        postInvalidate();
    }
}
