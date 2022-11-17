package com.azure.eventhub.kafka.consumer;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Customer {
    Long CustomerID;
    String customerName;
    String itemName;
    Integer quantity;
    BigDecimal price;

    public void setCustomerID(Long customerID) {
        CustomerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Long getCustomerID() {
        return CustomerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }


}