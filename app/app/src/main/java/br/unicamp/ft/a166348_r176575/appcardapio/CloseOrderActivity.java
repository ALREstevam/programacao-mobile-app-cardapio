package br.unicamp.ft.a166348_r176575.appcardapio;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemMenuAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.adapter.ProductItemOrderAdapter;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiAsyncTaskBasis;
import br.unicamp.ft.a166348_r176575.appcardapio.apiconsumer.ApiConsumer;
import br.unicamp.ft.a166348_r176575.appcardapio.database.LocalDbHelper;
import br.unicamp.ft.a166348_r176575.appcardapio.factory.ProductConverter;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.OrderSendable;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProdStatus;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.VisitIdHolder;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Menu;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Order;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Sellable;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;
import br.unicamp.ft.a166348_r176575.appcardapio.utils.SimpleAlert;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.UpdatePayload;

public class CloseOrderActivity extends AppCompatActivity implements ProductItemOrderAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocalDbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Order ord = null;
    private String firebaseUserToken =  FirebaseInstanceId.getInstance().getToken();
    private String payloadJson;
    final private Context reference = this;
    final private ProductItemOrderAdapter.OnItemClickListener reference2 = this;


    private BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            payloadJson = intent.getStringExtra("payload");

            payloadJson = "[" + payloadJson + "]";

            Log.e ("PAYLOAD", payloadJson );

            Genson genson = new Genson(  );

            List<UpdatePayload> lst1 = genson.deserialize( payloadJson, new GenericType<List<UpdatePayload>>(){});
            UpdatePayload payloadObj = lst1.get( 0 );


            ContentValues contentValues = new ContentValues();
            contentValues.put("state", payloadObj.getOrder_status());

            String   whereClause = "cart_id = ?";
            String[] whereArgs   = new String[]{payloadObj.getLocal_id()};

            sqLiteDatabase.update("product_on_cart", contentValues, whereClause, whereArgs);

            for(SellableProduct prod : ord.getSellables()){
                Log.e("LOCALID", prod.getLocalId() + " " + Long.parseLong(payloadObj.getLocal_id()));

                if(prod.getLocalId() == Long.parseLong(payloadObj.getLocal_id())){
                    Log.e("FOUND SELLBLE!", prod.toString());
                    prod.setState( payloadObj.getOrder_status() );
                    mAdapter.notifyItemChanged( ord.getSellables().indexOf( prod ) );
                }
            }

            mAdapter.notifyDataSetChanged();



        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_close_order );
        ord = null;
        Log.e("FIREBASE USER TOKEN:", firebaseUserToken);

        IntentFilter myFilter = new IntentFilter("MyServiceBroadcast");
        LocalBroadcastManager.getInstance(this).registerReceiver(myBroadcastReceiver, myFilter);

        dbHelper = new LocalDbHelper(CloseOrderActivity.this);
        sqLiteDatabase = dbHelper.getReadableDatabase();

        Intent intent = getIntent();

        if(intent != null){
            ord = (Order) intent.getParcelableExtra(MenuActivity.INTENT_ORDER);

            for(SellableProduct prod : ord.getSellables()){
                prod.setStatusEnum( ProdStatus.NAO_PEDIDO );
            }

            getOldSells();

            mRecyclerView = (RecyclerView) findViewById( R.id.aRecyclerView);
            mRecyclerView.setHasFixedSize( true );//Mudanças nos elementos da tabela não irão aumentar o tamanho dela
            mLayoutManager = new LinearLayoutManager( this );//recebe um context, a activity é um context
            mRecyclerView.setLayoutManager( mLayoutManager );//recycler view precisa de um layout manage
            mAdapter = new ProductItemOrderAdapter(ord, this);
            mRecyclerView.setAdapter( mAdapter );
        }else{
            new SimpleAlert().alertOk( "Erro", "Um erro ocorreu ao tentar processar seu pedido", this );
        }
        intent = null;

        TextView total = (TextView) findViewById( R.id.totalSumValue );
        String totalPrice = String.format( "Total R$ %.2f", ord.getTotalPrice() );
        total.setText(totalPrice);
    }

    @Override
    protected void onPause() {
        Intent intent = getIntent();
        if(intent != null) {
            Order locOrd = (Order) intent.getParcelableExtra( MenuActivity.INTENT_ORDER );
            ord.setSellables( locOrd.getSellables() );
        }else{
            ord.setSellables( new ArrayList<SellableProduct>(  ) );
        }
        super.onPause();
    }


    @Override
    public void onItemClick(SellableProduct prod) {
        //prod.setName( "Aaaaaaa" );
        //mAdapter.notifyDataSetChanged();
    }

    public void onPedirClicked(View view) {
        if(ord.getNonSubmitedSellables().size() == 0){
            new SimpleAlert().alertOk( "Não há produtos adicionados", "Parece que você não adicionou nenhum novo produto ao seu pedido, adicione um para poder concluí-lo, use o botão voltar para acessar o menu.", this );
            return;
        }

        Toast.makeText( this, "Seu pedido está sendo processado...", Toast.LENGTH_SHORT ).show();

        //TextView stt = findViewById( R.id.state );
        //stt.setText( "Status: preparando..." );

        int items = mAdapter.getItemCount();
        final Context localContext = this;


        new ApiAsyncTaskBasis<String, Boolean>( "http://andreluiz342.pythonanywhere.com", "/order" ) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                for(SellableProduct prod : ord.getNonSubmitedSellables()) {
                    prod.setStatusEnum( ProdStatus.ENVIANDO );
                    mAdapter.notifyItemChanged( ord.getSellables().indexOf( prod ) );
                }
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                    try{
                        ApiConsumer cons = new ApiConsumer( this.getApiUrl() );
                        Genson genson = new Genson();
                        List<SellableProduct> products = ord.getSellablesWithStatus( ProdStatus.ENVIANDO );
                        List<Long> localIdS = new ArrayList<>(  );
                        for(SellableProduct prod : products) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put( "product_id", prod.getProdutctId() );
                            contentValues.put( "amount", prod.getAmount() );
                            contentValues.put( "state", ProdStatus.ENVIADO_PARA_A_COZINHA.getStatusAsText() );
                            long id = sqLiteDatabase.insert( "product_on_cart", null, contentValues );
                            Log.e( "DATABASE", "inserting product" );

                            if (id == -1) {
                                return false;
                            }
                            prod.setLocalId( id );
                        }


                        OrderSendable order = makeOrder( products );
                        Log.e("OBJECT ORDER", order.toString());

                        String orderJson = genson.serialize( order );
                        Log.e("ORDERJSON", orderJson);

                        String answ = cons.post(this.getApiRoute(), orderJson);
                        Log.e("DEBUG", answ);

                        return true;
                    }catch (Exception e){
                        e.printStackTrace();
                        return false;
                    }
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
                    btnenv.setEnabled( false );

                    for(SellableProduct prod : ord.getSellablesWithStatus( ProdStatus.ENVIANDO )) {
                        prod.setStatusEnum( ProdStatus.ENVIADO_PARA_A_COZINHA );
                        mAdapter.notifyItemChanged( ord.getSellables().indexOf( prod ) );
                    }

                }
            }
        }.execute( "" );


    }

    private OrderSendable makeOrder(List<SellableProduct> clientChoices){
        List<Long> prodIds = new ArrayList<>(  );
        List<Long> localIdList = new ArrayList<>(  );

        int index = 0;

        for(SellableProduct prod : clientChoices) {
            for(int i = 0; i < prod.getAmount(); i++){
                prodIds.add( prod.getProdutctId() );
                localIdList.add(prod.getLocalId());
            }
        }

        Log.e("choices is empty ", clientChoices.size() + "");
        for(long l : prodIds){
            Log.e("CLIENT CHOOSED ID: ", l + " ");
        }

        OrderSendable order = null;

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT visit_id FROM visit_data", null);
        if (cursor.moveToLast()){

            long visitId = cursor.getLong(0);
            Long[] idArr = new Long[prodIds.size()];
            Long[] localIdArr = new Long[prodIds.size()];
            idArr = prodIds.toArray(idArr);
            localIdArr = localIdList.toArray(localIdArr);

            order = new OrderSendable(visitId, idArr, localIdArr, ProdStatus.ENVIADO_PARA_A_COZINHA.getStatusAsText());
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


    private void getOldSells(){

        class ProdLocalDbData{
            private int     id;
            private int     amount;
            private String  state;
            private long    localid;


            public ProdLocalDbData(int id, int amount, String state, long localid) {
                this.id = id;
                this.amount = amount;
                this.state = state;
                this.localid = localid;
            }

            public long getLocalid() {
                return localid;
            }

            public void setLocalid(long localid) {
                this.localid = localid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            @Override
            public String toString() {
                return "prodLocalDbData{" +
                        "id=" + id +
                        ", amount=" + amount +
                        ", state='" + state + '\'' +
                        '}';
            }
        }


        class ProductIdSendable{
            private long productId;

            public ProductIdSendable(long productId) {
                this.productId = productId;
            }

            public ProductIdSendable() {
            }

            public long getProductId() {
                return productId;
            }

            public void setProductId(long productId) {
                this.productId = productId;
            }
        }


        final List<ProdLocalDbData> data = new ArrayList<>(  );
        final List<SellableProduct> products = new ArrayList<>(  );

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT cart_id, insertion_date, product_id, amount, state FROM product_on_cart", null);
        if (cursor.moveToFirst()){
            String str = "";
            do {
                long    localid    = cursor.getLong(0);
                int     id    = cursor.getInt(2);
                int     amount = cursor.getInt(3);
                String  state = cursor.getString(4);
                ProdLocalDbData temp  = new ProdLocalDbData(id, amount, state, localid);
                data.add(temp);
                
                Log.e("DATA FROM SQLITE", temp.toString());
            }while(cursor.moveToNext());
        }
        cursor.close();

        Log.e("DATA FROM SQLITE SIZE", data.size() + "");

        for(ProdLocalDbData dt : data){
            Log.e("DEBUG", dt.toString());
        }

        final TextView total = (TextView) findViewById( R.id.totalSumValue );
            new ApiAsyncTaskBasis<String, Boolean>( "http://andreluiz342.pythonanywhere.com", "/product" ){

                @Override
                protected Boolean doInBackground(String... strings) {
                    ApiConsumer cons = new ApiConsumer( this.getApiUrl() );
                    Genson genson = new Genson();

                    for(ProdLocalDbData prod : data){
                        String serializedProd = genson.serialize( new ProductIdSendable(prod.getId()));
                        Log.e( "SERIALIZED PROD", serializedProd );

                        String prodJson = cons.post(this.getApiRoute(), serializedProd);

                        Log.e("PRODS JSON", prodJson);

                        List<Product> lst1 = genson.deserialize( prodJson, new GenericType<List<Product>>(){});
                        Product productFromApi = lst1.get( 0 );

                        //productsFromApi.add( productFromApi );

                        SellableProduct tempSellable = ProductConverter.productToSellable( productFromApi );
                        tempSellable.setState( prod.getState() );
                        tempSellable.setAmount( prod.getAmount() );
                        tempSellable.setProdutctId( prod.getId() );
                        tempSellable.setLocalId( prod.getLocalid() );

                        Log.e( "TEMP SELLABLE!", tempSellable.toString() );

                        //products.add( tempSellable );

                        if(!ord.getSellables().contains(tempSellable) && tempSellable.getStatusEnum() != ProdStatus.PEDIDO_ANTIGO){
                            ord.getSellables().add( tempSellable );
                        }

                        Log.e("ORD SELLABLES", ord.getSellables().size()+ "");
                        publishProgress(  );
                    }

                    return null;
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate( values );
                    mAdapter.notifyDataSetChanged();
                    String totalPrice = String.format( "Total R$ %.2f", ord.getTotalPrice() );
                    total.setText(totalPrice);
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute( aBoolean );
                    mAdapter.notifyDataSetChanged();
                    String totalPrice = String.format( "Total R$ %.2f", ord.getTotalPrice() );
                    total.setText(totalPrice);
                }
            }.execute( "" );
    }

    /*
    db.execSQL( " CREATE TABLE product_on_cart ( " +
                "cart_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "insertion_date datetime DEFAULT CURRENT_TIMESTAMP, " +
                "product_id INTEGER, " +
                "amount iNTEGER, " +
                "state CHAR(50))" );
    */

}