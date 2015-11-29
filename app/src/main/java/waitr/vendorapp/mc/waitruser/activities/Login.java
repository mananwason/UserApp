package waitr.vendorapp.mc.waitruser.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import waitr.vendorapp.mc.waitruser.Helpers.Constants;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.api.APIClient;
import waitr.vendorapp.mc.waitruser.api.protocol.UserResponse;
import waitr.vendorapp.mc.waitruser.dataObjects.User;

public class Login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 0;
    private static final int REQUEST_ACCOUNTS = 1;
    private static final String TAG = "Login activity";
    public static GoogleApiClient mGoogleApiClient;
    private static String[] PERMISSIONS_ACCOUNTS = {Manifest.permission.GET_ACCOUNTS};
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private View mLayout;
    private ConnectionResult mConnectionResult;

    public static final String Email = "emailKey";
//    SharedPreferences sharedpreferences;
    TextView wecomeTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLayout = findViewById(R.id.relative_layout);
        wecomeTextview = (TextView)findViewById(R.id.welcome_textView);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.EMAIL))
                .addScope(new Scope(Scopes.PROFILE))
                .addScope(new Scope(Scopes.PLUS_LOGIN))
                .build();

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        animation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isNetworkAvailable()) {
            mGoogleApiClient.connect();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this)
                    .setTitle(getString(R.string.no_internet)).setMessage(getString(R.string.internet_required))
                    .setPositiveButton(getString(R.string.open_wifi_settings), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));

                        }
                    })
                    .setNegativeButton("Use Mobile Data", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        }
                    })
                    .setNeutralButton("None available", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
//            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
//                    .setResultCallback(new ResultCallback<Status>() {
//                        @Override
//                        public void onResult(Status arg0) {
//                            Log.e("Access", "User access revoked!");
//                        }
//
//                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                    .setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status arg0) {
                            Log.e("Access", "User access revoked!");
                        }

                    });
        }

    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        String personName = "";
        String personEmail = "";

        final Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            requestAccountPermissions();
        } else {
            if (currentPerson != null) {

                personName = currentPerson.getDisplayName();
                personEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
            }
            APIClient apiClient = new APIClient();

            apiClient.getmApi().getUserId(personName, personEmail, new Callback<UserResponse>() {
                @Override
                public void success(UserResponse userResponse, Response response) {
                    for (User user : userResponse.user) {
                        SharedPreferences pref = getSharedPreferences(Constants.Prefs, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt(Constants.UserIdKey, user.getId());
                        editor.apply();
                    }

                    String personName = null, personPhoto = null;
                    if (currentPerson != null) {
                        personName = currentPerson.getDisplayName();
                        personPhoto = currentPerson.getImage().getUrl();
                    }
                    Intent mIntent = new Intent(Login.this, MainActivity.class);
                    mIntent.putExtra("name", personName);
                    mIntent.putExtra("photo", personPhoto);
                    Toast.makeText(Login.this, "Signed in as " + "\n" + personName, Toast.LENGTH_SHORT).show();
                    startActivity(mIntent);

                }

                @Override
                public void failure(RetrofitError error) {
//               Log.d("get user id fail", error.getCause().toString());
                }
            });
        }


//        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended", Toast.LENGTH_SHORT).show();
        mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            requestAccountPermissions();
        }
        if (isNetworkAvailable()) {
            if (!mGoogleApiClient.isConnecting()) {
                mSignInClicked = true;
                resolveSignInError();

            }
        } else
            Toast.makeText(this, "Network Not available", Toast.LENGTH_SHORT).show();
    }

    private void requestAccountPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {
            Log.i(TAG,
                    "Displaying contacts permission rationale to provide additional context.");

            Snackbar.make(mLayout, R.string.permission_account_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat
                                    .requestPermissions(Login.this, PERMISSIONS_ACCOUNTS,
                                            REQUEST_ACCOUNTS);
                        }
                    })
                    .show();
        } else {
            // Contact permissions have not been granted yet. Request them directly.
            ActivityCompat.requestPermissions(this, PERMISSIONS_ACCOUNTS, REQUEST_ACCOUNTS);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;
            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }


    private void animation() {
        wecomeTextview.setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        wecomeTextview.startAnimation(anim);
    }



}
