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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nikita.bearadvice.Logic.Food;
import nikita.bearadvice.Logic.Item;
import nikita.bearadvice.Logic.MySecondAdapter;

public class foodGroupsActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    LinkedList<Item> drinksToFoodNew;
    boolean isDifferent = false;

    ListView listView;
    List<String> listRows;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_groups);


        //Создание списка кнопок (групп еды)
        Set templ1 = Food.GroupsFoodUser.keySet();
        String[] templ2 = (String[]) templ1.toArray(new String[templ1.size()]);
        LinkedList<String> groupList = Food.StringArrayToLinkedList(templ2);
        listRows = sortList(groupList);
        listView = (ListView) findViewById(R.id.listItemsView);

        MySecondAdapter adapter = new MySecondAdapter(this, listRows);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        LinkedList<Item> food = getFoodByGroupPosition(position);
//        MyBasicAdapter adapter = new MyBasicAdapter(this, food, false);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);

        String foodGroupName = listRows.get(position);
        if(needToStepOver(foodGroupName)) {
            LinkedList<String> food = Food.getFoodByGroupName(foodGroupName);
            String foodName = food.get(0);
            Intent intent = new Intent(this, ShowListItemsActivity.class);
            intent.putExtra("isDrinks", true);
            intent.putExtra("foodName", foodName);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, chooseDishActivity.class);
            intent.putExtra("isDrinks", false);
            intent.putExtra("name", foodGroupName);
            startActivity(intent);
        }
    }

    boolean needToStepOver(String foodGroupName) {
        LinkedList<String> food = Food.getFoodByGroupName(foodGroupName);
        if(food.size() == 1)
            return true;
        else
            return false;
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

