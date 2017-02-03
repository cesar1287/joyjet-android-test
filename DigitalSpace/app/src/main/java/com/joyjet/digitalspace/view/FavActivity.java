package com.joyjet.digitalspace.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.joyjet.digitalspace.R;
import com.joyjet.digitalspace.controller.domain.Article;
import com.joyjet.digitalspace.controller.fragment.ArticleFragment;
import com.joyjet.digitalspace.model.JoyjetDAO;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {

    ArticleFragment frag;

    List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        setupUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshList();
    }

    public List<Article> getArticlesList() {

        loadlist();

        return articles;
    }

    private void loadlist() {

        JoyjetDAO dao = new JoyjetDAO(getApplicationContext());

        articles = dao.getFavs();

        dao.close();
    }

    public void refreshList(){
        frag.mList.clear();
        frag.mList.addAll(getArticlesList());
        frag.adapter.notifyDataSetChanged();
    }


    public void setupUI(){

        frag = (ArticleFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new ArticleFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fav_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }
}
