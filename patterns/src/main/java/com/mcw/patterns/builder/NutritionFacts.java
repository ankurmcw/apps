package com.mcw.patterns.builder;

import java.util.Objects;

public class NutritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static NutritionFacts of(int servingSize, int servings) {
        return new NutritionFacts.Builder(servingSize, servings).build();
    }

    public static class Builder {
        // required params
        private final int servingSize;
        private final int servings;

        // optional params
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int value) {
            this.calories = value;
            return this;
        }

        public Builder fat(int value) {
            this.fat = value;
            return this;
        }

        public Builder sodium(int value) {
            this.sodium = value;
            return this;
        }

        public Builder carbohydrate(int value) {
            this.carbohydrate = value;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NutritionFacts that)) return false;
        return servingSize == that.servingSize && servings == that.servings && calories == that.calories
                && fat == that.fat && sodium == that.sodium && carbohydrate == that.carbohydrate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(servingSize, servings, calories, fat, sodium, carbohydrate);
    }

    @Override
    public String toString() {
        return "NutritionFacts{" +
                "servingSize=" + servingSize +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fat=" + fat +
                ", sodium=" + sodium +
                ", carbohydrate=" + carbohydrate +
                '}';
    }
}
