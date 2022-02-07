package model.converters;

import model.customer.Customer;
import model.customer.Purchase;
import org.bson.Document;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerConverter {
    PurchaseConverter purchaseConverter = new PurchaseConverter();
    public Customer documentToCustomer(Document d){
        List<Document> purchasesDocument = d.getList("purchases", Document.class);
        List<Purchase> purchases = Collections.emptyList();
        if (purchasesDocument != null){
            purchases = purchasesDocument.stream()
                    .map(document -> purchaseConverter.toPurchaseFromDocument(document))
                    .collect(Collectors.toList());
        }
        return Customer.builder()
                .id(d.getString("_id"))
                .name(d.getString("name"))
                .telephone(d.getString("telephone"))
                .address(d.getString("address"))
                .purchases(purchases)
                .build();
    }

    public Document customerToDocument(Customer c){
        List<Purchase> purchases = c.getPurchases();
        if (c.getPurchases() == null || c.getPurchases().isEmpty()){
            purchases = Collections.emptyList();
        }
        return new Document()
                .append("_id", c.getId())
                .append("name", c.getName())
                .append("telephone", c.getTelephone())
                .append("address", c.getAddress())
                .append("purchases", purchases);
    }
}
