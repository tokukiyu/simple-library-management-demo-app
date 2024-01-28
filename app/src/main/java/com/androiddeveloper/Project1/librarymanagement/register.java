package com.androiddeveloper.Project1.librarymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText name,email,regNo,password,confirmPass;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final LoginDataBaseHelper db=new LoginDataBaseHelper(this);

        name=findViewById(R.id.etName);
        email=findViewById(R.id.etMail);
        regNo=findViewById(R.id.etRegNo);
        password=findViewById(R.id.etpassword);
        confirmPass=findViewById(R.id.etConfirmPass);
        Button create=findViewById(R.id.btnCreate);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Sname=name.getText().toString().trim();
                String Semail=email.getText().toString().trim();
                String SregNo=regNo.getText().toString().trim();
                String Spassword=password.getText().toString().trim();
                String SconfirmPass=confirmPass.getText().toString().trim();

                if(Sname.isEmpty() || Semail.isEmpty() || SregNo.isEmpty() || Spassword.isEmpty() || SconfirmPass.isEmpty())
                {
                    Toast.makeText(register.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(Spassword.equals(SconfirmPass))
                    {
                        if(Spassword.length()>6 && SconfirmPass.length()>6)
                        {
                            Boolean checkEmail = db.checkEmail(Semail);
                            if (checkEmail == true) {
                                Boolean insert = db.insert(Semail, Spassword);
                                if (insert == true) {
                                    Toast.makeText(register.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();

                                    name.setText("");
                                    email.setText("");
                                    password.setText("");
                                    confirmPass.setText("");
                                    regNo.setText("");


                                } else {
                                    Toast.makeText(register.this, "Invalid Entry!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(register.this, "The entered email already exists!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(register.this, "Password must be atleast 7 characters long!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {


                            Toast.makeText(register.this, "Both field of password must be same!", Toast.LENGTH_SHORT).show();

                    }


                }

            }
        });




    }
}
