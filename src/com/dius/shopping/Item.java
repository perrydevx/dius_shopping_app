package com.dius.shopping;

import java.math.BigDecimal;

public class Item {
    public String sku;
    public String name;
    public BigDecimal price;

    public Item(String sku) {
        this.sku = sku;
    }
}
