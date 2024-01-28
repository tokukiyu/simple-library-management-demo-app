package com.androiddeveloper.Project1.librarymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DetailReturnBk extends AppCompatActivity {
    TextView issueDate,ReturnDate;
    EditText Fine;
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_return_bk);
        issueDate=findViewById(R.id.Tvissuedate);
        ReturnDate=findViewById(R.id.TvReturndate);
        Fine=findViewById(R.id.TvFine);
        submit=findViewById(R.id.Btnsubmit);
        Intent intent=getIntent();
         String issuedate=intent.getStringExtra("issueDate");
        String returndate=intent.getStringExtra("returnDate");

        issueDate.setText(issuedate);
        ReturnDate.setText(returndate);
        String fine=Fine.getText().toString().trim();
        if(fine.equals(""))
            fine="0";
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                String sID = intent.getStringExtra("Id");
                DbIssueBook dbIssueBook = new DbIssueBook(getApplicationContext());
                dbIssueBook.open();
                long deleted = dbIssueBook.deleteBook(sID);
                dbIssueBook.close();
                if (deleted != -1) {
                    issueDate.setText("");
                    ReturnDate.setText("");
                    Fine.setText("");
                    Toast.makeText(DetailReturnBk.this, "Submitted Successfully!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(DetailReturnBk.this, "Book not returned!", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
}
