package com.rubi_bot.wendydeng.rubi_bot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.net.URL;

public class MainActivity extends Activity {
    int BUTTON_HEIGHT = 700;
    int SCREEN_HEIGHT = 2560;
    int SCREEN_WIDTH = 1440;
    Button[] captureButton = new Button[6];
    Bitmap[] cubeSideImages = new Bitmap[6];
    Button btnSolve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout l1 = (LinearLayout)findViewById(R.id.l1);
        LinearLayout l2 = (LinearLayout)findViewById(R.id.l2);
        for (int i = 0; i < 6; i++){

            captureButton[i] = new Button(this);
            captureButton[i].setText(String.format("Side %d", i));
            captureButton[i].setId(i);
            captureButton[i].setHeight(BUTTON_HEIGHT);

            final int finalI = i;
            captureButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchTakePictureIntent(finalI);
                }
            });
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i % 2 == 0) {
                l1.addView(captureButton[i],lp);
            } else {
                l2.addView(captureButton[i],lp);
            }
        }
        btnSolve = (Button) findViewById(R.id.btnSolve);
        btnSolve.setText("Solve");
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < 6; i++){
                    if(cubeSideImages[i] == null){
                        System.out.println("Please fill out all sides of the cube");
                        bitmapToString(cubeSideImages);
                        return;
                    }
                }
                bitmapToString(cubeSideImages);
            }
        });
        //FOR TESTING PURPOSES ONLY
        captureButton[0].setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    if (cubeSideImages[0] != null){
                        for (int i = 1; i < 6; i++){
                            cubeSideImages[i] = cubeSideImages[0];
                        }
                        bitmapToString(cubeSideImages);

                    }
                    dispatchTakePictureIntent(0);
                }
                return true;
            }
        });

    }
    private void bitmapToString(Bitmap[] bmpSides){
        System.out.println("Solving");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        StringBuilder color;
        for(int i = 0; i < 6; i++){
            color = getColorsSide(bmpSides[i]);
        }
    }
    private StringBuilder getColorsSide(Bitmap bmpSide){
        StringBuilder color = new StringBuilder();
        int height = bmpSide.getHeight();
        int width = bmpSide.getWidth();
        for(int i = 0; i < height; i += 2){
            for(int j = 0; j < width; j +=2){
                char pixelColor = getColorsPixel(bmpSide.getPixel(j, i));
                color.append(pixelColor);
            }
            color.append('\n');
        }
        System.out.println(color);
        return color;
    }
    private char getColorsPixel(int pixel){
        int r = Color.red(pixel);
        int g = Color.green(pixel);
        int b = Color.blue(pixel);
        if (r >= 180 && g < 50 && b < 50){
            return 'R';
        }
        else if(r < 50 && g >= 180 && b < 50){
            return 'G';
        }
        else if(r < 50 && g < 50 && b >= 180){
            return 'B';
        }
        else if(r >= 230 && g > 100 && g < 130 && b <30){
            return 'O';
        }
        else if(r >=240 && g > 240 && b < 30){
            return 'Y';
        }
        else if(r > 230 && g > 230 && b > 230){
            return 'W';
        }
        return '-';
    }

    private void dispatchTakePictureIntent(int side) {
        System.out.println(side);
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera_intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(camera_intent, side);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        System.out.println();
        if (resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap capturedImage = (Bitmap) extras.get("data");
            cubeSideImages[requestCode] = capturedImage;
            captureButton[requestCode].setBackground(new BitmapDrawable(getResources(), capturedImage));
    }
}
}

