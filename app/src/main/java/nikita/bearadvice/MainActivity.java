package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Item.deserialization(this);
        if(Drink.ListItems.isEmpty()){
            try {
                Item.createDB(this); //исправить создание датабазы
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBtnFoodClick(View view) {
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", false);
        startActivity(intent);
    }

    public void onBtnDrinksClick(View view) {
        Intent intent = new Intent(this, ShowListItemsActivity.class);
        intent.putExtra("isDrinks", true);
        startActivity(intent);
    }
}


