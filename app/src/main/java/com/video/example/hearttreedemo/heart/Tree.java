package com.video.example.hearttreedemo.heart;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.video.example.hearttreedemo.R;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by llyzhcllyzhc on 2017/9/5.
 */

public class Tree {

    // 控制素材显示大小的缩放因子
    private static final float CROWN_RADIUS_FACTOR = 0.35f;
    private static final float STAND_FACTOR = (CROWN_RADIUS_FACTOR / 0.28f);
    private static final float BRANCH_FACTOR = 1.3f * STAND_FACTOR;

    //动画的步骤
    private enum Step {
        BRANCHES_GROWING,
        BLOOMS_GROWING,
        MOVING_SNAPSHOT,
        BLOOM_FALLING
    }

    private Step step = Step.BRANCHES_GROWING;


    // 树枝的素材
    private float branchesDx;
    private float branchesDy;
    private LinkedList<Branch> growingBranches = new LinkedList<>();

    // 树的快照
    private Paint snapshotPaint = new Paint();
    private Snapshot treeSnapshot;

    // 控制快照偏移的变量
    private float snapshotDx;
    private float xOffset;

    // 根据屏幕分辨率设定缩放因子
    private float resolutionFactor;

    public Tree(final int canvasWidth, final int canvasHeight) {
        resolutionFactor = canvasHeight / 1080f;

        TreeMarker.init(canvasHeight, CROWN_RADIUS_FACTOR);

        // snapshot
        float snapshotWidth = 816f * STAND_FACTOR * resolutionFactor;
        float snapshotHeight = canvasHeight;
        treeSnapshot = new Snapshot(Bitmap.createBitmap(Math.round(snapshotWidth), canvasHeight, Bitmap.Config.ALPHA_8));

        //branches
        float branchWidth = 375f * BRANCH_FACTOR * resolutionFactor;
        float banchHeight = 490f * BRANCH_FACTOR * resolutionFactor;
        branchesDx = (snapshotWidth - branchWidth) / 2f - 40f * STAND_FACTOR;
        branchesDy = snapshotHeight - banchHeight;
        growingBranches.add(TreeMarker.getBranches());


        snapshotDx = (canvasWidth - snapshotWidth) / 2f;


    }


    public void draw(Canvas canvas) {
        //绘制背景颜色
        canvas.drawColor(0xffffffee);
//        canvas.drawColor(Color.BLUE);

        // 绘制动画元素
        canvas.save();
        canvas.translate(snapshotDx + xOffset, 0);

        switch (step) {
            case BRANCHES_GROWING:
                drawBranches();
                drawSnapshot(canvas);
                break;
        }
    }

    private void drawSnapshot(Canvas canvas) {
        canvas.drawBitmap(treeSnapshot.bitmap,0,0,snapshotPaint);
    }

    private void drawBranches() {
        if (!growingBranches.isEmpty()) {
            LinkedList<Branch> tempBranches = null;
            treeSnapshot.canvas.save();
            treeSnapshot.canvas.translate(branchesDx, branchesDy);
            Iterator<Branch> iterator = growingBranches.iterator();
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                if (branch.grow(treeSnapshot.canvas, BRANCH_FACTOR * resolutionFactor)) {
                    iterator.remove();
                    if (branch.childList != null) {
                        if (tempBranches == null) {
                            tempBranches = branch.childList;
                        } else {
                            tempBranches.addAll(branch.childList);
                        }
                    }
                }
            }
            treeSnapshot.canvas.restore();

            if(tempBranches != null){
                growingBranches.addAll(tempBranches);
            }

        } else {
            step = Step.BLOOMS_GROWING;
        }
    }


}
