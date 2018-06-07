package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemMenuAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemOrderAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiAsyncTaskBasis;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiConsumer;
import br.unicamp.ft.a166348_r176575.appcardapio.database.LocalDbHelper;
import br.unicamp.ft.a166348_r176575.appcardapio.factory.ProductConverter;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.OrderSendable;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.VisitIdHolder;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Menu;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Order;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;
import br.unicamp.ft.a166348_r176575.appcardapio.utils.SimpleAlert;

public class CloseOrderActivity extends AppCompatActivity implements ProductItemOrderAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocalDbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Order ord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_close_order );

        dbHelper = new LocalDbHelper(CloseOrderActivity.this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        Intent intent = getIntent();

        if(intent != null){
            ord = (Order) intent.getParcelableExtra(MenuActivity.INTENT_ORDER);

            mRecyclerView = (RecyclerView) findViewById( R.id.aRecyclerView);
            mRecyclerView.setHasFixedSize( true );//Mudanças nos elementos da tabela não irão aumentar o tamanho dela
            mLayoutManager = new LinearLayoutManager( this );//recebe um context, a activity é um context
            mRecyclerView.setLayoutManager( mLayoutManager );//recycler view precisa de um layout manager

            mAdapter = new ProductItemOrderAdapter(ord, this);
            mRecyclerView.setAdapter( mAdapter );

            TextView total = (TextView) findViewById( R.id.totalSumValue );
            total.setText( String.format( "Total R$ %.2f", ord.getTotalPrice() ) );

            setStateForAllItems("Não pedido");





        }else{
            new SimpleAlert().alertOk( "Erro", "Um erro ocorreu ao tentar processar seu pedido", this );
        }
    }



    @Override
    public void onItemClick(SellableProduct prod) {
        //prod.setName( "Aaaaaaa" );
        //mAdapter.notifyDataSetChanged();
    }

    public void onPedirClicked(View view) {
        //new SimpleAlert().alertOk( "Pedidido enviado", "seu pedido foi enviado.", this );

        Toast.makeText( this, "Seu pedido está sendo processado...", Toast.LENGTH_SHORT ).show();

        //TextView stt = findViewById( R.id.state );
        //stt.setText( "Status: preparando..." );

        int items = mAdapter.getItemCount();
        final Context localContext = this;


        new ApiAsyncTaskBasis<String, Boolean>( "http://andreluiz342.pythonanywhere.com", "/order" ) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                setStateForAllItems("Enviando...");
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                ApiConsumer cons = new ApiConsumer( this.getApiUrl() );
                Genson genson = new Genson();

                OrderSendable order = makeOrder( ord.getSellables() );

                if(order != null){
                    String orderJson = genson.serialize( order );

                    Log.e("DEFAULT", orderJson);

                    try{
                        cons.post(this.getApiRoute(), orderJson);
                        return true;
                    }catch (Exception e){
                        e.printStackTrace();
                        return false;
                    }

                }

                return true;
            }

            @Override
            protected void onPostExecute(Boolean okresponse) {
                super.onPostExecute( okresponse );




                if(!okresponse){
                    new SimpleAlert().alertOk( "Erro ao fazer pedido", "Um erro ocorreu ao enviar o pedido.", localContext );
                }else{
                    //new SimpleAlert().alertOk( "Pedido feito com sucesso", "Seu pedido foi enviado para a cozinha.", localContext );
                    Button btnenv = (Button) findViewById( R.id.closeOrder );
                    Toast.makeText( localContext, "Pedido fechado.", Toast.LENGTH_SHORT ).show();
                    setStateForAllItems("Pedido fechado");
                    btnenv.setEnabled( false );
                }


            }
        }.execute( "" );


    }

    private OrderSendable makeOrder(List<SellableProduct> clientChoices){
        List<Long> prodIds = new ArrayList<>(  );

        int index = 0;
        for(SellableProduct prod : clientChoices) {
            for(int i = 0; i < prod.getAmount(); i++){
                prodIds.add( prod.getProdutctId() );
            }
        }

        OrderSendable order = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT visit_id FROM visit_data", null);
        if (cursor.moveToLast()){
            long visitId = cursor.getLong(0);

            Long[] idArr = new Long[prodIds.size()];
            idArr = prodIds.toArray(idArr);

            order = new OrderSendable(visitId, idArr);
        }
        cursor.close();
        return order;
    }

    private void setStateForAllItems(String state){
        for(SellableProduct prod : ord.getSellables()){
            prod.setState( state );
            mAdapter.notifyItemChanged(ord.getSellables().indexOf( prod ));
        }
    }

}