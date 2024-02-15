package com.legendarysoftwares.livetv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.health.connect.datatypes.units.Length;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ChannelAdapter channelAdapter;
    DatabaseReference mbase;
    ImageView filter,save;
    TextView homeText;
     SearchView searchView;
    MenuBuilder menuBuilder;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filter = findViewById(R.id.filter);
        save = findViewById(R.id.save);
        homeText = findViewById(R.id.home_text);
        searchView=findViewById(R.id.search);
        searchView.clearFocus();



        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        try {
            mbase = FirebaseDatabase.getInstance().getReference();
            FirebaseRecyclerOptions<channel_model> options = new FirebaseRecyclerOptions.Builder<channel_model>()
                    .setQuery(mbase.orderByChild("name"), channel_model.class).build();

            channelAdapter = new ChannelAdapter(options);
            recyclerView.setAdapter(null);
            recyclerView.setItemAnimator(null);
            recyclerView.setAdapter(channelAdapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, filter);

                // Inflating popup menu from popup_menu.xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        if (id==R.id.All_Channels){
                            displayAllChannels();
                            return true;
                        } else if (id==R.id.Documentary){
                            filter("Documentary");
                            return true;
                        } else if (id==R.id.Entertainment) {
                            filter("Entertainment");
                            return true;
                        }else if (id==R.id.Kids) {
                            filter("Kids");
                            return true;
                        }else if (id==R.id.Movies) {
                            filter("");
                            return true;
                        }else if (id==R.id.Music) {
                            filter("Music");
                            return true;
                        }else if (id==R.id.News) {
                            filter("News");
                            return true;
                        }else if (id==R.id.Sports) {
                            filter("Sports");
                            return true;
                        } else if (id==R.id.Religious) {
                            filter("Religious");
                            return true;
                        }else if (id==R.id.Settings) {
                            Intent intent=new Intent(MainActivity.this,mySettings.class);
                            startActivity(intent);
                            return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, saved_channels.class);
                startActivity(intent);
            }
        });
        homeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAllChannels();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        channelAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        channelAdapter.stopListening();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        channelAdapter.startListening();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        channelAdapter.stopListening();
    }
    @Override
    protected void onResume() {
        super.onResume();
        channelAdapter.startListening();
    }
    @Override
    protected void onPause() {
        super.onPause();
        channelAdapter.stopListening();
    }

    private void processSearch(String s) {
        FirebaseRecyclerOptions<channel_model> options = new FirebaseRecyclerOptions.Builder<channel_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().orderByChild("name")
                        .startAt(s.toUpperCase()+s).endAt(s+"\uf8ff"), channel_model.class).build();
        channelAdapter = new ChannelAdapter(options);
        channelAdapter.startListening();
        recyclerView.setAdapter(channelAdapter);
    }
    private void filter(String category) {
        FirebaseRecyclerOptions<channel_model> options1 = new FirebaseRecyclerOptions.Builder<channel_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().orderByChild("category")
                        .equalTo(category), channel_model.class).build();

        channelAdapter = new ChannelAdapter(options1);
        channelAdapter.startListening();
        recyclerView.setAdapter(channelAdapter);
    }
    private void displayAllChannels() {
        FirebaseRecyclerOptions<channel_model> options1 = new FirebaseRecyclerOptions.Builder<channel_model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().orderByChild("name"), channel_model.class).build();
        channelAdapter = new ChannelAdapter(options1);
        channelAdapter.startListening();
        recyclerView.setAdapter(channelAdapter);
    }

}