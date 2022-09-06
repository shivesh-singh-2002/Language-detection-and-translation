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
import android.view.Window;
import android.view.WindowManager;
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
import com.google.mlkit.vision.text.devanagari.DevanagariTextRecognizerOptions;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.HashMap;
import java.util.Map;

public class devnagiri extends AppCompatActivity {

    ImageView img;
    TextView textview;
    Button snapBtn;
    Button detectBtn;

    // variable for our image bitmap.
    Bitmap imageBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);

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
        TextRecognizer recognizer = TextRecognition.getClient(new DevanagariTextRecognizerOptions.Builder().build());
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
                                Toast.makeText(devnagiri.this, "Fail to detect the text from image..", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(devnagiri.this, "No Text ", Toast.LENGTH_LONG).show();
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
        Map<String,String> map=new HashMap<>();
        String[] code={"af","ar","be","bg","bn","ca","cs","cy","da","de","el","en","eo","es","et","fa","fi","fr","ga","gl","gu","he","hi","hr","ht","hu","id","is","it","ja","ka","kn","ko","lt","lv","mk","mr","ms","mt","nl","no","pl","pt","ro","ru","sk","sl","sq","sv","sw","ta","te","th","tl","tr","uk","ur","vi","zh"};
        String[] val={"Afrikaans","Arabic","Belarusian","Bulgarian","Bengali","Catalan","Czech","Welsh","Danish","German","Greek","English","Esperanto","Spanish","Estonian","Persian","Finnish","French","Irish","Galician","Gujarati","Hebrew","Hindi","Croatian","Haitian","Hungarian","Indonesian","Icelandic","Italian","Japanese","Georgian","Kannada","Korean","Lithuanian","Latvian","Macedonian","Marathi","Malay","Maltese","Dutch","Norwegian","Polish","Portuguese","Romanian","Russian","Slovak","Slovenian","Albanian","Swedish","Swahili","Tamil","Telugu","Thai","Tagalog","Turkish","Ukrainian","Urdu","Vietnamese","Chinese"};
        int l=code.length;
        for (int i = 0; i <l ; i++) {
            map.put(code[i],val[i]);
        }
        map.put("hi-Latn","Hindi");
        // initializing our firebase language detection.
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();

        // adding method to detect language using identify language method.
        languageIdentifier.identifyLanguage(string).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                // below line we are setting our
                // language code to our text view.
                if(map.containsKey(s))s=map.get(s);
                s.toUpperCase();
                textview.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handling error method and displaying a toast message.
                Toast.makeText(devnagiri.this, "Fail to detect language : \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}