package nikita.bearadvice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class StoryActivity extends AppCompatActivity {

    TextView twStory;
    Item listItem;
    ImageView iwPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        twStory = (TextView)findViewById(R.id.textView6);
        iwPicture = (ImageView) findViewById(R.id.imageView2);


        Intent intent = getIntent();
        boolean isDrinks = intent.getBooleanExtra("isDrinks", true);
        int position = intent.getIntExtra("itemNumber", -1);

        if (position == -1) {
            new Exception("Плохое значение позиции элемента -1, StoryActivity.java");
        }
        if(isDrinks) {
            listItem = Drink.ListItems.get(position);
        }
        else {
            listItem = Food.ListItems.get(position);
        }
        String story;
        Random rand = new Random();
        int storyNumber = rand.nextInt(listItem.Stories.length - 0 + 1);
        story = listItem.Stories[storyNumber];
        String way = "R.mipmap."+listItem.Picture;
        String packageName = getPackageName();
        int resID = -1;
        try {
            resID = R.drawable.class.getField(listItem.Picture).getInt(getResources());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        int newID = R.drawable.arak;
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),  resID);



//        iwPicture.setImageDrawable
//                (
//                        getResources().getDrawable(getResourceID(str, "drawable",
//                                getApplicationContext()))
//                );


        iwPicture.setImageBitmap(icon);
        twStory.setText(story);
    }


    protected final static int getResourceID
            (final String resName, final String resType, final Context ctx)
    {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }
}
