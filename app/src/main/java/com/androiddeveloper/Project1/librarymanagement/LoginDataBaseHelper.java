package com.androiddeveloper.Project1.librarymanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginDataBaseHelper extends SQLiteOpenHelper {





    public LoginDataBaseHelper(Context context) {

        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE user(email text primary key, password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ "user");
        onCreate(db);

    }
    public Boolean insert(String email, String password)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("email",email);
        cv.put("password",password);
        long ins= db.insert("user",null,cv);

        if(ins==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Boolean checkEmail(String email)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("Select * from user where email=?",new String[]{email});
        if(c.getCount()>0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
    public Boolean loginCheck(String email,String password)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("Select * from user where email=? and password=?",new String[]{email,password});

        if(c.getCount()>0) return true;
        else return false;
    }
}
