package com.example.mraema.ocrReder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mraema.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class OcrActivity extends AppCompatActivity {

    private static final String TAG ="checkimage" ;
    private String imageReferance;
    private TextView resultTv;
    private ImageView prescriptionImage;
    private Button scanButon;
    private Bitmap bmp, rotatedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        Intent intent = getIntent();
        imageReferance = intent.getStringExtra("imgRef");


        resultTv = findViewById(R.id.resultText);
        prescriptionImage = findViewById(R.id.imageView);
        scanButon = findViewById(R.id.scanBtn);



        StorageReference sr = FirebaseStorage.getInstance().getReference("images/"+imageReferance);

        final long ONE_MEGABYTE = 1024 * 1024*5;
        sr.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Matrix matrix = new Matrix();

                matrix.postRotate(90);
                bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);


                prescriptionImage.setImageBitmap(rotatedBitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(OcrActivity.this, "No Such file or Path found!!", Toast.LENGTH_LONG).show();
            }
        });


        scanButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                detectText(rotatedBitmap);

            }


            });
    }

            private void detectText(Bitmap bmap) {
               // Log.d(TAG, "onSuccess: ");
                InputImage image = InputImage.fromBitmap(bmap,0);
                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                Task<Text> result = recognizer.process(image).addOnSuccessListener(new OnSuccessListener<Text>() {
                    @Override
                    public void onSuccess(Text text) {
                      //  Log.d(TAG, "onSuccess: "+text);
                        StringBuilder result = new StringBuilder();
                        for (Text.TextBlock block : text.getTextBlocks()){
                            String blockText = block.getText();

                           // Log.d(TAG, "onSuccess: "+blockText);
                            Point[] blockCornerPoints = block.getCornerPoints();
                            Rect blockFrame = block.getBoundingBox();
                            for (Text.Line line : block.getLines()){
                                String lineText = line.getText();
                                result.append(lineText+"\n ");
                                Point[] lineCornerPoint = line.getCornerPoints();
                                Rect lineRect = line.getBoundingBox();
                                for (Text.Element element : line.getElements()){
                                    String elementText = element.getText();
                                   // result.append(elementText);
                                }
                                resultTv.setText(result);
                              //  Log.d(TAG, "onSuccess: "+blockText+" "+result);
                            }

                        }

                        //resultTv.setText(blockText);
                       // Log.d(TAG, "onSuccess: "+blockText+" "+result);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(OcrActivity.this, "fail to detect text from image... "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


    }
