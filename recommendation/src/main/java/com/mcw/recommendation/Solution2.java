package com.mcw.recommendation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution2 {

    public static void main(String[] args){
        System.out.println(new Solution2().recommend(
                List.of(
                        List.of("Casper", "Purple", "Wayfair"),
                        List.of("Purple", "Wayfair", "Tradesy"),
                        List.of("Wayfair", "Tradesy", "Peloton")
                )
        ));
    }

    public Map<String, Set<String>> recommend(List<List<String>> customerPurchases) {
        Map<String, Map<String, Integer>> coOccurrenceMap = new HashMap<>();
        for (List<String> merchantList: customerPurchases) {
            for (String merchant1: merchantList) {
                for (String merchant2: merchantList) {
                    if (merchant1.equals(merchant2)) continue;
                    coOccurrenceMap
                            .computeIfAbsent(merchant1, d -> new HashMap<>())
                            .put(merchant2, coOccurrenceMap.get(merchant1).getOrDefault(merchant2, 0) + 1);
                }
            }
        }

        Map<String, Set<String>> recommendations = new HashMap<>();
        coOccurrenceMap.forEach((k, vMap) -> {
            Map<Integer, Set<String>> freqToMerchant = new HashMap<>();
            int maxFreq = 0;
            for (Map.Entry<String, Integer> entry: vMap.entrySet()) {
                if (entry.getValue() > maxFreq) {
                    maxFreq = entry.getValue();
                }
                freqToMerchant.computeIfAbsent(entry.getValue(), d -> new HashSet<>()).add(entry.getKey());
            }
            recommendations.put(k, freqToMerchant.get(maxFreq));
        });

        return recommendations;
    }
}
