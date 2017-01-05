package com.example.user.sqlitecrudapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText id,name, surname, marks;
    Button insert, read, update, delete;

    String stringId, stringName, stringSurname, stringMarks;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        id = (EditText) findViewById(R.id.idId);
        name = (EditText) findViewById(R.id.nameId);
        surname = (EditText) findViewById(R.id.surnameId);
        marks = (EditText) findViewById(R.id.marksId);

        insert = (Button) findViewById(R.id.insertBtnId);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringName = name.getText().toString().trim();
                stringSurname = surname.getText().toString().trim();
                stringMarks = marks.toString().trim();

                boolean isInserted = myDb.insertData(stringName, stringSurname, stringMarks);

                name.setText("");
                surname.setText("");
                marks.setText("");

                if (isInserted == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        read = (Button) findViewById(R.id.readBtnId);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = myDb.readAllData();

                if (cursor.getCount() == 0){
                    Toast.makeText(MainActivity.this, "no data", Toast.LENGTH_SHORT).show();
                    showMessage("DATA: ", "There is no data");
                    return;

                }else {

                    StringBuffer buffer = new StringBuffer();

                    while (cursor.moveToNext()){
                        buffer.append("ID: "+ cursor.getString(0)+"\n");
                        buffer.append("NAME: "+ cursor.getString(1)+"\n");
                        buffer.append("SURNAME: "+ cursor.getString(2)+"\n");
                        buffer.append("MARKS: "+ cursor.getString(3)+"\n\n");

                    }

                    showMessage("Data", buffer.toString());

                }
            }
        });


        update = (Button) findViewById(R.id.updateBtnId);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringId = id.getText().toString().trim();
                stringName = name.getText().toString().trim();
                stringSurname = surname.getText().toString().trim();
                stringMarks = marks.toString().trim();

                boolean isUpdated = myDb.updateData(stringId, stringName, stringSurname, stringMarks);

                id.setText("");
                name.setText("");
                surname.setText("");
                marks.setText("");

                if (isUpdated == true){
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
            }
        });


        delete = (Button) findViewById(R.id.deleteBtnId);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringId = id.getText().toString().trim();

                Integer rowDeleted = myDb.deleteData(stringId);

                id.setText("");

                if (rowDeleted > 0){
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();


    }
}
