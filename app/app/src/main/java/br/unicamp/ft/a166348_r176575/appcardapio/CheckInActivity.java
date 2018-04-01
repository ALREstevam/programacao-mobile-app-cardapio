package br.unicamp.ft.a166348_r176575.appcardapio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CheckInActivity extends AppCompatActivity {
    private Spinner spinner, spinner2;
    private static final String[]paths = {"1 Pessoa", "2 Pessoas", "3 Pessoas"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_in );
        
         spinner = (Spinner)findViewById(R.id.spinner1);
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
            android.R.layout.simple_spinner_item,paths);

       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Mesa, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinner2.setAdapter(adapter2);      
    }
}
