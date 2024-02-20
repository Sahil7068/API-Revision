package org.example;

import io.restassured.path.json.JsonPath;

import static files.payload.jsonPath;

public class complexJsonPath {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(jsonPath());

        //print number of courses
        int sizee = js.getInt("courses.size()");
        System.out.println(sizee);

        // print purchased amount

        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("The puchase amount is " + purchaseAmount);

        // print title of the first course
        String title = js.getString("courses.title[0]");
        System.out.println(title);

        String title2 = js.getString("courses.title[2]");
        System.out.println(title2);

        //print course title and their prices

        for (int i =0; i<sizee; i++) {
            String title1 = js.getString("courses[" + i + "].title");
            int price = js.getInt("courses["+i+"].price");
            System.out.println("The title is " + title1 + " and price " + price );
        }

        // No of copies sold by RPA

        System.out.println("No of copies sold by RPA");
        System.out.println(js.getInt("courses[2].copies"));

        System.out.println("No of copies sold by RPA 2");


        for(int i =0; i<sizee; i++){
            String title1 = js.getString("courses[" + i + "].title");


            if(title1.equalsIgnoreCase("RPA")){
                System.out.println(js.get("courses[" + i + "].copies").toString());

            }
        }


        // price amount = price

        int pa = js.getInt("dashboard.purchaseAmount");
        System.out.println(pa);

        int add =0;

        for (int i =0; i<sizee; i++){
            int p1 = js.get("courses["+i+"].price");
            int c1 = js.get("courses["+i+"].copies");
            add = add + (p1*c1);

        }
        System.out.println("The total price is " + add);

        if (pa == add){
            System.out.println("The price is accurate");
        }
    }
}
