package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiAsyncTaskBasis;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiConsumer;
import br.unicamp.ft.a166348_r176575.appcardapio.database.LocalDbHelper;
import br.unicamp.ft.a166348_r176575.appcardapio.factory.ProductConverter;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ClientInfo;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.RestaurantTable;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.VisitIdHolder;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;
import br.unicamp.ft.a166348_r176575.appcardapio.utils.SimpleAlert;

public class CheckInActivity extends AppCompatActivity {
    private Spinner spinner, spinner2;
    private static final String[] paths = new String[9];
    private HashMap<String, Integer> hashMapMesas;
    public static String VISIT_ID_INTENT = "visitId";


    private EditText mEditText;
    RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    LocalDbHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;

    String namefromDb = "";
    int peopleOnTableFromDb = 0;
    String sexFromDb = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_check_in );


        for(int i = 0; i < paths.length; i++){
            paths[i] = (i+1) + " pessoas";
        }


        //DbHelper
        dbHelper = new LocalDbHelper(CheckInActivity.this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        //Interface
        mEditText = (EditText) findViewById(R.id.editText);
        maleRadioButton = (RadioButton) findViewById(R.id.radio_1);
        femaleRadioButton = (RadioButton) findViewById(R.id.radio_2);
        otherRadioButton = (RadioButton) findViewById(R.id.radio_3);


        //Spinners
        //Spinner1
        spinner = (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Spinner2
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setUiTables();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name, people_on_table, client_sex FROM client_data", null);
        if (cursor.moveToLast()){
            String name = cursor.getString(0);
            int peopleOnTable = cursor.getInt(1);
            String sex = cursor.getString(2);

            mEditText.setText( name );
            spinner.setSelection( peopleOnTable - 1 );

            String namefromDb = name;
            int peopleOnTableFromDb = peopleOnTable;
            String sexFromDb = sex;

            switch (sex){
                case "M":
                    maleRadioButton.setChecked( true );
                    break;
                case "F":
                    femaleRadioButton.setChecked( true );
                    break;
                default:
                    otherRadioButton.setChecked( true );
                    break;
            }
        }
        cursor.close();
    }

    void onCheckInClicked(View view){
        String nome = mEditText.getText().toString();

        if(nome.isEmpty()){
            new SimpleAlert().alertOk( "Nome em branco", "Parece que você não escreveu seu nome, adicione seu nome para poder avançar", this );
        } else if( (maleRadioButton.isChecked() || femaleRadioButton.isChecked() || otherRadioButton.isChecked()) ){


            //

            int peopleOnTable = 0;
            for(String path : paths){
                peopleOnTable ++;
                if(path.equals( spinner.getSelectedItem() )){
                    break;
                }
            }

            String clientSex = sexAsString();

            if(!namefromDb.equals( nome ) || !sexFromDb.equals( clientSex ) || !(peopleOnTable == peopleOnTableFromDb)){
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", nome);
                contentValues.put("people_on_table", peopleOnTable);
                contentValues.put("client_sex", clientSex);

                sqLiteDatabase.insert("client_data", null, contentValues);
            }

/*{
	"tableId":3,
	"ClientName":"Abraão",
	"ClientSex":"M",
	"PeopleOnTable":15
}*/

            final Context referenceContext = this;
            final Class referenceClass = MenuActivity.class;
            //int tableId, String clientName, char clientSex, byte peopleOnTabl
            new ApiAsyncTaskBasis<String, String>( "http://andreluiz342.pythonanywhere.com", "/visit" ){

                @Override
                protected String doInBackground(String... strings) {
                    //public ClientInfo(int tableId, String clientName, char clientSex, byte peopleOnTable) {
                    int tableID = hashMapMesas.get( (String) spinner2.getSelectedItem() );

                    byte peopleOnTable = (byte) (spinner.getSelectedItemPosition() + 1);

                    ClientInfo cliInfoSubmit = new ClientInfo( tableID, mEditText.getText().toString(),  sexAsString().charAt( 0 ), peopleOnTable );
                    Genson genson = new Genson();
                    String serializedCliInfo = genson.serialize( cliInfoSubmit );

                    Log.e("DEBUG!", serializedCliInfo);

                    ApiConsumer cons = new ApiConsumer( this.getApiUrl() );

                    String visitJson = cons.post( this.getApiRoute(), serializedCliInfo );
                    return visitJson;
                }

                @Override
                protected void onPostExecute(String visitJson) {
                    super.onPostExecute( visitJson );

                    Log.e( "DEBUG", visitJson );

                    Genson genson = new Genson();
                        List<VisitIdHolder> lst1 = genson.deserialize( visitJson, new GenericType<List<VisitIdHolder>>() {
                    } );

                    int visitId = lst1.get( 0 ).getVisitId();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("visit_id", visitId);

                    sqLiteDatabase.insert("visit_data", null, contentValues);

                    Intent intent = new Intent(referenceContext, referenceClass);
                    intent.putExtra( VISIT_ID_INTENT, lst1.get( 0 ).getVisitId() );
                    startActivity(intent);
                }
            }.execute( "" );




        }else{
            new SimpleAlert().alertOk( "Sexo não Escolhido", "Sexo não escolhido", this );

        }

    }



    private void setUiTables(){
        final Context contextReference = this;
        new ApiAsyncTaskBasis<String, String>( "http://andreluiz342.pythonanywhere.com", "/restaurant" ){

            @Override
            protected String doInBackground(String... strings) {
                try {
                    ApiConsumer cons = new ApiConsumer( this.getApiUrl() );
                    String chairsJson = cons.get( this.getApiRoute() );
                    return chairsJson;
                }catch (Exception e){
                    new SimpleAlert().alertOk(
                            "Erro de conexão",
                            "Não foi possível conectar ao serviço, verifique sua conexão com a internet ou tente conectar mais tarde.",
                            contextReference );
                }
                return null;
            }

            @Override
            protected void onPostExecute(String chairsJson) {
                super.onPostExecute( chairsJson );

                if(chairsJson == null){
                    return;
                }

                Genson genson = new Genson();
                List<RestaurantTable> lst1 = genson.deserialize( chairsJson, new GenericType<List<RestaurantTable>>() {} );

                System.out.println( chairsJson );

                List<String> mesasNomes = new ArrayList<>(  );

                hashMapMesas = new HashMap<>(  );
                for (RestaurantTable tabl : lst1) {
                    Log.e( "DEBUG!", tabl.toString() );
                    if(tabl.getChairs() >= spinner.getSelectedItemPosition() + 1){
                        String labelMesa = tabl.getName() + " (" + tabl.getChairs() + " lugares)";
                        mesasNomes.add(labelMesa );
                        hashMapMesas.put(labelMesa, tabl.getId());
                    }
                }

                String[] mesaNomesArray = new String[mesasNomes.size()];
                mesaNomesArray = mesasNomes.toArray(mesaNomesArray);

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(contextReference, android.R.layout.simple_spinner_item, mesaNomesArray);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Specify the layout to use when the list of choices appears

                spinner2.setAdapter(adapter2);

            }
        }.execute( "" );
    }


    @NonNull
    private String sexAsString(){

        if(maleRadioButton.isChecked()){
            return "M";
        }else if(femaleRadioButton.isChecked()){
            return "F";
        }else{
            return "O";
        }
    }
}