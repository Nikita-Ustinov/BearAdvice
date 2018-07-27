package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nikita.bearadvice.Logic.Drink;
import nikita.bearadvice.Logic.Food;
import nikita.bearadvice.Logic.Item;
import nikita.bearadvice.Logic.MyBasicAdapter;
import nikita.bearadvice.Logic.MySecondAdapter;

import static nikita.bearadvice.Logic.Food.getFoodByGroupPosition;
import static nikita.bearadvice.Logic.Food.getItem;

public class chooseDishActivity  extends AppCompatActivity  implements AdapterView.OnItemClickListener {

    LinkedList<Item> drinksToFoodNew;
    boolean isDifferent = false;
    ListView listView;
    List<Item> listRows;
    LinkedList<String> FoodList;

    private AdView mAdView;

    boolean isDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_dish);
        listRows = new ArrayList<Item>();
        listView = (ListView) findViewById(R.id.listItemsView);
        Intent intent = getIntent();
        String foodGroupName = intent.getStringExtra("name");
        LinkedList<String> food = Food.getFoodByGroupName(foodGroupName);
        food = sortList(food);
//        int position = intent.getIntExtra("position", -1);
//        LinkedList<Item> food = getFoodByGroupPosition(position);
        FoodList = food;
        if(food.size() == 1) {
            showAlokohol();
        }
        MySecondAdapter adapter = new MySecondAdapter(this, FoodList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        //Реклама
        MobileAds.initialize(this, "ca-app-pub-6533731312932944/4620797759");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-6533731312932944/4620797759");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // TODO: Add adView to your view hierarc
    }

    void showAlokohol() {
        String foodName = FoodList.get(0);
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        intent.putExtra("foodName", foodName);
        startActivity(intent);
    }

    public void showAlokohol(LinkedList<String> food, Class context) {
        String foodName = food.get(0);
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        intent.putExtra("foodName", foodName);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String foodName = FoodList.get(position);
//        LinkedList<Item> drinksToFood = Drink.getListDrinksToFood(foodName);
//        isDrinks = true;
//        drinksToFoodNew = drinksToFood;
//        MyBasicAdapter adapter = new MyBasicAdapter(this, drinksToFood, isDrinks);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);
//        isDifferent = true;


        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        intent.putExtra("foodName", foodName);
        startActivity(intent);
    }

    static LinkedList<String> sortList(LinkedList<String> input) {
        String templ;
        int ii = 0, jj = 0;
        try {
            for (int i = 0; i < input.size() - 1; i++) {
                ii=i;
                for (int j = i + 1; j < input.size(); j++) {
                    jj=j;
                    if (input.get(i).compareTo(input.get(j))>0) {
                        changeItems(input, i, j);
                    }
//                    else if ((input.get(i).charAt(1) > input.get(j).charAt(1))&&(input.get(i).charAt(0) > input.get(j).charAt(0))) {
//                        changeItems(input, i, j);
//                    }j
//                    else if (ListItems.get(i).Name.charAt(2) > ListItems.get(j).Name.charAt(2)) {
//                        changeItems(ListItems, i, j);
//                    }
                }
            }
        }catch (Exception e) {}
        return input;
    }

    static void changeItems(LinkedList<String> list, int first, int second) {
        String templ;
        templ = list.get(second);
        list.set(second, list.get(first));
        list.set(first, templ);
    }
}
