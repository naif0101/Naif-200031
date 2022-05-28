package alfaisal.naif.naif_200031;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="project.db";
    public static final String TABLE_NAME="Student";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "FATHER_NAME";
    public static final String COL4 = "SURNAME";
    public static final String COL5 = "NATIONAL_ID";
    public static final String COL6 = "DATE_OF_BIRTH";
    public static final String COL7 = "GENDER";

    //Constructor
    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID TEXT PRIMARY KEY," +
                " NAME TEXT NOT NULL," +
                " FATHER_NAME TEXT NOT NULL," +
                " SURNAME TEXT NOT NULL," +
                " NATIONAL_ID TEXT NOT NULL," +
                " DATE_OF_BIRTH TEXT NOT NULL," +
                " GENDER TEXT NOT NULL);";
        db.execSQL(createTable);
    }

    //Every time the dB is updated (or upgraded)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String id, String name, String fatherName, String surname, String nid, String dob, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, name);
        contentValues.put(COL3, fatherName);
        contentValues.put(COL4, surname);
        contentValues.put(COL5, nid);
        contentValues.put(COL6, dob);
        contentValues.put(COL7, gender);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    public Cursor getSpecific(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where ID=?", new String[]{id});
        data.moveToFirst();
        if(check(data)) {
            data.moveToFirst();
            String dataID = data.getString(0);
            String dataName = data.getString(1);
            String dataFather = data.getString(2);
            String dataSurname = data.getString(3);
            String dataNid = data.getString(4);
            String dataDob = data.getString(5);
            String dataGender = data.getString(6);
            return data;
        }
        return null;
    }
    public boolean deleteSpecific(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int d = db.delete(TABLE_NAME, COL1 + " = ?", new String[]{id});

        //if data are inserted incorrectly, it will return -1
        if(d == -1) {return false;} else {return true;}
    }
    public boolean check (Cursor cursor){
        if(cursor.getCount()!=0)
            return true;
        return false;
    }

    public boolean updateSpecific(String id, String name, String fatherName, String surname, String nid, String dob, String gender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, name);
        contentValues.put(COL3, fatherName);
        contentValues.put(COL4, surname);
        contentValues.put(COL5, nid);
        contentValues.put(COL6, dob);
        contentValues.put(COL7, gender);

        int d = db.update(TABLE_NAME, contentValues, COL1 + " = ?", new String[]{id});

        //if data are inserted incorrectly, it will return -1
        if(d == -1) {return false;} else {return true;}
    }
    public Cursor ViewList () {
        // create instance to write to the database created earlier
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor x = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return x;
    }

}
