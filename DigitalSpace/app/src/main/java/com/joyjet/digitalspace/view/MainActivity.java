package com.joyjet.digitalspace.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.joyjet.digitalspace.R;
import com.joyjet.digitalspace.controller.domain.Article;
import com.joyjet.digitalspace.controller.fragment.ArticleFragment;
import com.joyjet.digitalspace.controller.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    ArticleFragment frag;

    RequestQueue requestQueue;

    public static final String TAG = "ResponseJSON";

    List<Article> articles = new ArrayList<>();

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        dialog = ProgressDialog.show(this,"", "Loading data...", true, false);

        doGet();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));

        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void doGet() {

        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, Util.URL, null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject row;
                            JSONArray item;

                            try {
                                row = response.getJSONObject(i);
                                item = row.getJSONArray("items");
                                for(int j=0;j<=item.length(); j++){
                                    Article article = new Article();
                                    article.setCategory(row.getString("category"));
                                    Log.i(TAG, row.getString("category"));
                                    JSONObject row_item = item.getJSONObject(j);
                                    Log.i(TAG, row_item.getString("title"));
                                    article.setTitle(row_item.getString("title"));
                                    Log.i(TAG, row_item.getString("description"));
                                    String description = row_item.getString("description");
                                    article.setDescription(row_item.getString("description"));
                                    String descriptionRv[] = description.split("\\.");
                                    article.setDescriptionMainActivity(descriptionRv[0]);

                                    JSONArray galery = row_item.getJSONArray("galery");
                                    article.setUrlBanner(galery.get(0).toString());

                                    Log.i(TAG, article.getTitle()+article.getUrlBanner());
                                    articles.add(article);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        dialog.dismiss();

                        frag = (ArticleFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
                        Log.i(TAG, "fragment");
                        if(frag == null) {
                            frag = new ArticleFragment();
                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                            ft.replace(R.id.articles_fragment_container, frag, "mainFrag");
                            ft.commit();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        requestQueue.add(getRequest);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.nav_fav) {
            startActivity(new Intent(this, FavActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public List<Article> getArticlesList() {

        return articles;
    }
}
