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

import static nikita.bearadvice.Food.getFoodByGroupPosition;

public class foodGroupsActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    LinkedList<Item> drinksToFoodNew;
    boolean isDifferent = false;

    ListView listView;
    List<String> listRows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_groups);

        listRows = Food.GroupList;
        listView = (ListView) findViewById(R.id.listItemsView);

        MySecondAdapter adapter = new MySecondAdapter(this, listRows);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        LinkedList<Item> food = getFoodByGroupPosition(position);
//        MyBasicAdapter adapter = new MyBasicAdapter(this, food, false);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);

        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", false);
        intent.putExtra("position", position);
        startActivity(intent);
    }


}

