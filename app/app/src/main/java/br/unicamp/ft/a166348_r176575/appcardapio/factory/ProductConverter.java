package br.unicamp.ft.a166348_r176575.appcardapio.factory;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProdStatus;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.Product;
import br.unicamp.ft.a166348_r176575.appcardapio.pojo.ProductSendable;
import br.unicamp.ft.a166348_r176575.appcardapio.sell.SellableProduct;

/**
 * Created by andre on 05/06/2018.
 */

public class ProductConverter {

    static public ProductSendable productToSendableProduct(Product prod){
        return new ProductSendable(prod.getName(), prod.getDescription(), prod.getPicture(), prod.getPrice(), prod.getProdutctId());
    }

    static public SellableProduct sendableToSellable(ProductSendable prod){
        return new SellableProduct( prod.getName(), prod.getDescription(), prod.getPicture(), prod.getPrice(), prod.getProdutctId(), 0, ProdStatus.SEM_STATUS.getStatusAsText() );
    }

    static public SellableProduct productToSellable(Product product){
        return ProductConverter.sendableToSellable( ProductConverter.productToSendableProduct( product ) );
    }

    static public List<SellableProduct> productsToSellables(List<Product> prods){
        List<SellableProduct> answer = new ArrayList<>( prods.size() );
        for(Product prod : prods){
            answer.add( ProductConverter.productToSellable( prod ) );
        }
        return answer;
    }


}
