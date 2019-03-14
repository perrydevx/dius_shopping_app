package com.dius.shopping.test;

import com.dius.shopping.Checkout;
import com.dius.shopping.Item;
import com.dius.shopping.ItemRepository;
import com.dius.shopping.OpeningDaySpecial;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutTest {

    private ItemRepository repository = new ItemRepository();

    @Test
    public void when_no_items_then_pay_zero() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        assertEquals(new BigDecimal(0).setScale(2), co.total());
    }

    @Test
    public void when_appletv_is_less_than_three_then_pay_normal_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        co.scan(new Item("atv"));
        co.scan(new Item("atv"));

        BigDecimal expected = repository.findBySku("atv").price.multiply(new BigDecimal(2));
        assertEquals(expected.setScale(2), co.total());
    }

    @Test
    public void when_appletv_is_three_then_pay_discounted_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        co.scan(new Item("atv"));
        co.scan(new Item("atv"));
        co.scan(new Item("atv"));


        BigDecimal expected = repository.findBySku("atv").price.multiply(new BigDecimal(2));
        assertEquals(expected.setScale(2), co.total());
    }

    @Test
    public void when_appletv_is_4_then_pay_discounted_price_and_normal_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());

        for (int i=0; 4>i; i++) {
            co.scan(new Item("atv"));
        }

        BigDecimal discountedPriceOf3 = repository.findBySku("atv").price.multiply(new BigDecimal(2));
        BigDecimal normalPrice = repository.findBySku("atv").price.multiply(new BigDecimal(1));
        BigDecimal expected = discountedPriceOf3.add(normalPrice);
        assertEquals(expected.setScale(2), co.total());
    }

    @Test
    public void when_ipad_is_4_or_less_then_pay_normal_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());

        for (int i=0; 4>i; i++) {
            co.scan(new Item("ipd"));
        }

        BigDecimal expected = repository.findBySku("ipd").price.multiply(new BigDecimal(4));
        assertEquals(expected.setScale(2, RoundingMode.HALF_EVEN), co.total());
    }

    @Test
    public void when_ipad_is_more_than_4_then_pay_discounted_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());

        for (int i=0; 5>i; i++) {
            co.scan(new Item("ipd"));
        }

        BigDecimal expected = new BigDecimal(499.99).multiply(new BigDecimal(5));
        assertEquals(expected.setScale(2, RoundingMode.HALF_EVEN), co.total());
    }

    @Test
    public void when_mbp_is_more_than_or_equal_to_vga_then_pay_mbp_price_only() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        co.scan(new Item("mbp"));
        co.scan(new Item("vga"));

        BigDecimal expected = repository.findBySku("mbp").price;
        assertEquals(expected.setScale(2, RoundingMode.HALF_EVEN), co.total());
    }

    @Test
    public void when_mbp_is_less_than_vga_then_pay_mbp_price_and_remainder_of_vga_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        co.scan(new Item("mbp"));
        co.scan(new Item("vga"));
        co.scan(new Item("vga"));

        BigDecimal mbpPrice = repository.findBySku("mbp").price;
        BigDecimal vgaPrice = repository.findBySku("vga").price;
        BigDecimal expected = mbpPrice.add(vgaPrice);
        assertEquals(expected.setScale(2, RoundingMode.HALF_EVEN), co.total());
    }

    @Test
    public void when_mbp_is_only_purchase_then_pay_mbp_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        co.scan(new Item("mbp"));

        BigDecimal expected = repository.findBySku("mbp").price;
        assertEquals(expected.setScale(2, RoundingMode.HALF_EVEN), co.total());
    }

    @Test
    public void when_vga_is_only_purchase_then_pay_vga_price() {
        Checkout co = new Checkout(new OpeningDaySpecial());
        co.scan(new Item("vga"));

        BigDecimal expected = repository.findBySku("vga").price;
        assertEquals(expected.setScale(2, RoundingMode.HALF_EVEN), co.total());
    }
}