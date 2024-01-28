package com.androiddeveloper.Project1.librarymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbIssueBook  {
    public static final String DB_name="issue_book";
    public static final String DB_TABLE ="Db_table";
    public static final String BookName="book_name";
    public static final String KeyID="Book_id";
    public static final String AutoId="Auto_id";
    public static final String Author="_author";
    public static final String Edition="_Edition";
    public static final String IssueDate="issue_date";
    public static final String DueDate="Due_Date";
    public static final String StName="student_name";
    public static final String RegNum="reg_num";

    private final int DB_VERSION=1;
    private SQLiteDatabase sqLiteDatabase;
    private DbIssueHelper myHelper;
    private final Context myContext;
    public DbIssueBook(Context context)
    {
        myContext=context;
    }


    public class DbIssueHelper extends SQLiteOpenHelper
    {
        public DbIssueHelper(Context context)
        {
            super(context,DB_name,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sqlCode="CREATE TABLE "+DB_TABLE +" ("+ AutoId+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KeyID+" TEXT NOT NULL,"+BookName+" TEXT NOT NULL, "+Author+" TEXT NOT NULL,"+Edition
                    +" TEXT NOT NULL,"+IssueDate+" TEXT NOT NULL,"+DueDate+" TEXT NOT NULL,"+
                    StName+" TEXT NOT NULL,"+RegNum+" TEXT NOT NULL"+" );";
            db.execSQL(sqlCode);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
            onCreate(db);
        }
    }


    public DbIssueBook open()throws SQLException
    {
        myHelper= new DbIssueHelper(myContext);
        sqLiteDatabase=myHelper.getWritableDatabase();
        return this;

    }
    public DbIssueBook openReadable()throws SQLException
    {
        myHelper= new DbIssueHelper(myContext);
        sqLiteDatabase=myHelper.getReadableDatabase();
        return this;

    }
    public void close()
    {
        myHelper.close();
    }

    public boolean createEntry(String bookName,String ID, String author,String edition,String Stname,
                            String regNum,String IssueD,String DueD)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(BookName,bookName);
        contentValues.put(KeyID,ID);
        contentValues.put(Author,author);
        contentValues.put(Edition,edition);
        contentValues.put(StName,Stname);
        contentValues.put(RegNum,regNum);
        contentValues.put(IssueDate,IssueD);
        contentValues.put(DueDate,DueD);
        long ins= sqLiteDatabase.insert(DB_TABLE,null,contentValues);
        if(ins==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public String getIssueBook( String ID)
    {
        String[] coloumns=new String[]{KeyID,BookName,Author,Edition,StName,RegNum,IssueDate,DueDate};
        Cursor c= sqLiteDatabase.query(DB_TABLE,coloumns,
                KeyID + "=?", new String[] { String.valueOf(ID) },
                null,null,null,null);
        String IssueBooks="";
        int BknameIndex=c.getColumnIndex(BookName);
        int IdIndex=c.getColumnIndex(KeyID);
        int EditionIndex=c.getColumnIndex(Edition);
        int StNameIndex=c.getColumnIndex(StName);
        int AuthorIndex=c.getColumnIndex(Author);
        int RegNumIndex=c.getColumnIndex(RegNum);
        int IssueDtIndex=c.getColumnIndex(IssueDate);
        int DueDtIndex=c.getColumnIndex(DueDate);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            IssueBooks=c.getString(IdIndex)+"#!"+c.getString(BknameIndex)+"#!"+c.getString(AuthorIndex)+"#!"+
                    c.getString(EditionIndex)+"#!"+c.getString(StNameIndex)+"#!"+c.getString(RegNumIndex)+"#!"+
                    c.getString(IssueDtIndex)+"#!"+c.getString(DueDtIndex);
        }
        return IssueBooks;
    }
    public long updateEntry(String bookName,String ID, String author,String edition,String Stname,
                               String regNum,String IssueD,String DueD)
    {

        ContentValues contentValues=new ContentValues();
        contentValues.put(BookName,bookName);
        contentValues.put(KeyID,ID);
        contentValues.put(Author,author);
        contentValues.put(Edition,edition);
        contentValues.put(StName,Stname);
        contentValues.put(RegNum,regNum);
        contentValues.put(IssueDate,IssueD);
        contentValues.put(DueDate,DueD);

        return sqLiteDatabase.update(DB_TABLE,contentValues,KeyID+" =? ",new String[]{ID});
    }
    public long deleteBook(String ID)
    {
        return sqLiteDatabase.delete(DB_TABLE,KeyID+" =? ",new String[]{ID});
    }
    public boolean AlreadyExist(String ID)
    {
        Cursor c=sqLiteDatabase.rawQuery("Select * from "+DB_TABLE+" where "+KeyID+" =?",new String[]{ID});
        if(c.getCount()>0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public ArrayList<String> getName() {
        String[] coloumns = new String[]{KeyID,BookName};
        Cursor c= sqLiteDatabase.query(DB_TABLE,coloumns,null,null,null,null,null);
        int bookNameIndex=c.getColumnIndex(BookName);
        int Id=c.getColumnIndex(KeyID);
        ArrayList<String> Result=new ArrayList<>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Result.add(c.getString(Id)+"."+c.getString(bookNameIndex));
        }
        return Result;

    }






    public String getIssueDate(String ID)
    {
        Cursor c= sqLiteDatabase.query(DB_TABLE,new String[]{IssueDate},
                KeyID + "=?", new String[] { String.valueOf(ID) },
                null,null,null,null);
        String issueDate="";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            issueDate=c.getString(c.getColumnIndex(IssueDate));
        }
        return issueDate;
    }




}
