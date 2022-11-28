package com.example.fibonacci;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
        TextView textView;
        EditText register_name , register_password , register_password_again,register_email;
        Button register_button;

        FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tanımlama();
        islem();

        textView = (TextView) findViewById(R.id.textView5);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }
    private void tanımlama(){
        register_name = findViewById(R.id.register_name);
        register_password = findViewById(R.id.register_password);
        register_password_again = findViewById(R.id.register_password_again);
        register_email = findViewById(R.id.register_email);
        register_button = findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();
    }
    private void islem(){
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = register_email.getText().toString();
                String name = register_name.getText().toString();
                String password = register_password.getText().toString();
                String password_again = register_password_again.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Kullanıcı Adını Doldurunuz", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(name)){
                    Toast.makeText(Register.this, "Kullanıcı Adını Doldurunuz", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Şifrenizi Oluşturunuz", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(password_again)){
                    Toast.makeText(Register.this, "Şifrenizi Tekrar Giriniz", Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }

            private void register() {
                String email = register_email.getText().toString();
                String password = register_password.getText().toString();

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent main = new Intent(Register.this,Login.class);
                                    startActivity(main);
                                    finish();
                                }else{
                                    Toast.makeText(Register.this, "İşlem Sırasında Bir Hata Oluştu", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                }
        });
    }
}