package com.rabindra.studenthub;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    String userId;

    {
        assert user != null;
        userId = user.getUid();
    }

    String jsonResult;

    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + userId);

    RecyclerView recyclerView;
    ImageView sendBtn;
    TextView welcomeTextView;
    EditText query;
    List<Message> messageList;

    MessageAdapter messageAdapter;
    private boolean isFirstInteraction = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        View headerView = navigationView.getHeaderView(0);
        CircleImageView profilePic = headerView.findViewById(R.id.profile_pic);
        TextView name = headerView.findViewById(R.id.nameTV);
        TextView email = headerView.findViewById(R.id.emailTV);
        Button editProfile = headerView.findViewById(R.id.NHEditProfile);

        //Actual chat
        recyclerView=findViewById(R.id.recycler_view);
        sendBtn=findViewById(R.id.send_btn);
        welcomeTextView=findViewById(R.id.welcomeText);
        query=findViewById(R.id.query);
        messageList=new ArrayList<>();

        //Setup recycler View
        messageAdapter =new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);


        query.setOnFocusChangeListener((v, hasFocus) -> {
            if (isFirstInteraction) {
                // Add bot greeting to chat messages
                messageList.add(new Message("Hi, How may I help you?",Message.SENT_BY_BOT));
                messageAdapter.notifyItemInserted(messageList.size()-1);

                messageList.add(new Message("What do you want to know about?",Message.SENT_BY_BOT));
                messageAdapter.notifyItemInserted(messageList.size()-1);


                isFirstInteraction = false;
                welcomeTextView.setVisibility(View.GONE);
            }
        });


        sendBtn.setOnClickListener(v -> {
            String question=query.getText().toString().trim().toLowerCase();

            if(question.contains("#clear"))
            {
                messageList.clear();
                messageAdapter.notifyDataSetChanged();
                query.setText("");

            }
            else if(!question.isEmpty())
            {
                addToChat(question,Message.SENT_BY_ME);
                welcomeTextView.setVisibility(View.GONE);
                query.setText("");
                String finalResult=JsonParser.parseData(question);
                addResponse(finalResult);
                if(finalResult=="https://mcastudenthub.blogspot.com/p/time-table-for-mca-provided-by-student.html")
                {
                    messageList.add(new Message("URL to Time Table has been copied to clipboard",Message.SENT_BY_BOT));
                    messageAdapter.notifyItemInserted(messageList.size()-1);
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("URL", finalResult);
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(this, "URL copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                }

                if(finalResult=="https://drive.google.com/file/d/1wBDEbq9UttgVfQP0CJieAJxOL73vW68_/view?usp=sharing")
                {
                    messageList.add(new Message("URL to Syllabus of MCA department has been copied to clipboard",Message.SENT_BY_BOT));
                    messageAdapter.notifyItemInserted(messageList.size()-1);
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("URL", finalResult);
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(this, "URL copied to clipboard", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
            {
                query.setHint("Type your query");
            }
        });


        //Get Timezone
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String loginTime = dateFormat.format(new Date());


        //Get Device info
        String deviceName = Build.MANUFACTURER + " " + Build.MODEL;
        String kernelVersion = System.getProperty("os.version");
        String baseBandVersion = Build.getRadioVersion();


        //Get Wi-Fi IP
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        @SuppressLint("DefaultLocale") String wifiIP = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));


        /*===========================================================================================*/
        //Check the login Provider by which user got logged in
        if (user != null) {
            //Default realtime Database add
            userRef.child("Device details");
            userRef.child("Device details").child("Device Name").setValue(deviceName);
            userRef.child("Device details").child("Kernel Version").setValue(kernelVersion);
            userRef.child("Device details").child("Base-band Version").setValue(baseBandVersion);
            userRef.child("Device details").child("Wifi Ip").setValue(wifiIP);

            userRef.child("userId").setValue(userId);
            userRef.child("Login Time").setValue(loginTime);

            // Check the provider ID to determine the sign-in method
            // User signed in with Google
            email.setText(user.getEmail());
            name.setText(user.getDisplayName());
            Uri picUri = user.getPhotoUrl();
            String picUrl = String.valueOf(picUri);


            userRef.child("phone").setValue(user.getPhoneNumber());
            userRef.child("name").setValue(user.getDisplayName());
            userRef.child("picUrl").setValue(picUrl);

            if(picUrl.equals("null"))
            {
                userRef.child("email").setValue(user.getEmail());
            }
            else
            {
                Picasso.get().load(picUrl).into(profilePic);
            }



            if(!Objects.equals(user.getEmail(), ""))
            {
                email.setText(user.getEmail());
                userRef.child("email").setValue(user.getEmail());
            }
            else
            {
                email.setText("");
                userRef.child("email").setValue("");
            }
        }

        /*===============================================================================================*/
        //Edit Profile button Onclick Listener
        editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DialogProfileInfo.class);
            intent.putExtra("UserName",String.valueOf(user.getDisplayName()));
            startActivity(intent);
        });
        /*===============================================================================================*/


        /*===============================================================================================*/
        //For the navigation bar button
        setSupportActionBar(toolbar);
        //To toggle and sync the state of the navigation button
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        /*===============================================================================================*/


        /*===============================================================================================*/
        //Navigation items Click Listener

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.terms_and_condition) {
                //To open the terms and condition activity
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://mcastudenthub.blogspot.com/p/student-hub-terms-condition.html"));
                startActivity(intent);
            } else if (id == R.id.about_us) {
                //To open the about us activity
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://mcastudenthub.blogspot.com/p/about-us.html"));
                startActivity(intent);

            } else if (id == R.id.support) {
                //To open the support activity
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.support, null);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.create();
                dialog.show();

            } else if (id == R.id.logout) {
                //To logout the email user from the account
                mAuth.signOut();

                //Logout the Google SignIn user from the account
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .build();
                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
                googleSignInClient.signOut();

                Intent intent = new Intent(MainActivity.this, LoginOptions.class);
                startActivity(intent);


            } else {
                //For closing the drawer and opening the Home page
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        });
        /*=============================================================================================*/

    }
    /*===================================================================================*/
    @SuppressLint("NotifyDataSetChanged")
    void addToChat(String message,String sentBy)
    {
        runOnUiThread(() -> {
            messageList.add(new Message(message,sentBy));
            messageAdapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
        });
    }

    void addResponse(String response)
    {
        addToChat(response,Message.SENT_BY_BOT);
    }

    /*=============================================================================================*/

    /*====================================================================================================*/
    //On Back pressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Exit")
                .setMessage("Do you want to exit?")
                .setIcon(R.drawable.applogo)
                .setCancelable(false)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> MainActivity.super.onBackPressed())
                .create().show();
    }
    /*================================================================================================*/


    private final Handler mHandler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Refresh the main activity
            checkUserIdRevocation();
            mHandler.postDelayed(this, 3000);
        }
    };

    private void checkUserIdRevocation() {
        /*===============================================================================================*/

        //For user Revocation
        FirebaseDatabase.getInstance().getReference("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // User has been deleted by Firebase admin
                    mAuth.signOut();
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(getString(R.string.default_web_client_id))
                            .build();
                    GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(MainActivity.this, gso);
                    googleSignInClient.signOut();

                    Intent intent = new Intent(MainActivity.this, LoginOptions.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
        /*===============================================================================================*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop the refresh loop
        mHandler.removeCallbacksAndMessages(null);
    }
}