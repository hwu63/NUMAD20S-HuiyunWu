package whyellow.tk.numad20s_huiyunwu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LinkSQLite extends SQLiteOpenHelper{

    public LinkSQLite(Context c){
        super(c,"LinkDb",null,1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS linkTable";
        db.execSQL(query);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE linkTable (name varchar(20) , url TEXT PRIMARY KEY)";
        db.execSQL(query);
    }

    public void insert(String name, String url){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO linkTable values(?,?)",new String[]{name,url});
        db.close();
    }

    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE linkTable");
        db.execSQL("CREATE TABLE linkTable (name varchar(20) , url TEXT PRIMARY KEY)");
        db.close();
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from linkTable", null);
       return c;
    }

    public int getCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM linkTable", null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

}