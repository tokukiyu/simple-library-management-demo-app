package com.androiddeveloper.Project1.librarymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class BooksList extends AppCompatActivity {
    RecyclerView recyclerView;
    BooksAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);
        recyclerView=findViewById(R.id.RvList);
         layoutManager=new LinearLayoutManager(getApplicationContext());
         recyclerView.setLayoutManager(layoutManager);
        dbHelper db=new dbHelper(getApplicationContext());
         db.open();
        final ArrayList<String> BooksName;
        BooksName=db.getName();
        db.close();
        myAdapter=new BooksAdapter(this.getApplicationContext(),BooksName);
        recyclerView.setAdapter(myAdapter);
        myAdapter.setOnItemClickListener(new BooksAdapter.onItemClick() {
            @Override
            public void onItemClicked(int position) {

                Intent intent=new Intent(getApplicationContext(),
                com.androiddeveloper.Project1.librarymanagement.StatsShow.class);

                    String name="";
                    name= BooksName.get(position).trim();

                    Toast.makeText(BooksList.this, name, Toast.LENGTH_SHORT).show();

                        intent.putExtra("name",name);

                    startActivity(intent);
            }
        });
    }



}
