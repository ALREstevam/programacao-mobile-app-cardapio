package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemOrderAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Order;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;
import br.unicamp.ft.a166348_r176575.appcardapio.utils.SimpleAlert;

public class CloseOrderActivity extends AppCompatActivity implements ProductItemOrderAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_close_order );

        Intent intent = getIntent();
        Order ord = null;

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

        Button btnenv = (Button) findViewById( R.id.closeOrder );
        btnenv.setEnabled( false );

        for(int i = 0; i < items; i++){
            View v = mRecyclerView.getChildAt( i );
            final TextView t = (TextView) v.findViewById( R.id.state );

            Runnable r = new Runnable() {
                @Override
                public void run(){
                    t.setText( "Enviando para a cozinha..." );

                    Runnable r1 = new Runnable() {
                        @Override
                        public void run(){
                            t.setText( "Preparando..." );

                            Runnable r2 = new Runnable() {
                                @Override
                                public void run(){
                                    t.setText( "Pronto..." );
                                }
                            };

                            Handler h2 = new Handler();
                            h2.postDelayed(r2, 50000); // <-- the "1000" is the delay time in miliseconds.


                        }
                    };

                    Handler h1 = new Handler();
                    h1.postDelayed(r1, 5000); // <-- the "1000" is the delay time in miliseconds.

                }
            };

            Handler h = new Handler();
            h.postDelayed(r, 1000); // <-- the "1000" is the delay time in miliseconds.





        }

    }
}

//String text = intent.getStringExtra(ButtonActivity.INTENT_TEXT);
