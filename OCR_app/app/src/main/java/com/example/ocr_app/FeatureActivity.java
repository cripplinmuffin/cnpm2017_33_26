package com.example.ocr_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class FeatureActivity extends AppCompatActivity {
    ImageView imageView;
    Button button,bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
        imageView=(ImageView)findViewById(R.id.imageView);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,123);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            bt=(Button)findViewById(R.id.button2);

            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),OCRActivity.class);
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

                            } else {


                                intent.putExtra("text",blocks);
                                startActivity(intent);
                                finish();
                            }
                        } else {

                        }
                    } catch (Exception e) {

                        Log.e("hihi", e.toString());
                    }

                }

            });
        }
    }
}
