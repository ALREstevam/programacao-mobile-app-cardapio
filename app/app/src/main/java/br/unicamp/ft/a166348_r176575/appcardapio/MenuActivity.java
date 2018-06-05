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
                        "Pastel de Carne",
                        "Descrição do produto aqui uma descrição do produto",
                        "http://www.fotografiadecomida.com.br/wp-content/uploads/2013/12/MG_7283_alta_blog-660x270.jpg",
                        ProductType.SALGADO , 20.50, 0, 0, "Não pedido")
        );
        mockProducts.add(
                new SellableProduct(
                        "Prato feito",
                        "Descrição do produto aqui uma descrição do produto",
                        "http://www.fotografiadecomida.com.br/wp-content/uploads/2013/12/MG_6274_m_alta_blog-660x270.jpg",
                        ProductType.SALGADO , 20.50, 0, 0, "Não pedido")
        );

        mockProducts.add(
                new SellableProduct(
                        "Hamburguer",
                        "Descrição do produto aqui uma descrição do produto",
                        "http://i.dailymail.co.uk/i/pix/2015/12/14/15/03C5A3E8000005DC-3359403-image-a-63_1450106294393.jpg",
                        ProductType.DOCE , 20.50, 0, 0, "Não pedido")
        );

        mockProducts.add(
                new SellableProduct(
                        "Sopa da Casa",
                        "Descrição do produto aqui uma descrição do produto",
                        "https://guiadacozinha.com.br/wp-content/uploads/2017/02/sopa-cremosa-legumes-1.jpg",
                        ProductType.DOCE , 20.50, 0, 0, "Não pedido")
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 5",
                        "Descrição do produto aqui uma descrição do produto",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0, "Não pedido")
        );
        mockProducts.add(
                new SellableProduct(
                        "Produto 6",
                        "Descrição do produto aqui uma descrição do produto",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0, "Não pedido")
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 7",
                        "Descrição do produto aqui uma descrição do produto",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0,"Não pedido")
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 8",
                        "Descrição do produto aqui uma descrição do produto",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0,"Não pedido")
        );

        mockProducts.add(
                new SellableProduct(
                        "Produto 9",
                        "Descrição do produto aqui uma descrição do produto",
                        "https://upload.wikimedia.org/wikipedia/commons/2/2e/Fast_food_meal.jpg",
                        ProductType.DOCE , 20.50, 0, 0,"Não pedido")
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



