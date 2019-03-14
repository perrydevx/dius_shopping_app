package com.dius.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Checkout {
    PricingRules pricingRules;
    Map<String, Integer> itemMap = new HashMap<>();

    public Checkout(PricingRules pricingRules) {
        this.pricingRules = pricingRules;
    }

    public void scan(Item item) {
        String sku = item.sku;

        if (itemMap.get(sku) == null) itemMap.put(sku, 1);
        else itemMap.put(sku, itemMap.get(sku) + 1);
    }

    public BigDecimal total() {
        BigDecimal total = pricingRules.computeAll(itemMap);
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }
}
