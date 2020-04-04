package com.cpuschedulingsimulator.computerarchitecture;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_cpu_scheduling:
                    //setAdds
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }


                    cpuSchedulingFragmentTransaction();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set interestitil adds
      /*  MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());  */
        //banner add
      /*

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        MobileAds.initialize(this, "ca-app-pub-5496440556025420~2803787157");


        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        cpuSchedulingFragmentTransaction();

        //add
        prepareAdd();
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }
                        prepareAdd();
                    }
                });
            }
        },5,20, TimeUnit.SECONDS);//display every 20 secs.


        //banner add
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void prepareAdd(){
        //prepare the add
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5496440556025420/7449609179");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void cpuSchedulingFragmentTransaction() {
        CPUSchedulingFragment cpuFragment = new CPUSchedulingFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, cpuFragment, getString(R.string.cpuFragment));
        transaction.commit();
    }




    //Option Menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.infoId){
            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
            this.startActivity(intent);
            return true;

        }
        if(item.getItemId()==R.id.feedbackId){
        Toast.makeText(MainActivity.this,"Feedback is selected!",Toast.LENGTH_SHORT).show();
        return true;
        }
        if(item.getItemId()==R.id.aboutUsId){
        Toast.makeText(MainActivity.this,"About Us is selected!",Toast.LENGTH_SHORT).show();
        return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
