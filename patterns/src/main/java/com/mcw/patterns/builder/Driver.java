package com.mcw.patterns.builder;

public class Driver {

    public static void main(String[] args){
        NutritionFacts facts1 = new NutritionFacts.Builder(10, 20)
                .calories(30)
                .carbohydrate(40)
                .fat(50)
                .sodium(60)
                .build();

        System.out.println(facts1);

        NutritionFacts facts2 = NutritionFacts.of(10, 20);
        System.out.println(facts2);

        NyPizza pizza = new NyPizza.Builder(NyPizza.Size.SMALL)
                .addTopping(Pizza.Topping.PEPPER)
                .addTopping(Pizza.Topping.MUSHROOM)
                .addTopping(Pizza.Topping.ONION)
                .build();

        System.out.println(pizza);

        Calzone calzone = new Calzone.Builder()
                .addTopping(Pizza.Topping.HAM)
                .sauceInside()
                .build();

        System.out.println(calzone);
    }
}

