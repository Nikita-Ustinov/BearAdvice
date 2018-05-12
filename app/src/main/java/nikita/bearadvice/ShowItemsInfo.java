package nikita.bearadvice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class ShowItemsInfo extends AppCompatActivity {


    boolean isDrinks;
    int position;
    String consumeWith;
    Item listItem;
    TextView twName;
    TextView twConsumeWith;
    TextView twDefenice;
    Button btnDefenice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_info);

        Intent intent = getIntent();
        isDrinks = intent.getBooleanExtra("isDrinks", true);
        position = intent.getIntExtra("itemNumber", -1);

        twName  = (TextView)findViewById(R.id.twNameItem);
        btnDefenice  = (Button) findViewById(R.id.btnDefenice);
        twConsumeWith = (TextView)findViewById(R.id.textView6);
        twDefenice = (TextView)findViewById(R.id.textView7);

        if(position == -1) {
            new Exception("Плохоу значение позиции элемента -1, ShowItemsInfo.java");        }

        if(isDrinks) {
//            listItem = Drink.ListItems.get(position);
            listItem = Drink.getItem(position);
        }
        else {
//            listItem = Food.ListItems.get(position);
            listItem = Food.getItem(position);
        }


        //удаление null из String
        for(int i=0; i<listItem.ConsumeWith.length; i++) {
            if(listItem.ConsumeWith[i].contains("null")) {
                listItem.ConsumeWith[i].replace("null", "");
            }

            consumeWith += listItem.ConsumeWith[i];
            consumeWith += "\r\n";
        }
        consumeWith = consumeWith.substring(4);

        twConsumeWith.setText(consumeWith);
        twName.setText(listItem.Name);
    }


    public void onInfoClick(View view) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra("isDrinks", isDrinks);
        intent.putExtra("itemNumber", position);
        startActivity(intent);
    }

    public void onDefeniceClick(View view) {
        twDefenice.setText(listItem.Definice);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale);
        Animation animInvizible = AnimationUtils.loadAnimation(this, R.anim.invizible);
        twDefenice.startAnimation(anim);
        btnDefenice.startAnimation(animInvizible);
        twDefenice.setVisibility(View.VISIBLE);
        btnDefenice.setVisibility(View.INVISIBLE);
    }


    public void onDefeniceTextClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_back);
        Animation animInvizible = AnimationUtils.loadAnimation(this, R.anim.invizible_back);
        twDefenice.startAnimation(anim);
        btnDefenice.startAnimation(animInvizible);
        twDefenice.setVisibility(View.INVISIBLE);
        btnDefenice.setVisibility(View.VISIBLE);
    }
}
