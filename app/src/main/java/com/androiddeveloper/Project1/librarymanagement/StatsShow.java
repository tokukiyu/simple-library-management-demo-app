package com.androiddeveloper.Project1.librarymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StatsShow extends AppCompatActivity {

    TextView display,Name,edition,publisher,pages,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_show);

        display=findViewById(R.id.tvDisplay);
        Name=findViewById(R.id.TvName);
        edition=findViewById(R.id.TvEdition);
        publisher=findViewById(R.id.TvPublisher);
        pages=findViewById(R.id.TvPages);
        price=findViewById(R.id.TvPrice);


        Intent intent=getIntent();
        String name=intent.getStringExtra("name");

        if(name!=null) {

            String data = "";
            dbHelper db = new dbHelper(getApplicationContext());

            if(name!=null)
            {
                db.open();
                data = db.getRowData(Character.getNumericValue(name.charAt(0)));
                db.close();
            }
            else
            {
                display.setText("No data to display!");
            }

            if(data!=null)
            {

                String[] arrOfData = data.split("#!");

                if(arrOfData.length==5)
                {

                    Name.setText("NAME:   "+arrOfData[0]);
                    edition.setText("EDITION:   "+arrOfData[1]);
                    publisher.setText("PUBLISHER:   "+arrOfData[2]);
                    pages.setText("PAGES:   "+arrOfData[3]);
                    price.setText("PRICE:   "+arrOfData[4]);
                }
                else
                {
                    display.setText("No data to display!");
                }

            }
            else
            {
                display.setText("No data to display!");
            }

        }
        else
            {
                display.setText("No data to display!");
            }




    }
}
