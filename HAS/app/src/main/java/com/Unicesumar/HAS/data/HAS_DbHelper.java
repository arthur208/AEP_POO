package com.Unicesumar.HAS.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;





public class HAS_DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HAS.db";

    private static final int DATABASE_VERSION = 1;

    public HAS_DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_ALARM_TABLE =  "CREATE TABLE " + HAS_Contract.HAS_Acesso.TABLE_NAME + " ("
                + HAS_Contract.HAS_Acesso._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HAS_Contract.HAS_Acesso.KEY_TITLE + " TEXT NOT NULL, "
                + HAS_Contract.HAS_Acesso.KEY_DATE + " TEXT NOT NULL, "
                + HAS_Contract.HAS_Acesso.KEY_TIME + " TEXT NOT NULL, "
                + HAS_Contract.HAS_Acesso.KEY_REPEAT + " TEXT NOT NULL, "
                + HAS_Contract.HAS_Acesso.KEY_REPEAT_NO + " TEXT NOT NULL, "
                + HAS_Contract.HAS_Acesso.KEY_REPEAT_TYPE + " TEXT NOT NULL, "
                + HAS_Contract.HAS_Acesso.KEY_ACTIVE + " TEXT NOT NULL " + " );";

        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
