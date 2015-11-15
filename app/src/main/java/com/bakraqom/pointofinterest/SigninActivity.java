package com.bakraqom.pointofinterest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

public class SigninActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;
//    Button signin_btn;
    private static int RC_SIGN_IN = 9001;
    SignInButton signin_btn;
    public static String userId;
    public static String userName;
    public static String userEmail;
    SharedPreferences sharedPref;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            Log.d("data","handleSignInResult:" + result.isSuccess());
            if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();

                userId = acct.getId();
                userName = acct.getDisplayName();
                userEmail = acct.getEmail();

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.shared_pref_user_id), userId);
                editor.putString(getString(R.string.shared_pref_user_name), userName);
                editor.putString(getString(R.string.shared_pref_user_email), userEmail);
                editor.commit();

                checkUserLogin();


            } else {
                // Signed out, show unauthenticated UI.
                Toast.makeText(this, "Didn't Happen!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_signin);

        sharedPref = MainFragmentActivity.context.getPreferences(Context.MODE_PRIVATE);

//        removeSharedPreferences();

//        checkUserLogin();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signin_btn = (SignInButton)findViewById(R.id.sign_in_button);
        signin_btn.setSize(SignInButton.SIZE_WIDE);

        checkUserLogin();

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void checkUserLogin(){
        if (userId != getString(R.string.shared_pref_user_id)){
            Intent mainActivityIntent = new Intent(this, MainFragmentActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivityIntent);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    public void removeSharedPreferences(){
        sharedPref.edit().clear().commit();
    }
}
