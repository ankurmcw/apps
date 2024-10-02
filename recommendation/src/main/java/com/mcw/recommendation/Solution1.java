package com.mcw.recommendation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution1 {

    public static void main(String[] args){
      System.out.println(new Solution1().recommend(
              List.of(
                      List.of("Casper", "Purple", "Wayfair"),
                      List.of("Purple", "Wayfair", "Tradesy"),
                      List.of("Wayfair", "Tradesy", "Peloton")
              )
      ));
    }

    public Map<String, Set<String>> recommend(List<List<String>> customerPurchases) {
        AtomicInteger index = new AtomicInteger(-1);
        Map<String, Integer> merchantToIndex = new HashMap<>();
        Map<Integer, String> indexToMerchant = new HashMap<>();
        for (List<String> merchantList: customerPurchases) {
            for (String merchant: merchantList) {
                merchantToIndex.computeIfAbsent(merchant, d -> {
                    index.addAndGet(1);
                    return index.get();
                });
                indexToMerchant.putIfAbsent(merchantToIndex.get(merchant), merchant);
            }
        }

        int size = merchantToIndex.size();
        int[][] merchantMatrix = new int[size][size];
        for (List<String> merchantList: customerPurchases) {
            for (String merchant1: merchantList) {
                for (String merchant2: merchantList) {
                    if (merchant1.equals(merchant2)) continue;
                    int index1 = merchantToIndex.get(merchant1);
                    int index2 = merchantToIndex.get(merchant2);
                    merchantMatrix[index1][index2]++;
//                    System.out.printf("[%s][%s] = %d%n", merchant1, merchant2, merchantMatrix[index1][index2]);
                }
            }
        }

        Map<String, Set<String>> recommendations = new HashMap<>();
        for (int i=0; i<size; i++) {
            int max = 0;
            for (int j=0; j< size; j++) {
//                System.out.printf("%d ", merchantMatrix[i][j]);
                if (merchantMatrix[i][j] > max) {
                    max = merchantMatrix[i][j];
                }
            }
//            System.out.println();
//            System.out.printf("max=%d%n", max);

            for (int j=0; j< size; j++) {
                if (merchantMatrix[i][j] == max) {
                    recommendations
                            .computeIfAbsent(indexToMerchant.get(i), d -> new HashSet<>())
                            .add(indexToMerchant.get(j));
                }
            }
        }

//        System.out.println(merchantToIndex);
        return recommendations;
    }
}
