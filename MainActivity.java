package com.example.databaseexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_add, btn_edit, btn_delete, btn_display, btn_search;
    EditText et_title, et_description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.button_add);
        btn_edit = findViewById(R.id.button_edit);
        btn_delete = findViewById(R.id.button_delete);
        btn_display = findViewById(R.id.button_display);
        et_title = findViewById(R.id.editText_title);
        et_description = findViewById(R.id.editText_description);

        btn_search = findViewById(R.id.search_btn);

        DBHelper database = new DBHelper(getApplicationContext(),"db_example",null, 1);
        database.getReadableDatabase();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleInput = et_title.getText().toString();
                String descriptionInput = et_description.getText().toString();
                long l = database.addValue(titleInput, descriptionInput);
                if(l<=0){
                    Toast.makeText(MainActivity.this, "Cannot add the value!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Value added to the Database!", Toast.LENGTH_SHORT).show();
                    et_description.setText("");
                    et_title.setText("");
                }

            }
        });

        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = database.display();
                if(cursor.getCount()==0){
                    Toast.makeText(MainActivity.this, "No data to display!", Toast.LENGTH_SHORT).show();
                }else{
                    String dataStore = "";
                    while (cursor.moveToNext()){
                        dataStore +="\nTilte: "+cursor.getString(1)+" Description: "+cursor.getString(2);
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Data from Course Table");
                    alertDialog.setMessage(dataStore);
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                }
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleInput = et_title.getText().toString();
                Cursor cursor = database.search_display(titleInput);
                if(cursor.getCount()==0){
                    Toast.makeText(MainActivity.this, "No data to display!", Toast.LENGTH_SHORT).show();
                }else{
                    String dataStore = "";
                    while (cursor.moveToNext()){
                        dataStore +="\nTilte: "+cursor.getString(1)+" Description: "+cursor.getString(2);
                    }
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                    alertDialog.setTitle("Data from Course Table");
                    alertDialog.setMessage(dataStore);
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                }
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleInput = et_title.getText().toString();
                String descriptionInput = et_description.getText().toString();
                long l = database.updateValue(titleInput, descriptionInput);
                if(l<=0){
                    Toast.makeText(MainActivity.this, "Cannot update the value!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Value update to the Database!", Toast.LENGTH_SHORT).show();
                    et_description.setText("");
                    et_title.setText("");
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleInput = et_title.getText().toString();
                Cursor cursor = database.deleteValue(titleInput);
                if(cursor.getCount()==0){
                    Toast.makeText(MainActivity.this, "No data to delete!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Data deleted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
