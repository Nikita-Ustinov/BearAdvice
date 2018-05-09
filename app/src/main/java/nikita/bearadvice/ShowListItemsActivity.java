package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShowListItemsActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

//    public static final String[] drinksNames = Drink.getDrinksNames();
//    public static final String[] foodsNames = Food.getFoodNames();

    LinkedList<Item> drinksToFoodNew;
    boolean isDifferent = false;

    ListView listView;
    List<Item> listRows;

    boolean isDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listRows = new ArrayList<Item>();
        Intent intent = getIntent();
        isDrinks = intent.getBooleanExtra("isDrinks", true);
        if(isDrinks) {
            listRows = Drink.ListItems;
        }
        else {
            listRows = Food.ListItems;
        }
        listView = (ListView) findViewById(R.id.listItemsView);
        MyBasicAdapter adapter = new MyBasicAdapter(this, listRows, isDrinks);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isDrinks) {
            int drinkID;
            if(isDifferent) {
                String name = drinksToFoodNew.get(position).Name;
                drinkID = Drink.getID(name);
                isDifferent = false;
            }
            else {
                String name = Drink.ListItems.get(position).Name;
                drinkID = Drink.getID(name);
            }
            Intent intent = new Intent(this, ShowItemsInfo.class);
            intent.putExtra("isDrinks", isDrinks);
            intent.putExtra("itemNumber", drinkID);
            startActivity(intent);
        }
        else {
            String foodName = Food.getFoodName(position);
            LinkedList<Item> drinksToFood = Drink.getListDrinksToFood(foodName);
            isDrinks = true;
            drinksToFoodNew = drinksToFood;
            MyBasicAdapter adapter = new MyBasicAdapter(this, drinksToFood, isDrinks);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            isDifferent = true;
        }

    }
}
