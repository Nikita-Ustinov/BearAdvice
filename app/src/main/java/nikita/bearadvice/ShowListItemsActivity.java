package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ShowListItemsActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    public static final String[] drinksNames = Item.getDrinksNames();
    public static final String[] foodsNames = Item.getDrinksNames();

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
            listRows = Item.Drinks;
        }
        else {
            listRows = Item.Food;
        }
        listView = (ListView) findViewById(R.id.listItemsView);
        MyBasicAdapter adapter = new MyBasicAdapter(this, listRows);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra("isDrinks", isDrinks);
        intent.putExtra("itemNumber", position);
        startActivity(intent);
    }
}
