package com.minhpt.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edt_title, edt_content, edt_date, edt_type;
    Button btn_add, btn_update, btn_delete;
    ListView lv_list;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<ToDo> listTD = new ArrayList<>();
    ToDoDAO toDoDAO;
    Context context = this;
    ArrayAdapter<String> adapterToDo;

    public void view() {
        list.clear();
        listTD.clear();
        listTD = toDoDAO.getListToDo();
        for (ToDo toDo : listTD) {
            list.add("\n" + "Title: " + toDo.getTitle()
                    + "\n" + "Content: " + toDo.getContent()
                    + "\n" + "Date: " + toDo.getDate()
                    + "\n" + "Type: " + toDo.getType() + "\n");
        }
        adapterToDo.notifyDataSetChanged();
    }

    public void setData(ToDo toDo) {
        toDo.setId(toDo.getId());
        toDo.setTitle(edt_title.getText().toString());
        toDo.setContent(edt_content.getText().toString());
        toDo.setDate(edt_date.getText().toString());
        toDo.setType(edt_type.getText().toString());
        toDo.setStatus(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_title = findViewById(R.id.edt_title);
        edt_content = findViewById(R.id.edt_content);
        edt_date = findViewById(R.id.edt_date);
        edt_type = findViewById(R.id.edt_type);
        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        lv_list = findViewById(R.id.lv_list);
        toDoDAO = new ToDoDAO(context);
        adapterToDo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv_list.setAdapter(adapterToDo);
        view();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo toDo = new ToDo();
                setData(toDo);
                toDoDAO.addToDo(toDo);
                view();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo toDo = new ToDo();
                setData(toDo);
                toDoDAO.updateToDo(toDo);
                view();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edt_title.getText().toString();
                toDoDAO.deleteToDo(title);
                view();
            }
        });
    }
}