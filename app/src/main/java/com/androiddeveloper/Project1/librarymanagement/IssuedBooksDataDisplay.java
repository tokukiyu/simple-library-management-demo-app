package com.androiddeveloper.Project1.librarymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IssuedBooksDataDisplay extends AppCompatActivity {
    TextView name,BookId,author,issueDate,Stname,StReg,dueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_books_data_display);
        name=findViewById(R.id.BookNameD);
        BookId=findViewById(R.id.BookIdD);
        author=findViewById(R.id.BookAuthorD);
        issueDate=findViewById(R.id.BookIssueDateD);
        Stname=findViewById(R.id.BookStudentNameD);
        StReg=findViewById(R.id.RegNoD);
        dueDate=findViewById(R.id.BookDueDateD);
        Intent intent=getIntent();
        String Id=intent.getStringExtra("DataId");
        DbIssueBook dbIssueBook=new DbIssueBook(getApplicationContext());
        dbIssueBook.open();
        String data=dbIssueBook.getIssueBook(Id);
        dbIssueBook.close();
        if(data!=null)
        {

            String[] arrOfData = data.split("#!");
            String length="length"+Id;
            if (arrOfData.length == 8) {

                name.setText("NAME:    "+arrOfData[1]);
                BookId.setText("ID:    "+arrOfData[0]);
                author.setText("AUTHOR:    "+arrOfData[2]);
                issueDate.setText("ISSUE DATE:    "+arrOfData[6]);
                StReg.setText("STUDENT REG NUMBER:    "+arrOfData[5]);
                Stname.setText("STUDENT NAME:    "+arrOfData[4]);
                dueDate.setText("DUE DATE:    "+arrOfData[7]);

            }
            else
            {
                name.setText("NO data to Display!1");
            }

        }
        else
        {
            name.setText("NO data to Display!2");
        }


    }
}
