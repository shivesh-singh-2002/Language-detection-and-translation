package com.example.language_detection_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

public class trans_jap extends AppCompatActivity {

    protected EditText edt;
    protected TextView tvv;
    protected Button clean;
    protected Button det;
    Translator HindiEnlishtrsnl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_jap);

        edt = findViewById(R.id.idEdtjap);
        tvv = findViewById(R.id.tvout_jap);
        clean = (Button) findViewById(R.id.clear_jap);
        det = (Button) findViewById(R.id.idBtnDetectLanguage_jap);
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.JAPANESE)
                        .setTargetLanguage(TranslateLanguage.ENGLISH)
                        .build();
        HindiEnlishtrsnl =
                Translation.getClient(options);


        // adding on click listener for button
        det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to download language
                // modal to which we have to translate.
                String string = edt.getText().toString();
                downloadModal(string);
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.setText("");
            }
        });

    }

    public void downloadModal(String input) {
        // below line is use to download the modal which
        // we will require to translate in german language
        DownloadConditions conditions = new DownloadConditions.Builder()
                .requireWifi()
                .build();
        // below line is use to download our modal.
        HindiEnlishtrsnl.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                // this method is called when modal is downloaded successfully.
                Toast.makeText(trans_jap.this, "Please wait language modal is being downloaded.", Toast.LENGTH_SHORT).show();

                // calling method to translate our entered text.
                translateLanguage(input);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(trans_jap.this, "Fail to download modal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void translateLanguage(String input) {
        HindiEnlishtrsnl.translate(input).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                tvv.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(trans_jap.this, "Fail to translate", Toast.LENGTH_SHORT).show();
            }
        });
    }


}