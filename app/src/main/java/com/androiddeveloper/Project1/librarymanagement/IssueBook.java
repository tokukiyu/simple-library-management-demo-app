package com.androiddeveloper.Project1.librarymanagement;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class IssueBook extends AppCompatActivity {

    private TextView issueDate;
    private EditText ID,name,author,editon,Stname,RegNo;
    private TextView dueDate;
    private Button btnissue;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String Idate;
    private String Ddate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);
        issueDate=findViewById(R.id.IssueDate1);
        dueDate=findViewById(R.id.DueDate1);
        ID=findViewById(R.id.BookID1);
        name=findViewById(R.id.BookName1);
        author=findViewById(R.id.Author1);
        editon=findViewById(R.id.Edition1);
        Stname=findViewById(R.id.StName1);
        RegNo=findViewById(R.id.StReg1);
        btnissue=findViewById(R.id.BtnIssue1);




        issueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int date=calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IssueBook.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,dateSetListener,year,month,date);
                datePickerDialog.show();

            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                  Idate= month+"/"+dayOfMonth+"/"+year;
                if(dayOfMonth<=20 && (month==4 || month==6 || month==9 || month==11))
                {
                    Ddate= month+"/"+(dayOfMonth+10)+"/"+year;
                }
                else if(dayOfMonth>20 && (month==4 || month==6 || month==9 || month==11))
                {
                    Ddate = (month + 1) + "/" + (dayOfMonth - 20) + "/" + year;
                }
                else
                {
                    if(year==2)
                    {
                        if(year % 400==0)
                        {
                            if(dayOfMonth<=19)
                            {
                                Ddate= month+"/"+(dayOfMonth+10)+"/"+year;
                            }
                            else
                            {
                                Ddate= month+"/"+((dayOfMonth+1)-20)+"/"+year;
                            }
                        }
                        else
                        {
                            if(dayOfMonth<=18)
                            {
                                Ddate= month+"/"+(dayOfMonth+10)+"/"+year;
                            }
                            else
                            {
                                Ddate= month+"/"+((dayOfMonth+2)-20)+"/"+year;
                            }
                        }
                    }
                    else
                    {
                        if(dayOfMonth<=21)
                            Ddate= month+"/"+(dayOfMonth+10)+"/"+year;
                        else
                            Ddate = (month + 1) + "/" + (dayOfMonth - 21) + "/" + year;

                    }
                }
                issueDate.setText(Idate);
                dueDate.setText(Ddate);
            }
        };
        btnissue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sID=ID.getText().toString().trim();
                String sName=name.getText().toString();
                String sAuthor=author.getText().toString();
                String sEdition=editon.getText().toString().trim();
                String sStName=Stname.getText().toString();
                String sRegNo=RegNo.getText().toString().trim();
                dbHelper db=new dbHelper(getApplicationContext());
                db.open();
                db.deleteBook(sID);
                db.close();

                if (sID.isEmpty() || sAuthor.isEmpty() || sEdition.isEmpty() || sName.isEmpty() || sStName.isEmpty() ||
                        sRegNo.isEmpty() || Idate.isEmpty() || Ddate.isEmpty() )
                {

                    Toast.makeText(IssueBook.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        DbIssueBook dbIssueBook=new DbIssueBook(getApplicationContext());
                        dbIssueBook.open();
                        boolean exist=dbIssueBook.AlreadyExist(sID);


                    if (!exist) {
                        boolean created = dbIssueBook.createEntry(sName, sID, sAuthor, sEdition, sStName, sRegNo, Idate, Ddate);
                        dbIssueBook.close();
                        if (created) {

                            Toast.makeText(IssueBook.this, "Book issued successfully!", Toast.LENGTH_SHORT).show();
                            ID.setText("");
                            name.setText("");
                            author.setText("");
                            editon.setText("");
                            Stname.setText("");
                            RegNo.setText("");
                            issueDate.setText("");
                            dueDate.setText("");
                        } else {
                            Toast.makeText(IssueBook.this, "Book cannot be issued!", Toast.LENGTH_SHORT).show();
                        }
                    }
                     else {
                        Toast.makeText(IssueBook.this, "This Book is already issued!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            });

            }


}
