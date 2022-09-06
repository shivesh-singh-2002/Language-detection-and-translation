package com.example.language_detection_01;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;



public class screen_02 extends AppCompatActivity {

    // creating variables for our
    // image view, text view and two buttons.
     ImageView img;
     TextView textview;
     Button snapBtn;
     Button detectBtn;

    // variable for our image bitmap.
     Bitmap imageBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on below line we are initializing our variables.
        img = (ImageView) findViewById(R.id.image);
        textview = (TextView) findViewById(R.id.text);
        snapBtn = (Button) findViewById(R.id.snapbtn);
        detectBtn = (Button) findViewById(R.id.detectbtn);

        // adding on click listener for detect button.
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to
                // detect a text .
                detectTxt();
            }
        });
        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to capture our image.
                dispatchTakePictureIntent();
            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent() {
        // in the method we are displaying an intent to capture our image.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        someActivityResultLauncher.launch(takePictureIntent);

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        // on below line we are getting
                        // data from our bundles. .
                        Bundle extras = data.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");

                        // below line is to set the
                        // image bitmap to our image.
                        img.setImageBitmap(imageBitmap);
                    }
                }
            });

    public void detectTxt() {
        // this is a method to detect a text from image.
        // below line is to create variable for firebase
        // vision image and we are getting image bitmap.
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        InputImage image = InputImage.fromBitmap(imageBitmap, 0);

        recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                processTxt(visionText);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(screen_02.this, "Fail to detect the text from image..", Toast.LENGTH_SHORT).show();
                                    }

                                });

    }

    public void processTxt(Text result) {
        // below line is to create a list of vision blocks which
        // we will get from our firebase vision text.
        String resultText = result.getText();
        if (resultText.isEmpty()) {
            // if the size of blocks is zero then we are displaying
            // a toast message as no text detected.
            Toast.makeText(screen_02.this, "No Text ", Toast.LENGTH_LONG).show();
            return;
        }

        // checking if the size of the
        // block is not equal to zero.

        // extracting data from each block using a for loop.
        StringBuilder sb=new StringBuilder();
        for (Text.TextBlock block : result.getTextBlocks()) {

            // below line is to get text
            // from each block.
            String txt = block.getText();

            // below line is to set our
            // string to our text view.
          sb.append(txt).append(" ");
        }
        detectLanguage(sb.toString());
    }
    public void detectLanguage(String string) {
        // initializing our firebase language detection.
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();

        // adding method to detect language using identify language method.
        languageIdentifier.identifyLanguage(string).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                // below line we are setting our
                // language code to our text view.
                textview.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handling error method and displaying a toast message.
                Toast.makeText(screen_02.this, "Fail to detect language : \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}