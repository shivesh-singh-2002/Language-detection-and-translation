package com.example.language_detection_01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating variables for our image view,
    // text view and two buttons.
    private EditText edtLanguage;
    private TextView languageCodeTV;
    private Button detectLanguageBtn;
    private Button tv_linked;
    private Button dev_sc;
    private Button jap_sc;
    private Button chines_sc;
    private Button edtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        // on below line we are initializing our variables.
        edtLanguage = findViewById(R.id.idEdtLanguage);

        detectLanguageBtn = findViewById(R.id.idBtnDetectLanguage);
        tv_linked=findViewById(R.id.tv_link);
        dev_sc=findViewById(R.id.dev);
        languageCodeTV=findViewById(R.id.tvout);
        jap_sc=findViewById(R.id.jap);
        chines_sc=findViewById(R.id.chi);
        edtext=findViewById(R.id.get_text);
        // adding on click listener for button
        detectLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting string from our edit text.
                String edt_string = edtLanguage.getText().toString();
                // calling method to detect language.
                detectLanguage(edt_string);
            }
        });
        edtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain2Activity=new Intent(MainActivity.this,vocabl.class);
                startActivity(intentMain2Activity);
            }
        });
     chines_sc.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intentMain2Activity=new Intent(MainActivity.this,chinese.class);
             startActivity(intentMain2Activity);
         }
     });
        dev_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain2Activity=new Intent(MainActivity.this,devnagiri.class);
                startActivity(intentMain2Activity);
            }
        });

        jap_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain2Activity=new Intent(MainActivity.this,japanese.class);
                startActivity(intentMain2Activity);
            }
        });
        tv_linked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMain2Activity=new Intent(MainActivity.this,translation.class);
                startActivity(intentMain2Activity);
            }
        });
    }

    private void detectLanguage(String string) {
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
                languageCodeTV.setText(s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // handling error method and displaying a toast message.
                Toast.makeText(MainActivity.this, "Fail to detect language : \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }

}