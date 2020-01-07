package com.example.ocr_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btCamera, btOCR;
    private Toolbar tbMain;

    private static int OCRRequest = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btCamera = findViewById(R.id.btCamera);

        tbMain = findViewById(R.id.toolbarMain);
        tbMain.setTitle("Home");
        setSupportActionBar(tbMain);

        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, OCRRequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OCRRequest && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");

            imageView.setImageBitmap(imageBitmap);
            btOCR = findViewById(R.id.btOCR);

            btOCR.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  Intent intent = new Intent(MainActivity.this,OCRActivity.class);
                   try {
                       TextRecognizer detector = new TextRecognizer.Builder(getApplicationContext()).build();
                       if (detector.isOperational() && imageBitmap != null) {
                           Frame frame = new Frame.Builder().setBitmap(imageBitmap).build();
                           SparseArray<TextBlock> textBlocks = detector.detect(frame);
                           String blocks = "";
                           String lines = "";
                           String words = "";
                           for (int index = 0; index < textBlocks.size(); index++) {
                               //extract scanned text blocks here
                               TextBlock tBlock = textBlocks.valueAt(index);
                               blocks = blocks + tBlock.getValue() + "\n" + "\n";
                               for (Text line : tBlock.getComponents()) {
                                   //extract scanned text lines here
                                   lines = lines + line.getValue() + "\n";
                                   for (Text element : line.getComponents()) {
                                       //extract scanned text words here
                                       words = words + element.getValue() + ", ";
                                   }
                               }
                           }
                           if (textBlocks.size() == 0) {
                               Toast.makeText(MainActivity.this,
                                       "Your image has no text content\nPlease take another image",
                                       Toast.LENGTH_LONG).show();
                           }
                           else {
                               intent.putExtra("text", blocks);
                               startActivity(intent);
                               finish();
                           }
                       }
                       else {
                           Toast.makeText(MainActivity.this,
                                   "Unexpected error",
                                   Toast.LENGTH_SHORT).show();
                       }
                   }
                   catch (Exception e) {
                       Log.e("OCR error", e.toString());
                   }
               }
           });
        }
    }

    //Disable back press
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
