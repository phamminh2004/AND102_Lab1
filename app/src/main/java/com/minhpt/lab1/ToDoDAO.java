package com.minhpt.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ToDoDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private Context context;

    public ToDoDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean addToDo(ToDo toDo) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", toDo.getId());
        contentValues.put("title", toDo.getTitle());
        contentValues.put("content", toDo.getContent());
        contentValues.put("date", toDo.getDate());
        contentValues.put("type", toDo.getType());
        contentValues.put("status", toDo.getStatus());
        long check = database.insert("ToDo", null, contentValues);
        if (check > 0)
            Toast.makeText(context, "Thành công!!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!!",
                Toast.LENGTH_SHORT).show();
        return check != 1;
    }

    public boolean updateToDo(ToDo toDo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", toDo.getId());
        contentValues.put("title", toDo.getTitle());
        contentValues.put("content", toDo.getContent());
        contentValues.put("date", toDo.getDate());
        contentValues.put("type", toDo.getType());
        contentValues.put("status", toDo.getStatus());
        long check = database.update("ToDo", contentValues, "title=?", new String[]{String.valueOf(toDo.getTitle())});
        if (check > 0)
            Toast.makeText(context, "Thành công!!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!!",
                Toast.LENGTH_SHORT).show();
        return check != 1;
    }

    public boolean deleteToDo(String title) {
        long check = database.delete("ToDo", "title=?", new String[]{String.valueOf(title)});
        if (check > 0)
            Toast.makeText(context, "Thành công!!",
                    Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Thất bại!!",
                Toast.LENGTH_SHORT).show();
        return check != 1;
    }

    public ArrayList<ToDo> getListToDo() {
        ArrayList<ToDo> list = new ArrayList<>();
        database = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM ToDo", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    list.add(new ToDo(cursor.getInt(0)
                            , cursor.getString(1)
                            , cursor.getString(2)
                            , cursor.getString(3)
                            , cursor.getString(4)
                            , cursor.getInt(5)));
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("TAG", e.getMessage());
        }
        return list;
    }
}
