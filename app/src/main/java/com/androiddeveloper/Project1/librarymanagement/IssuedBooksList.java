package com.androiddeveloper.Project1.librarymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class IssuedBooksList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BooksAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_books_list);

        recyclerView = findViewById(R.id.RvIssuedBooks);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        DbIssueBook dbIssueBook = new DbIssueBook(getApplicationContext());
        dbIssueBook.open();
        final ArrayList<String> BooksName;
        BooksName = dbIssueBook.getName();
        dbIssueBook.close();
        myAdapter = new BooksAdapter(getApplicationContext(),BooksName);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new BooksAdapter.onItemClick() {
                    @Override
                    public void onItemClicked(int position) {

                        Intent intent = new Intent(getApplicationContext(),
                                com.androiddeveloper.Project1.librarymanagement.IssuedBooksDataDisplay.class);

                        String Id = "";
                        String Id1="";
                        Id = BooksName.get(position).trim();
                        String[] arrOfData=new String[Id.length()] ;
                        for(int i=0;i<arrOfData.length;i++)
                        {
                            arrOfData[i]= String.valueOf(Id.charAt(i));
                        }
                        if(arrOfData.length>0)
                        {
                            Id1= arrOfData[0];
                        }



                        Toast.makeText(IssuedBooksList.this, Id, Toast.LENGTH_SHORT).show();

                        intent.putExtra("DataId",Id1);

                        startActivity(intent);

                    }
                });
    }
}
