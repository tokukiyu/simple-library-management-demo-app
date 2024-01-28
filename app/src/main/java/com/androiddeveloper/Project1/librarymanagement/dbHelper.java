package com.androiddeveloper.Project1.librarymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class dbHelper {

    public static final String KEY_ID="_id";
    public static final String BOOK_NAME="book_name";
    public static final String EDITION="_edition";
    public static final String PUBLISHER="_publisher";
    public static final String PRICE="_price";
    public static final String PAGES="_pages";

    private final String DB_NAME="addBook";
    private final int DB_VERSION=1;
    private final String DB_TABLE="db_table";

    private DBhelper ourHelper;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public dbHelper(Context context)
    {
        myContext=context;
    }


    private class DBhelper extends SQLiteOpenHelper {



        public DBhelper(Context context) {
            super(context,DB_NAME , null, DB_VERSION);
        }



        @Override
        public void onCreate(SQLiteDatabase db) {

            String sqlCode="CREATE TABLE "+DB_TABLE +" ("+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    BOOK_NAME+" TEXT NOT NULL,"+EDITION+" TEXT NOT NULL, "+PUBLISHER+" TEXT NOT NULL,"+PRICE
                    +" TEXT NOT NULL,"+PAGES+" TEXT NOT NULL"+" );";
            db.execSQL(sqlCode);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
            onCreate(db);

        }
    }
    public dbHelper open() throws SQLException

    {
        ourHelper=new DBhelper(myContext);
        myDataBase=ourHelper.getWritableDatabase();
        return this;

    }
    public void close()
    {
        ourHelper.close();
    }
    public long createEntry(String bookName,String publisher,String edition, String pages,String price)
    {
        ContentValues cv=new ContentValues();
        cv.put(BOOK_NAME,bookName);
        cv.put(EDITION,edition);
        cv.put(PUBLISHER,publisher);
        cv.put(PAGES,pages);
        cv.put(PRICE,price);

        return myDataBase.insert(DB_TABLE,null,cv);
    }
    public String getData()
    {
        String[] coloumns=new String[]{KEY_ID,BOOK_NAME,EDITION,PUBLISHER,PAGES,PRICE};
        Cursor c= myDataBase.query(DB_TABLE,coloumns,null,null,null,null,null);
        String result="";

        int bookNameIndex=c.getColumnIndex(BOOK_NAME);
        int editionIndex=c.getColumnIndex(EDITION);
        int publisherIndex=c.getColumnIndex(PUBLISHER);
        int pagesIndex=c.getColumnIndex(PAGES);
        int priceIndex=c.getColumnIndex(PRICE);

        {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                result= "  " + c.getString(bookNameIndex) + "          " + c.getString(editionIndex) + "          " +
                        c.getString(publisherIndex) + "          " + c.getString(pagesIndex) + "          " +
                        c.getString(priceIndex) + "\n" + result;

            }
        }


        c.close();
        return result;

    }
    public ArrayList<String> getName()
    {
        String[] coloumns=new String[]{KEY_ID,BOOK_NAME,EDITION,PUBLISHER,PAGES,PRICE};
        Cursor c= myDataBase.query(DB_TABLE,coloumns,null,null,null,null,null);
        int bookNameIndex=c.getColumnIndex(BOOK_NAME);
        int keyIDIndex=c.getColumnIndex(KEY_ID);
        ArrayList<String> Result=new ArrayList<>();

            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                Result.add(c.getString(keyIDIndex)+". "+c.getString(bookNameIndex));
            }

        return Result;

    }
    public long deleteBook(String ID)
    {
        return myDataBase.delete(DB_TABLE,KEY_ID+" =? ",new String[]{ID});
    }
    public String getRowData(int ID)
    {
        //String[] row=new String[]{BOOK_NAME,EDITION,PUBLISHER,PAGES,PRICE};
        Cursor c = myDataBase.query(DB_TABLE, new String[] { BOOK_NAME,EDITION,PUBLISHER,PAGES,PRICE }
        , KEY_ID + "=?", new String[] { String.valueOf(ID) },
                null, null, null, null);
        //Cursor c=myDataBase.rawQuery("Select * from DB_TABLE where KEY_ID=?",new String[]{ID});
        int bookNameIndex=c.getColumnIndex(BOOK_NAME);
        int editionIndex=c.getColumnIndex(EDITION);
        int publisherIndex=c.getColumnIndex(PUBLISHER);
        int pagesIndex=c.getColumnIndex(PAGES);
        int priceIndex=c.getColumnIndex(PRICE);
        String result="";
        {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                result=c.getString(bookNameIndex) + "#!" + c.getString(editionIndex) + "#!" +
                        c.getString(publisherIndex) + "#!" + c.getString(pagesIndex) + "#!" +
                        c.getString(priceIndex) ;

            }
        }

        return result;
    }
    public long updateEntry(String ID,String bookName,String publisher,String edition, String pages,String price)
    {
        ContentValues cv=new ContentValues();
        cv.put(BOOK_NAME,bookName);
        cv.put(EDITION,edition);
        cv.put(PUBLISHER,publisher);
        cv.put(PAGES,pages);
        cv.put(PRICE,price);
        return myDataBase.update(DB_TABLE,cv,KEY_ID+"=?",new String[]{ID});
    }


}
