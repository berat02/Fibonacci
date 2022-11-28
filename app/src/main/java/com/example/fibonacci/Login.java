package com.example.fibonacci;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class Login extends AppCompatActivity {
    TextView textView;
    private int imageizinAlmakodu = 0,imageizinAlkodu = 1;
    private Bitmap secilenresim;
    private ImageView resimkaydet;
    EditText login_email,login_password;
    Button login_button;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        resimkaydet = (ImageView)findViewById(R.id.imageView8);
        textView = (TextView) findViewById(R.id.textView5);
        tanımlama();
        islem();

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }
    private void tanımlama(){
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();

    }
    private void islem(){
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Lütfen Bilgilerinizi Kontrol Ediniz", Toast.LENGTH_SHORT).show();
                }else{
                    login();
                }
            }

            private void login() {
                String email = login_email.getText().toString();
                String password = login_password.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this, "Merhaba Öğrenci", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(Login.this,Home.class);
                            startActivity(main);
                            finish();
                        }else{
                            Toast.makeText(Login.this, "Giriş Sırasında Bir Hata oluştu", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
    public void resimsec (View v){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},imageizinAlmakodu);
        }else{
            Intent resmiAl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(resmiAl,imageizinAlkodu);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==imageizinAlmakodu){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent resmiAl = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(resmiAl,imageizinAlkodu);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==imageizinAlkodu){
            if (requestCode==RESULT_OK && data != null){
                Uri resimuri = data.getData();

                try {
                   if (Build.VERSION.SDK_INT >= 28){
                       ImageDecoder.Source resimSource = ImageDecoder.createSource(this.getContentResolver(),resimuri);
                       secilenresim = ImageDecoder.decodeBitmap(resimSource);
                       resimkaydet.setImageBitmap(secilenresim);
                   }else{
                       secilenresim = MediaStore.Images.Media.getBitmap(this.getContentResolver(),resimuri);
                       resimkaydet.setImageBitmap(secilenresim);
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}