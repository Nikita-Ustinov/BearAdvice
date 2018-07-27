package nikita.bearadvice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import nikita.bearadvice.Logic.Item;

public class SettingsActivity extends AppCompatActivity {

    Button btnSound;
    Button btnAboutUs;
    TextView twAboutUs;
    LinearLayout laAboutUs;

    private AdView mAdView;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        laAboutUs = (LinearLayout) findViewById(R.id.laAboutUs);
        twAboutUs = (TextView)findViewById(R.id.twAboutUs);
        twAboutUs = (TextView)findViewById(R.id.twAboutUs);
        btnAboutUs = (Button)findViewById(R.id.btnAboutUs);
        btnSound = (Button)findViewById(R.id.btnSound);

        if(!MainActivity.WithMusic) {
            btnSound.setText(R.string.turnOnMusic);
            btnSound.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

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

    public void onBtnSoundClick(View view) {
        tougleMusic();
    }

    void tougleMusic() {
        if(MainActivity.WithMusic == true) {
            MainActivity.WithMusic = false;
            btnSound.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnSound.setText(R.string.turnOnMusic);
            btnSound.setTextColor(Color.BLACK);
            MainActivity.mPlayer.stop();
        }
        else {
            MainActivity.WithMusic = true;
            btnSound.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            btnSound.setText(R.string.turnOffSound);
            btnSound.setTextColor(Color.WHITE);
            MainActivity.mPlayer = MediaPlayer.create(this, R.raw.mix1);
            MainActivity.mPlayer.start();

        }

        MainActivity.serializace(this);
    }

    public void onAboutUsClock(View view) throws UnsupportedEncodingException {
        String textAboutUs = createTextAboutUs(this);
        twAboutUs.setText(textAboutUs);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.invizible_back);
        laAboutUs.startAnimation(anim);
        laAboutUs.setHovered(false);
        laAboutUs.setVisibility(View.VISIBLE);
    }

    public void onAboutUsTextClick(View view) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.invizible);
        laAboutUs.startAnimation(anim);
        laAboutUs.setVisibility(View.INVISIBLE);
    }

    String createTextAboutUs(Activity activity) throws UnsupportedEncodingException {
        String input = null;
        Resources r = activity.getResources();
        InputStream is = r.openRawResource(R.raw.about_us);
        BufferedReader imBR = new BufferedReader(new InputStreamReader(is,"UTF-16LE"));
        try {
            input = Item.convertStreamToString(imBR);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input = input.substring(4,input.length()-5);
        return input;
    }


}
