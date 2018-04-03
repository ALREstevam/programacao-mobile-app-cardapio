package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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

    }
}

//String text = intent.getStringExtra(ButtonActivity.INTENT_TEXT);
