package br.unicamp.ft.a166348_r176575.appcardapio.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import br.unicamp.ft.a166348_r176575.appcardapio.R;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.Order;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;


public class ProductItemOrderAdapter extends RecyclerView.Adapter<ProductItemOrderAdapter.ViewHolder> {

    private int selectedPos = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(SellableProduct prod);
    }

    private final Order products;
    private final OnItemClickListener listener;

    public ProductItemOrderAdapter(Order restautantOrder, OnItemClickListener listener) {
        this.products = restautantOrder;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.adapter_order_layout, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {//Toda vez que a interface precisar ser impressa (no scroll)
        //holder.itemView.setBackgroundColor(selectedPos == position ? Color.GREEN :  Color.TRANSPARENT);
        holder.itemView.setSelected(selectedPos == position);
        holder.bind(products.getSellables().get( position ), listener);
    }

    @Override public int getItemCount() {
        return products.getSellables().size();
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
        private TextView amountField;
        private TextView total;
        private TextView state;

        public ViewHolder(View itemView) {
            super(itemView);

            image           = (SimpleDraweeView)    itemView.findViewById(R.id.image);
            name            = (TextView)            itemView.findViewById(R.id.name);
            price           = (TextView)            itemView.findViewById(R.id.price);
            description     = (TextView)            itemView.findViewById( R.id.description);
            amountField     = (TextView)            itemView.findViewById(R.id.amountField);
            total           = (TextView)            itemView.findViewById( R.id.total);
            state           = (TextView)            itemView.findViewById( R.id.state );

            Log.e("", state.getText().toString());
        }



        public void bind(final SellableProduct product, final OnItemClickListener listener) {//Quando o adapter precisar fazer o bind (chamado anteriormente)
            /*name.setText(person.getName());
            imageView.setImageResource(person.getImageId());*/

            Uri uri = Uri.parse(product.getPicture());
            SimpleDraweeView draweeView = (SimpleDraweeView) itemView.findViewById(R.id.image);
            draweeView.setImageURI(uri);
            this.name.setText( product.getName() );
            this.price.setText(String.format( "R$ %.2f un.", product.getPrice()));
            this.amountField.setText( "x" + String.valueOf( product.getAmountInCart()) );
            this.total.setText( String.format( "R$ %.2f", product.getTotalPrice() ) );
            this.description.setText( product.getDescription() );
            this.state.setText( product.getState() );


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(product);
                    ViewHolder.this.onClick();//Redirecionando o gatilho para si mesmo
                }
            });
        }

        public void onClick(){
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }

    }
}