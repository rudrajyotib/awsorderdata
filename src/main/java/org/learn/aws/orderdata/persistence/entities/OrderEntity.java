package org.learn.aws.orderdata.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_data")
public class OrderEntity {


    @Id
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "product")
    private String product;

    public OrderEntity() {
    }

    public OrderEntity(String orderNumber, String product) {

        this.orderNumber = orderNumber;
        this.product = product;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getProduct() {
        return product;
    }


}
