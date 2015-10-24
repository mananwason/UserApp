package waitr.vendorapp.mc.waitruser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;

import waitr.vendorapp.mc.waitruser.Fragments.MenuFragment;
import waitr.vendorapp.mc.waitruser.R;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private FrameLayout mainFrame;
    private String personName,personId,displayPic;
    private GoogleApiClient mGoogleApiClient1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setUpNavDrawer();
        mainFrame = (FrameLayout) findViewById(R.id.layout_main);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        buildGoogleApiClient();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new MenuFragment()).commit();
        Intent receivedIntent = getIntent();
        personName = receivedIntent.getStringExtra("name");
        personId = receivedIntent.getStringExtra("id");
        displayPic = receivedIntent.getStringExtra("photo");    //url for dp

    }
    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient1.connect();

    }
    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient1.disconnect();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (mGoogleApiClient1.isConnected()) {
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient1)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e("Access", "User access revoked!");
                        }

                    });
        }

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient1 = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .build();
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setupDrawerContent(navigationView, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void setUpToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            final android.support.v7.app.ActionBar ab = getSupportActionBar();
            assert ab != null;
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
            ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                    mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setDisplayHomeAsUpEnabled(true);
            mActionBarDrawerToggle.syncState();
        }
    }

    private void setupDrawerContent(NavigationView navigationView, final Menu menu) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        menuItem.setChecked(true);
                        int id = menuItem.getItemId();
                        menu.clear();
                        switch (id) {
                            case R.id.nav_menu:
                                fragmentManager.beginTransaction()
                                        .replace(R.id.content_frame, new MenuFragment()).commit();
                                getSupportActionBar().setTitle(R.string.menu_items);
                                break;

                            case R.id.nav_settings:
                            case R.id.nav_sign_out: logOut();
                                break;


                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
    public void logOut(){
        if (mGoogleApiClient1.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient1);
            mGoogleApiClient1.disconnect();
//            mGoogleApiClient.connect();
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(this, SignOut.class);
            startActivity(mIntent);

        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient1.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mGoogleApiClient1.connect();
    }
}
