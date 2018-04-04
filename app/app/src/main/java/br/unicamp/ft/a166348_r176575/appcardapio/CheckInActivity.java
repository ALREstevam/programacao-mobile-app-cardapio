package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import br.unicamp.ft.a166348_r176575.appcardapio.utils.SimpleAlert;

public class CheckInActivity extends AppCompatActivity {
    private Spinner spinner, spinner2;
    private static final String[]paths = {"1 Pessoa", "2 Pessoas", "3 Pessoas"};
    private EditText mEditText;
    RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_in );
        mEditText = (EditText) findViewById(R.id.editText);

        maleRadioButton = (RadioButton) findViewById(R.id.radio_1);
        femaleRadioButton = (RadioButton) findViewById(R.id.radio_2);
        otherRadioButton = (RadioButton) findViewById(R.id.radio_3);

        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Mesa, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears
        spinner2.setAdapter(adapter2);
    }

    void onCheckInClicked(View view){
        String nome = mEditText.getText().toString();

        if(nome.isEmpty()){
            new SimpleAlert().alertOk( "Nome em branco", "Parece que você não escreveu seu nome, adicione seu nome para poder avançar", this );
        } else if( (maleRadioButton.isChecked() || femaleRadioButton.isChecked() || otherRadioButton.isChecked()) ){
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

        }else{
            new SimpleAlert().alertOk( "Sexo não Escolhido", "Sexo não escolhido", this );

        }



    }
}