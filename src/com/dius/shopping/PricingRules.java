package com.dius.shopping;

import java.math.BigDecimal;
import java.util.Map;

public interface PricingRules {

     BigDecimal computeAll(Map<String, Integer> itemMap);

}
