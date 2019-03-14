package com.dius.shopping;

import java.math.BigDecimal;
import java.util.Map;

public class OpeningDaySpecial implements PricingRules {

    private BigDecimal total = new BigDecimal(0);
    private ItemRepository repository = new ItemRepository();

    public BigDecimal computeAll(Map<String, Integer> itemMap) {
        chargedVga(itemMap);

        itemMap.forEach((k, v) -> {
            total = total.add(compute(k, v));
        });

        return total;
    }

    private BigDecimal compute(String sku, Integer count) {
        Item item = repository.findBySku(sku);
        BigDecimal price = item.price;

        if (sku.equals("atv")) {
            return computeAppleTv(price, count);
        } else if (sku.equals("ipd")) {
            return computeIpad(price, count);
        } else if (sku.equals("vga")) {
            return computeVga(price, count);
        } else if (sku.equals("mbp")) {
            return new BigDecimal(count).multiply(price);
        }
        return new BigDecimal(0);
    }

    private BigDecimal computeAppleTv(BigDecimal price, Integer count) {
        BigDecimal total = new BigDecimal(0);
        BigDecimal promoPrice = price.multiply(new BigDecimal(2));

        if (count > 0) {
            // price is 109.50 x 2 = 219
            BigDecimal discountedAtv = new BigDecimal(count / 3).multiply(promoPrice);
            BigDecimal normalAtv = new BigDecimal(count % 3).multiply(price);

            total = total.add(discountedAtv);
            total = total.add(normalAtv);
        }
        return total;
    }

    private BigDecimal computeIpad(BigDecimal price, Integer count) {
        BigDecimal total = new BigDecimal(0);
        if (count > 4) {
            // price is 499.99
            BigDecimal discountedIpd = new BigDecimal(count * 499.99);
            total = total.add(discountedIpd);
        } else {
            BigDecimal normalIpad = new BigDecimal(count).multiply(price);
            total = total.add(normalIpad);
        }
        return total;
    }

    private BigDecimal computeVga(BigDecimal price, Integer count) {
        BigDecimal total = new BigDecimal(0);

        if (count > 0) {
            total = total.add(new BigDecimal(count).multiply(price));
        }

        return total;
    }

    private void chargedVga(Map<String, Integer> itemMap) {
        if (itemMap.get("vga") != null && itemMap.get("mbp") != null) {
            int chargedVga = itemMap.get("vga") - itemMap.get("mbp");

            if (chargedVga > 0) {
                itemMap.put("vga", chargedVga);
            } else {
                itemMap.put("vga", 0);
            }
        }
    }
}
