package com.viluvasa.pelatihandncc1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //TODO (4) Inisiasi Button
    Button btnF_B;
    EditText edInputan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pemanggilan Fungsi
        InitViews();

    }
    //TODO (5) Inisiasi Views/Layout
    private void InitViews(){
        edInputan = (EditText)findViewById(R.id.edInputan);
        btnF_B = (Button)findViewById(R.id.btnF_B);
        btnF_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Fragment B", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO (3) Tambah fungsi klik Fragment_A
    public void ClickedF_A(View view){
        Intent i = new Intent(this, TampungFragmentActivity.class);
        i.putExtra("tujuan", 1);
        i.putExtra("judul", edInputan.getText().toString());
        startActivity(i);
    }

    public void ClickedF_C(View view){
        //Toast.makeText(this, "Fragment A", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TampungFragmentActivity.class);
        i.putExtra("tujuan", 1);
        i.putExtra("judul", "Dari Main Ke FA");
        startActivity(i);
    }
}
