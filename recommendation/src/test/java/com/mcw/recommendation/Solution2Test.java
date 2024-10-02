package com.mcw.recommendation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Solution2Test {

    private final Solution2 instance = new Solution2();

    @Test
    void validate_recommendations() {
        List<List<String>> customerPurchases =  List.of(
                List.of("Casper", "Purple", "Wayfair"),
                List.of("Purple", "Wayfair", "Tradesy"),
                List.of("Wayfair", "Tradesy", "Peloton")
        );

        Map<String, Set<String>> recommendations = instance.recommend(customerPurchases);
        assertNotNull(recommendations);
        assertTrue(recommendations.size() > 0);


    }
}