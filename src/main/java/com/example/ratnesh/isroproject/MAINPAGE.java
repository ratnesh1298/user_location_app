package com.example.ratnesh.isroproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MAINPAGE extends AppCompatActivity {
Button bt1,bt2,bt3;
EditText tx1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        bt1=(Button)findViewById(R.id.button);
        bt2=(Button)findViewById(R.id.button2);
        bt3=(Button)findViewById(R.id.button3);
        tx1=(EditText)findViewById(R.id.editText);
    }
    public void track(View a)
    {
        Intent intent= new Intent(getApplicationContext(),MapsMainActivity.class);
        intent.putExtra("Name",tx1.getText().toString());
        startActivity(intent);
        finish();
    }
    public void show(View b)
    {
        Intent intent= new Intent(getApplicationContext(),Filepage.class);
        intent.putExtra("Name",tx1.getText().toString());
        startActivity(intent);
        finish();
    }
    public void exi(View c)
    {
       finish();
    }
}
