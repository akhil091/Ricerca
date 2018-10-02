package com.example.rohit9934.anything;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private static Button b1;
    EditText e1;
    String s;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        if(id==R.id.help){
            Toast.makeText(this, "Enter a username and we will recommend you something.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b1= (Button) findViewById(R.id.enter);
        e1= (EditText) findViewById(R.id.handle);
        b1.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ShowData.class);
                String s=e1.getText().toString();
                i.putExtra("username",s);
                startActivity(i);
                finish();
            }
        });
    }
    //toolbar= (Toolbar) findVi
}
