package com.androiddeveloper.Project1.librarymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBook extends AppCompatActivity {
    EditText name,edition,publisher,pages,price;
    Button addBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        name=findViewById(R.id.EtBookName);
        edition=findViewById(R.id.EtBookEdition);
        pages=findViewById(R.id.EtbookPages);
        publisher=findViewById(R.id.EtbookPublisher);
        price=findViewById(R.id.EtbookPrice);
        addBook=findViewById(R.id.BtnAddBook);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Sname = name.getText().toString();
                String Sedition = edition.getText().toString().trim();
                String Spages = pages.getText().toString().trim();
                String Spublisher = publisher.getText().toString();
                String Sprice = price.getText().toString().trim();

                if (Sname.isEmpty()|| Sedition.isEmpty() ||
                        Spages.isEmpty() || Sprice.isEmpty() ||
                        Spublisher.isEmpty())
                {

                    Toast.makeText(AddBook.this, "Fill all the fields to add a book!",
                            Toast.LENGTH_SHORT).show();

                }
                else{
                    dbHelper db = new dbHelper(getApplicationContext());
                    db.open();
                    db.createEntry(Sname, Spublisher, Sedition, Spages, Sprice);
                    db.close();
                    Toast.makeText(AddBook.this, "Successfully saved!", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    edition.setText("");
                    pages.setText("");
                    price.setText("");
                    publisher.setText("");

                    }
            }
        });

    }
}
