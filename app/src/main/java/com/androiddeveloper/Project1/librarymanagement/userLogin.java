package com.androiddeveloper.Project1.librarymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class userLogin extends AppCompatActivity {

    EditText email,password;
    TextView register;
    Button login;
    LoginDataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        db=new LoginDataBaseHelper(this);
        login=findViewById(R.id.Btnsubmit);
        register=findViewById(R.id.RegisterAccount);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),
                        com.androiddeveloper.Project1.librarymanagement.register.class);

                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email=email.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(Email.isEmpty() || pass.isEmpty())
                {
                    Toast.makeText(userLogin.this, "Enter your user name and password!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean loginCheck=db.loginCheck(Email,pass);
                    if (loginCheck==true)
                    {
                        Intent intent2=getIntent();
                        String choice= intent2.getStringExtra("ChoiceButton");
                        Toast.makeText(userLogin.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        email.setText("");
                        password.setText("");
                        Intent intent=new Intent(userLogin.this,
                                com.androiddeveloper.Project1.librarymanagement.LibraryMenu.class);
                        intent.putExtra("userChoice",choice);
                        startActivity(intent);
                    }
                    else {

                        Toast.makeText(userLogin.this, "Invalid user name or password!", Toast.LENGTH_SHORT).show();

                   }

                }
            }
        });

    }
}
