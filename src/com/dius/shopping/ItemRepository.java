package com.dius.shopping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ItemRepository {
    Map<String, Item> itemMap = new HashMap<>();

    public Item findBySku(String sku) {
        return itemMap.get(sku);
    }

    public ItemRepository() {
        Item ipad = new Item("ipd");
        ipad.name = "Super Ipad";
        ipad.price = new BigDecimal(549.99);
        itemMap.put("ipd", ipad);

        Item mbp = new Item("mbp");
        mbp.name = "MacBook Pro";
        mbp.price = new BigDecimal(1399.99);
        itemMap.put("mbp", mbp);

        Item appleTv = new Item("atv");
        appleTv.name = "AppleTv";
        appleTv.price = new BigDecimal(109.50);
        itemMap.put("atv", appleTv);

        Item vga = new Item("vga");
        vga.name = "VGA adapter";
        vga.price = new BigDecimal(30.00);
        itemMap.put("vga", vga);
    }
}
