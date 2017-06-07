package com.rubi_bot.wendydeng.rubi_bot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.capture_button);
        imageView = (ImageView) findViewById((R.id.image_view));
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(camera_intent);
                Bundle photo = (Bundle) camera_intent.getExtras();
                //imageView.setImageBitmap(photo);
                System.out.println(MediaStore.EXTRA_OUTPUT);
            }
        });
    }
}
