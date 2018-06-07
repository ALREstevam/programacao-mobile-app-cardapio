package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemMenuAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiAsyncTaskBasis;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiConsumer;
import br.unicamp.ft.a166348_r176575.appcardapio.factory.OrderFactory;
import br.unicamp.ft.a166348_r176575.appcardapio.factory.ProductConverter;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
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
        Fresco.initialize( this );
        mRecyclerView = (RecyclerView) findViewById( R.id.aRecyclerView );
        mRecyclerView.setHasFixedSize( true );//Mudanças nos elementos da tabela não irão aumentar o tamanho dela
        mLayoutManager = new LinearLayoutManager( this );//recebe um context, a activity é um context
        mRecyclerView.setLayoutManager( mLayoutManager );//recycler view precisa de um layout manager

        //List<SellableProduct> mockProducts = new ArrayList<>();

        final ProductItemMenuAdapter.OnItemClickListener reference = this;


        new ApiAsyncTaskBasis<String, String>( "http://andreluiz342.pythonanywhere.com", "/products" ) {
            @Override
            protected String doInBackground(String... strings) {
                ApiConsumer cons = new ApiConsumer( this.getApiUrl() );
                String prodsJson = cons.get( this.getApiRoute() );
                return prodsJson;
            }

            @Override
            protected void onPostExecute(String prodsJson) {
                //super.onPostExecute( prodsJson );

                Genson genson = new Genson();
                List<Product> lst1 = genson.deserialize( prodsJson, new GenericType<List<Product>>() {
                } );

                System.out.println( prodsJson );

                for (Product prod : lst1) {
                    Log.e( "DEBUG!", prod.toString() );
                }


                List<SellableProduct> sellableProducts = ProductConverter.productsToSellables( lst1 );

                menu = new Menu( sellableProducts );
                mAdapter = new ProductItemMenuAdapter( menu, reference );
                mRecyclerView.setAdapter( mAdapter );

            }
        }.execute( "" );


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





