package waitr.vendorapp.mc.waitruser.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import waitr.vendorapp.mc.waitruser.Helpers.Constants;
import waitr.vendorapp.mc.waitruser.R;

public class GuideActivity extends AppCompatActivity {

    ImageView background;
    SharedPreferences mSharedPreferences;
    Button next;
    Bitmap largeIcon;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        count = 1;
        background = (ImageView) findViewById(R.id.backgroundImage);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (mSharedPreferences.getBoolean(Constants.FirstTime, true)) {
            mSharedPreferences.edit().putBoolean(Constants.FirstTime, false).apply();
            Log.d("first time", mSharedPreferences.getBoolean(Constants.FirstTime, true)+"");
            next = (Button) findViewById(R.id.buttonNext);
            background = (ImageView) findViewById(R.id.backgroundImage);
            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img1);
            background.setImageBitmap(largeIcon);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;
                    switch (count) {
                        case 2:
                            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img2);
                            background.setImageBitmap(largeIcon);
                            break;
                        case 3:
                            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img3);
                            background.setImageBitmap(largeIcon);
                            break;
                        case 4:
                            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img4);
                            background.setImageBitmap(largeIcon);
                            break;
                        case 5:
                            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img5);
                            background.setImageBitmap(largeIcon);
                            break;
                        case 6:
                            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img6);
                            background.setImageBitmap(largeIcon);
                            break;
                        case 7:
                            largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img7);
                            background.setImageBitmap(largeIcon);
                            next.setText("Start with the app");
                            break;
                        case 8:
                            Intent mIntent = new Intent(GuideActivity.this, Login.class);
                            startActivity(mIntent);
                            break;
                    }
                }
            });


        }
        else {
            Intent mIntent = new Intent(GuideActivity.this, Login.class);
            startActivity(mIntent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
