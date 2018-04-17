package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

public class ShowItemsInfo extends AppCompatActivity {


    boolean isDrinks;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_info);

        Intent intent = getIntent();
        isDrinks = intent.getBooleanExtra("isDrinks", true);
        position = intent.getIntExtra("itemNumber", -1);

        if(position == -1) {
            new Exception("Position == -1 ");
        }
    }


    public void onInfoClick(View view) {
        Intent intent = new Intent(this, ShowItemsInfo.class);
        intent.putExtra("isDrinks", isDrinks);
        intent.putExtra("itemNumber", position);
        startActivity(intent);
    }
}
