package com.androiddeveloper.Project1.librarymanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ReturnBook extends AppCompatActivity {
    private EditText BookName,ID,author,edition,StName,RegNo;
    private TextView returnDate;
    private Button BtnReturn;
    private String Rdate;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);
        BookName=findViewById(R.id.BookName);
        ID=findViewById(R.id.BookID);
        author=findViewById(R.id.Author);
        edition=findViewById(R.id.Edition);
        returnDate=findViewById(R.id.ReturnDate);
        StName=findViewById(R.id.StName);
        RegNo=findViewById(R.id.StReg);
        BtnReturn=findViewById(R.id.BtnReturn);


        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ReturnBook.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,onDateSetListener,
                        year,month,day);
                datePickerDialog.show();

            }
        });

       onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Rdate= month+"/"+dayOfMonth+"/"+year;
                returnDate.setText(Rdate);
            }
        };

        BtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sID=ID.getText().toString().trim();
                String sName=BookName.getText().toString().trim();
                String sAuthor=author.getText().toString().trim();
                String sEdition=edition.getText().toString().trim();
                String sStName=StName.getText().toString().trim();
                String sRegNo=RegNo.getText().toString().trim();
                if (sID.isEmpty() || sAuthor.isEmpty() || sEdition.isEmpty() || sName.isEmpty()
                        || sStName.isEmpty() || sRegNo.isEmpty() || Rdate.isEmpty())
                {
                    Toast.makeText(ReturnBook.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    DbIssueBook dbIssueBook=new DbIssueBook(getApplicationContext());
                    dbIssueBook.open();
                    boolean exist=dbIssueBook.AlreadyExist(sID);
                    //long deleted=dbIssueBook.deleteBook(sID);
                    String issueDate=dbIssueBook.getIssueDate(sID);
                    dbIssueBook.close();
                    if(exist)
                    {

                        {

                            ID.setText("");
                            author.setText("");
                            edition.setText("");
                            BookName.setText("");
                            returnDate.setText("");
                            StName.setText("");
                            RegNo.setText("");
                            Intent intent=new Intent(getApplicationContext(),
                                    com.androiddeveloper.Project1.librarymanagement.DetailReturnBk.class);
                            intent.putExtra("issueDate",issueDate);
                            intent.putExtra("returnDate",Rdate);
                            intent.putExtra("Id",sID);
                            startActivity(intent);
                        }

                    }
                    else
                    {
                        Toast.makeText(ReturnBook.this, "This book does not exist in Issued Books!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}
