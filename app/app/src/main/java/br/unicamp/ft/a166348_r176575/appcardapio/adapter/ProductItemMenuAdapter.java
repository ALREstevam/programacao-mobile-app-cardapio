package br.unicamp.ft.a166348_r176575.appcardapio.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.net.PortUnreachableException;

import br.unicamp.ft.a166348_r176575.appcardapio.R;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Menu;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;


public class ProductItemMenuAdapter extends RecyclerView.Adapter<ProductItemMenuAdapter.ViewHolder>{

    /*
    * Variável para poder selecionar uma das linhas da listas.
     */
    private int selectedPos = RecyclerView.NO_POSITION;

    /*
        Essa interface serve apenas para que possamos nos comunicar com
        a activity que está em execução. Essa activity deverá ter o método
        onItemClick.
     */
    public interface OnItemClickListener {
        void onItemClick(SellableProduct prod);
    }

    private final Menu products;
    private final OnItemClickListener listener;

    public ProductItemMenuAdapter(Menu restautantMenu, OnItemClickListener listener) {
        this.products = restautantMenu;
        this.listener = listener;
    }


    /*
       Este método cria um novo ViewHolder. Será chamada apenas algumas vezes,
       dependendo de quantas linhas cabem na RecyclerView.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Vai ser chamado uma vez para cada viewHolder
        //Sempre vai pegar um código XML e vai definir uma linha na RecyclerView
        /*
           Usamos o LayoutInflater para transformar um arquivo XML em uma classe
           java. No caso, estamos o arquivo adapter_layout.xml
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_layout, parent, false);
        return new ViewHolder(v);
    }


    /*
       Este método é chamado sempre que uma mudança ocorre na RecyclerView e
       itens precisam ser substituídos.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//Toda vez que a interface precisar ser impressa (no scroll)
        holder.itemView.setBackgroundColor(selectedPos == position ? Color.GREEN :  Color.TRANSPARENT);
        holder.itemView.setSelected(selectedPos == position);


        holder.bind(products.getMenuProducts().get( position ), listener);
    }

    @Override public int getItemCount() {
        return products.getMenuProducts().size();
    }


    /*
        Esta classe fornece uma referência para as views dos itens
        que estão na RecyclerView.
     */
    class ViewHolder extends RecyclerView.ViewHolder  {

        private SimpleDraweeView image;
        private TextView name;
        private TextView price;
        private TextView description;
        private EditText amount;
        private Button plusButton;
        private Button minusButton;


        public ViewHolder(View itemView) {
            super(itemView);

            image      = (SimpleDraweeView) itemView.findViewById(R.id.image);
            name        = (TextView) itemView.findViewById(R.id.name);
            price      = (TextView) itemView.findViewById(R.id.price);
            description = (TextView) itemView.findViewById( R.id.description);
            amount      = (EditText) itemView.findViewById(R.id.amount);
            plusButton = (Button) itemView.findViewById( R.id.plus );
            minusButton = (Button) itemView.findViewById( R.id.minus );

        }



        public void bind(final SellableProduct product, final OnItemClickListener listener) {//Quando o adapter precisar fazer o bind (chamado anteriormente)
            /*name.setText(person.getName());
            imageView.setImageResource(person.getImageId());*/

            Uri uri = Uri.parse(product.getPicture());
            SimpleDraweeView draweeView = (SimpleDraweeView) itemView.findViewById(R.id.image);
            draweeView.setImageURI(uri);
            this.name.setText( product.getName() );
            this.price.setText(String.format( "R$ %.2f", product.getPrice()));
            description.setText( product.getDescription() );
            amount.setText( String.valueOf( product.getAmountInCart() ) );


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(product);
                    ViewHolder.this.onClick();//Redirecionando o gatilho para si mesmo
                }
            });

            plusButton.setOnClickListener( new View.OnClickListener(){
                @Override public void onClick(View v) {
                    listener.onItemClick(product);
                    ViewHolder.this.onPlusClick(amount, product);//Redirecionando o gatilho para si mesmo
                }
            } );

            minusButton.setOnClickListener( new View.OnClickListener(){
                @Override public void onClick(View v) {
                    listener.onItemClick(product);
                    ViewHolder.this.onMinusClick(amount, product);//Redirecionando o gatilho para si mesmo
                }
            } );

            /*amount.addTextChangedListener( new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String newValue = s.toString();
                    product.setAmountInCart( Integer.valueOf( newValue ) );
                    amount.setText( product.getAmountInCart() );
                }
            });*/



        }

        public void onClick(){
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            notifyItemChanged(selectedPos);
            /*
               getLayoutPosition retorna a posição atual deste viewHolder
             */
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }

        public void onPlusClick(EditText amount, SellableProduct prod){
            prod.addOne();
            amount.setText( String.valueOf( prod.getAmountInCart() ) );

            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);

        }

        public void onMinusClick(EditText amount, SellableProduct prod){
            prod.removeOne();
            amount.setText( String.valueOf( prod.getAmountInCart() ) );

            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);

        }

    }
}