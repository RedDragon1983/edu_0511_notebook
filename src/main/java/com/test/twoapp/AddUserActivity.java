package com.test.twoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class AddUserActivity extends AppCompatActivity {
    Button insertUserBtn;
    Button delUserBtn;
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextPhone;
    TextView textView;
    Boolean newUser;
    String[] partUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        Intent intent = getIntent();
        String userData = intent.getStringExtra("userdata"); // получаем переданную строку
        newUser = userData.equals(""); // если новый пользователь то пустая строка, иначе строка со значиниями

        insertUserBtn = findViewById(R.id.insertUserBtn);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        delUserBtn = findViewById(R.id.delUserBtn);
        textView = findViewById(R.id.textView);

        if(newUser){
            insertUserBtn.setText("Добавить пользователя");
            textView.setText("Добавление пользователя");
            delUserBtn.setVisibility(View.GONE);
        }else {
            partUserData = userData.split(" @ ");
            editTextName.setText(partUserData[0]);
            editTextLastName.setText(partUserData[1]);
            editTextPhone.setText(partUserData[3]);
            textView.setText("Редактирование пользователя");
            insertUserBtn.setText("Редактировать пользователя");
        }
        insertUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = Users.get(AddUserActivity.this);
                if(newUser) {
                    User user = new User();
                    user.setUserName(editTextName.getText().toString());
                    user.setUserLastName(editTextLastName.getText().toString());
                    user.setPhone(editTextPhone.getText().toString());
                    users.addUser(user);
                } else{
                    User user = new User(UUID.fromString(partUserData[2]));
                    user.setUserName(editTextName.getText().toString());
                    user.setUserLastName(editTextLastName.getText().toString());
                    user.setPhone(editTextPhone.getText().toString());
                    users.updateUser(user);
                }
                onBackPressed();
            }
        });

        delUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = Users.get(AddUserActivity.this);
                users.delUser(partUserData[2]);
                onBackPressed();
            }
        });

    }
}