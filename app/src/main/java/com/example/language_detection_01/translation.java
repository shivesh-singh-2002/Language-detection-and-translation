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
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions;

import java.util.HashMap;
import java.util.Map;

public class translation extends AppCompatActivity {

    private ImageView img;
    private Button hin;
    private Button ger;
    private Button fren;
    private Button kann;
    private Button jap;
    private Button spnn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);


        img=findViewById(R.id.tr_img);
      hin=findViewById(R.id.tr_hindi);
      ger=findViewById(R.id.tr_german);
      fren=findViewById(R.id.tr_french);
      kann=findViewById(R.id.tr_kann);
      jap=findViewById(R.id.tr_jap);
      spnn=findViewById(R.id.tr_spanish);

      hin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentMain2Activity=new Intent(translation.this,trans_hindi.class);
              startActivity(intentMain2Activity);
          }
      });

      ger.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentMain2Activity=new Intent(translation.this,trans_german.class);
              startActivity(intentMain2Activity);
          }
      });

      fren.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentMain2Activity=new Intent(translation.this,trans_french.class);
              startActivity(intentMain2Activity);
          }
      });

      spnn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentMain2Activity=new Intent(translation.this,trans_spain.class);
              startActivity(intentMain2Activity);
          }
      });
      kann.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentMain2Activity=new Intent(translation.this,trans_kannad.class);
              startActivity(intentMain2Activity);
          }
      });
      jap.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intentMain2Activity=new Intent(translation.this,trans_jap.class);
              startActivity(intentMain2Activity);
          }
      });
    }

}