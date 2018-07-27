package nikita.bearadvice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nikita.bearadvice.Logic.Drink;
import nikita.bearadvice.Logic.Item;
import nikita.bearadvice.Logic.MyBasicAdapter;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class ShowListItemsActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

//    public static final String[] drinksNames = Drink.getDrinksNames();
//    public static final String[] foodsNames = Food.getFoodNames();

    LinkedList<Item> drinksToFoodNew;
    boolean isDifferent = false;

    ListView listView;
    List<Item> listRows;
    LinkedList<Item> FoodList;
    boolean isDrinks;
    private AdView mAdView;

    LinkedList<String> GlobalDrinkNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Реклама
        MobileAds.initialize(this, "ca-app-pub-6533731312932944/4620797759");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-6533731312932944/4620797759");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // TODO: Add adView to your view hierarc

        listRows = new LinkedList<Item>();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        ArrayList<String>  drinksListToshow = intent.getStringArrayListExtra("drinksListToshow");
        String foodName = intent.getStringExtra("foodName");
        listView = (ListView) findViewById(R.id.listItemsView);
        isDrinks = intent.getBooleanExtra("isDrinks", true);
        if(drinksListToshow != null){
            GlobalDrinkNames = new LinkedList<>();
            for(int i=0; i<drinksListToshow.size(); i++) {
                for(int j=0; j< Drink.ListItems.size(); j++) {
                    if (drinksListToshow.get(i).equals(Drink.ListItems.get(j).Name)) {
                        listRows.add(Drink.ListItems.get(j));
                        GlobalDrinkNames.add(Drink.ListItems.get(j).Name);
                        break;
                    }
                }
            }
//            listRows = (LinkedList<Item>) listRows;
            MyBasicAdapter adapter = new MyBasicAdapter(this, listRows, isDrinks);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
        }
        else {
            if ((position==-1)&&(foodName == null)) {
                listRows = Drink.ListItems;
                MyBasicAdapter adapter = new MyBasicAdapter(this, listRows, isDrinks);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this);
            }
            else if(foodName != null) {
                listRows = Drink.getListDrinksToFood(foodName);
                drinksToFoodNew = (LinkedList<Item>) listRows;
                MyBasicAdapter adapter = new MyBasicAdapter(this, listRows, isDrinks);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int drinkID;
        if(drinksToFoodNew != null) {
            String name = drinksToFoodNew.get(position).Name;
            drinkID = Drink.getID(name);
            isDifferent = false;
        }
        else {
            if(GlobalDrinkNames != null) {
                String name = GlobalDrinkNames.get(position);
                drinkID = Drink.getID(name);
            }
            else {
                String name = Drink.ListItems.get(position).Name;
                drinkID = Drink.getID(name);
            }
        }
        Intent intent = new Intent(this, ShowItemsInfo.class);
        intent.putExtra("isDrinks", isDrinks);
        intent.putExtra("itemNumber", drinkID);
        startActivity(intent);
    }


}
