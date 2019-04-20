package br.com.lisboa.figures_find_game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DoodleView extends View {

    private static final int TOUCH_TOLERANCE = 10;

    private static final int RADIUS = 300;
    private TextView chooseTextView;

    private Bitmap bitmap;

    private Canvas canvasBitmap;

    private Paint paintScreen;

    private Paint paintLine;

    private Map <Integer, Path> pathMap = new HashMap<>();

    private Map <Integer, Point> previousPointMap = new HashMap<>();

    private int correct;

    private int sqr;

    private int circle;

    private int triangle;

    private int rect;

    private Rect sqrObj;

    private Rect rectObj;

    private int circleX;

    private int circleY;

    List<LatLng> trian;

    private int points;

    private TextView pointsTextView;

    private int round;

    public DoodleView (Context context, AttributeSet set){
        super (context, set);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.argb(255,144,238,2));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(10);
        paintLine.setStrokeCap(Paint.Cap.ROUND);
        points = 0;
        round = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
    }

    public void clear (){
        pathMap.clear();
        previousPointMap.clear();
        bitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void setDrawingColor (int color){
        this.paintLine.setColor(color);
    }

    public int getDrawingColor (){
        return this.paintLine.getColor();
    }

    public void setLineWidth (int width){
        this.paintLine.setStrokeWidth(width);
    }

    public int getLineWidth (){
        return (int) this.paintLine.getStrokeWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(round < 5) {
            pointsTextView = ((TextView) ((Activity) getContext()).findViewById(R.id.pointsTextView));
            chooseTextView = ((TextView) ((Activity) getContext()).findViewById(R.id.chooseTextView));
            bitmap.eraseColor(Color.WHITE);
            Random r = new Random();
            sqr = r.nextInt(4);
            circle = next(sqr);
            triangle = next(circle);
            rect = next(triangle);
            correct = r.nextInt(4);
            //correct = triangle;
            canvas.drawBitmap(bitmap, 0, 0, paintScreen);
            drawSquare(canvasBitmap, paintLine, sqr);
            drawCircle(canvasBitmap, paintLine, circle);
            drawTriangle(canvasBitmap, paintLine, triangle, 600);
            drawRect(canvasBitmap, paintLine, rect);
            if (correct == sqr) {
                chooseTextView.setText(R.string.clickSqr);
            } else if (correct == circle) {
                chooseTextView.setText(R.string.clickCir);
            } else if (correct == rect) {
                chooseTextView.setText(R.string.clickRect);
            } else if (correct == triangle) {
                chooseTextView.setText(R.string.clickTri);
            }
            round++;
        } else {
            Toast.makeText(getContext(), "Final " + pointsTextView.getText(), Toast.LENGTH_SHORT).show();
            points = 0;
            pointsTextView.setText("Score: " + points);
            round = 0;
            invalidate();
        }
    }

    private void drawSquare(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                sqrObj = new Rect(getWidth()/4-300, getHeight()/4-300, getWidth()/2-60, getHeight()/2-300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 1:
                sqrObj = new Rect(getWidth()/4*3-300, getHeight()/4-300, getWidth()/4*3+300, getHeight()/2-300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 2:
                sqrObj = new Rect(getWidth()/4-300, getHeight()/2+300, getWidth()/2-60, getHeight()/4*3+300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
            case 3:
                sqrObj = new Rect(getWidth()/4*3-300, getHeight()/2+300, getWidth()/4*3+300, getHeight()/4*3+300);
                canvasBitmap.drawRect(sqrObj, paintLine);
                break;
        }
    }

    private void drawCircle(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                circleX = getWidth()/4;
                circleY = getHeight()/4;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 1:
                circleX = getWidth()/4*3;
                circleY = getHeight()/4;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 2:
                circleX = getWidth()/4;
                circleY =getHeight()/4*3;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
            case 3:
                circleX =getWidth()/4*3;
                circleY =getHeight()/4*3;
                canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
                break;
        }
    }

    private void drawRect(Canvas canvasBitmap, Paint paintLine, int pos) {
        switch (pos) {
            case 0:
                rectObj = new Rect(getWidth()/4-150, getHeight()/4-300, getWidth()/2-210, getHeight()/2-300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 1:
                rectObj = new Rect(getWidth()/4*3-150, getHeight()/4-300, getWidth()/4*3+150, getHeight()/2-300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 2:
                rectObj = new Rect(getWidth()/4-150, getHeight()/2+300, getWidth()/2-210, getHeight()/4*3+300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
            case 3:
                rectObj = new Rect(getWidth()/4*3-150, getHeight()/2+300, getWidth()/4*3+150, getHeight()/4*3+300);
                canvasBitmap.drawRect(rectObj, paintLine);
                break;
        }
    }

    private int next(int in) {
        if (in >= 3)
            return 0;
        return in+1;
    }

    public void drawTriangle(Canvas canvas, Paint paint, int pos, int width) {
        int x = 0;
        int y = 0;
        switch (pos) {
            case 0:
                x = getWidth()/4;
                y = getHeight()/4;
                break;
            case 1:
                x = getWidth()/4*3;
                y = getHeight()/4;
                break;
            case 2:
                x = getWidth()/4;
                y = getHeight()/4*3;
                break;
            case 3:
                x = getWidth()/4*3;
                y = getHeight()/4*3;
                break;
        }

        int halfWidth = width / 2;

        trian = new ArrayList<>();
        trian.add(new LatLng(x/100, (y-halfWidth)/100));
        trian.add(new LatLng((x -halfWidth)/100, (y + halfWidth)/100));
        trian.add(new LatLng((x + halfWidth)/100, (y + halfWidth)/100));

        Path path = new Path();
        path.moveTo(x, y - halfWidth); // Top
        path.lineTo(x - halfWidth, y + halfWidth); // Bottom left
        path.lineTo(x + halfWidth, y + halfWidth); // Bottom right
        path.lineTo(x, y - halfWidth); // Back to Top
        path.close();

        canvas.drawPath(path, paint);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        int actionIndex = event.getActionIndex();

        if(checkIfPoint(event)){
            addPoint();
        } else {
            decreasePoint();
        }
        /*if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN){

        }
        else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP){

        }
        else{

        }*/
        invalidate();
        return false;
    }

    private void decreasePoint() {
        if(points != 0) {
            points--;
        }
        pointsTextView.setText("Score: " + points);
    }

    private boolean checkIfPoint(MotionEvent event) {
        if(correct==sqr) {
            if(itsMySqr(event)) {
                return true;
            }
            return false;
        } else if(correct==circle) {
            if(itsMyCir(event)) {
                return true;
            }
        } else if(correct==rect) {
            if(itsMyrRect(event)) {
                return true;
            }
        } else if(correct==triangle) {
            if(itsMyTri(event)) {
                return true;
            }
        }
        return false;
    }

    private boolean itsMyTri(MotionEvent event) {
        float x = event.getX()/100;
        float y = event.getY()/100;
        LatLng point = new LatLng(x, y);
        boolean contains = PolyUtil.containsLocation(point.latitude, point.longitude, trian, true);
        if (contains) {
            return true;
        }
        return false;
    }

    private boolean itsMyCir(MotionEvent event) {
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }

    private boolean itsMyrRect(MotionEvent event) {
        if(rectObj.contains((int)event.getX(), (int)event.getY())){
            return true;
        }
        return false;
    }

    private boolean itsMySqr(MotionEvent event) {
        if(sqrObj.contains((int)event.getX(), (int)event.getY())){
            return true;
        }
        return false;
    }

    private void addPoint() {
        points++;
        pointsTextView.setText("Score: " + points);
    }

    private void touchStarted (float x, float y, int lineID){

    }
}
