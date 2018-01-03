package com.blockout22.jump;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {

    private String TEST_ID = "ca-app-pub-3940256099942544/6300978111";
    private String APP_AD_ID = "ca-app-pub-8272279833650119/9573898074";



    private LinearLayout layout;

	@Override
	protected void onCreate (final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(APP_AD_ID);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        adView.setAdListener(new AdListener(){
            public void onAdClosed() {
            }

            public void onAdFailedToLoad(int var1) {
                Toast.makeText(layout.getContext() , "Failed Ad: " + var1, Toast.LENGTH_SHORT).show();
            }

            public void onAdLeftApplication() {
            }

            public void onAdOpened() {
            }

            public void onAdLoaded() {
                Toast.makeText(layout.getContext(), "Ad Loaded", Toast.LENGTH_SHORT).show();
            }

            public void onAdClicked() {
            }

            public void onAdImpression() {
            }
        });

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View gameView = initializeForView(new Jump(), config);
//		initialize(new Jump(), config);
//		initialize(new Test3D(), config);
        layout.addView(adView);
        layout.addView(gameView);

        setContentView(layout);
	}
}
