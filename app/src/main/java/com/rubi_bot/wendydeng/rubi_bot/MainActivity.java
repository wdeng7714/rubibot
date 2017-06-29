package com.rubi_bot.wendydeng.rubi_bot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


enum FACES {
    Up, Right, Front, Down, Left, Bottom
}

public class MainActivity extends Activity {
    int BUTTON_HEIGHT = 700;
    Button[] captureButton = new Button[6];
    Bitmap[] cubeSideImages = new Bitmap[6];
    Button btnSolve;
    Pair<String, Integer>[] COLORS = new Pair[6];
    StringBuilder color = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        COLORS[0] = new Pair("RED", Color.rgb(255, 0, 0));
        COLORS[1] = new Pair("GREEN", Color.rgb(0, 255, 0));
        COLORS[2] = new Pair("BLUE", Color.rgb(0, 0, 255));
        COLORS[3] = new Pair("YELLOW", Color.rgb(255, 255, 0));
        COLORS[4] = new Pair("ORANGE", Color.rgb(255, 102, 0));
        COLORS[5] = new Pair("WHITE", Color.rgb(255, 255, 255));

        setContentView(R.layout.activity_main);
        LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        for (int i = 0; i < 6; i++) {
            //Order the sides are read: Up Right Front Down Left Back
            captureButton[i] = new Button(this);
            captureButton[i].setText(String.format("Side %s", FACES.values()[i]));
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
                l1.addView(captureButton[i], lp);
            } else {
                l2.addView(captureButton[i], lp);
            }
        }
        btnSolve = (Button) findViewById(R.id.btnSolve);
        btnSolve.setText("Solve");
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 6; i++) {
                    if (cubeSideImages[i] == null) {
                        String errMsgStr = "Please fill out all sides of the cube";
//                        new ServerConnection().execute();
                        new ServerConnection().execute();
//                        new ServerConnection().execute("THISISASAMPLECUBESTRING");
                        Snackbar errMsg = Snackbar.make(view.findViewById(R.id.btnSolve), errMsgStr, Snackbar.LENGTH_SHORT);
                        errMsg.show();
                        System.out.println(errMsgStr);
                        return;
                    }
                }
                bitmapToString(cubeSideImages);
            }
        });
        //FOR TESTING PURPOSES ONLY
//        captureButton[0].setOnTouchListener(new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent){
//                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//                    if (cubeSideImages[0] != null){
//                        for (int i = 1; i < 6; i++){
//                            cubeSideImages[i] = cubeSideImages[0];
//                        }
//                        bitmapToString(cubeSideImages);
//
//                    }
//                    dispatchTakePictureIntent(0);
//                }
//                return true;
//            }
//        });
    }

    private void bitmapToString(Bitmap[] bmpSides) {
        System.out.println("Solving");
        for (int i = 0; i < 6; i++) {
            color = getColorsSide(bmpSides[0]);
        }
//        getSolution(color);
    }

    private StringBuilder getColorsSide(Bitmap bmpSide) {
        int width = bmpSide.getWidth();
        int faceWidth = bmpSide.getWidth() / 3;
        System.out.println("facewidth = " + faceWidth);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int x = width / 6;
                x += j * faceWidth;
                int y = width / 6;
                y += i * faceWidth;
                System.out.println("x = " + x + ", y = " + y);
                char pixelColor = getColorsPixel(bmpSide.getPixel(x, y));
                color.append(pixelColor);
            }
        }
        System.out.println(color);
        return color;
    }

    private char getColorsPixel(int pixel) {
        Pair match = getEuclidianDist(pixel);
        if (match.first == "RED") {
            return 'R';
        } else if (match.first == "GREEN") {
            return 'G';
        } else if (match.first == "BLUE") {
            return 'B';
        } else if (match.first == "ORANGE") {
            return 'O';
        } else if (match.first == "YELLOW") {
            return 'Y';
        } else if (match.first == "WHITE") {
            return 'W';
        }
        return '-';
    }

    private Pair getEuclidianDist(int pixel) {
        double minDist = Double.MAX_VALUE;
        Pair closest = null;
        for (int i = 0; i < 6; i++) {
            double dist = 0;
            dist = Math.pow((Color.red(pixel) - Color.red(COLORS[i].second)), 2);
            dist += Math.pow((Color.green(pixel) - Color.green(COLORS[i].second)), 2);
            dist += Math.pow((Color.blue(pixel) - Color.blue(COLORS[i].second)), 2);
            if (dist < minDist) {
                minDist = dist;
                closest = COLORS[i];
            }
        }
        return closest;
    }

    private void dispatchTakePictureIntent(int side) {
        System.out.println(side);
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera_intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(camera_intent, side);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println();
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap capturedImage = (Bitmap) extras.get("data");
            capturedImage = Bitmap.createBitmap(capturedImage, 0, 0, capturedImage.getWidth(), capturedImage.getWidth());
            cubeSideImages[requestCode] = capturedImage;
            captureButton[requestCode].setBackground(new BitmapDrawable(getResources(), capturedImage));
        }
    }

    class ServerConnection extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            final String host = "192.168.1.13";
            final int port = 8080;
            try {
                System.out.println("Creating socket connection to " + host + port);
                Socket sock = new Socket(host, port);
                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
                out.print("hello");
                String solution = br.readLine();
                System.out.println("server says:" + solution);
                sock.close();
            } catch (Exception e) {
                System.out.println("Unable to connect to solution server " + e.toString());
                e.printStackTrace();
                return null;
            }
            return null;
        }

    }
}

