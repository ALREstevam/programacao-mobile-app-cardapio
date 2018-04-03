package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemMenuAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.factory.OrderFactory;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProductType;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Menu;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Order;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;
import br.unicamp.ft.a166348_r176575.appcardapio.utils.SimpleAlert;

public class MenuActivity extends AppCompatActivity implements ProductItemMenuAdapter.OnItemClickListener{


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Menu menu = null;
    public static String INTENT_ORDER = "order";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_menu );
        Fresco.initialize(this);
        mRecyclerView = (RecyclerView) findViewById( R.id.aRecyclerView);


        mRecyclerView.setHasFixedSize( true );//Mudanças nos elementos da tabela não irão aumentar o tamanho dela

        mLayoutManager = new LinearLayoutManager( this );//recebe um context, a activity é um context
        mRecyclerView.setLayoutManager( mLayoutManager );//recycler view precisa de um layout manager


        List<SellableProduct> mockProducts = new ArrayList<>();
        mockProducts.add(
                new SellableProduct(
                        "Produto 1",
                        "Um produto assim bem assim e assado assim",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.SALGADO , 20.50, 0, 0)
        );
        mockProducts.add(
                new SellableProduct(
                        "Produto 2",
                        "Um produto assim bem assim e assado assim",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.SALGADO , 20.50, 0, 0)
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 3",
                        "Um produto assim bem assim e assado assim",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0)
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 4",
                        "Um produto assim bem assim e assado assim",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0)
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 5",
                        "Um produto assim bem assim e assado assim",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0)
        );



        menu = new Menu( mockProducts );


        mAdapter = new ProductItemMenuAdapter(menu, this);
        mRecyclerView.setAdapter( mAdapter );

    }



    @Override
    public void onItemClick(SellableProduct prod) {
        //Toast.makeText( this, prod.getName(), Toast.LENGTH_LONG ).show();

    }

    void onCloseOrderClicked(View view){
        Order ord = new OrderFactory().createOrder( this.menu );

        if(ord.hasSellable()){
            Intent intent = new Intent(this, CloseOrderActivity.class);
            //intent.putExtra(INTENT_ORDER, ord);
            intent.putExtra(INTENT_ORDER, ord);
            startActivity(intent);

        }
        else{
            new SimpleAlert().alertOk( "Não há produtos adicionados", "Parece que você não adicionou nenhum produto ao seu pedido, adicione um para poder concluí-lo", this );
        }

    }

    public void onMinusClicked(View view) {
    }

    public void onPlusClicked(View view){

    }
}


