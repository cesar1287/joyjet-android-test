package com.joyjet.digitalspace.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joyjet.digitalspace.R;
import com.joyjet.digitalspace.controller.domain.Article;
import com.joyjet.digitalspace.model.JoyjetDAO;

public class ArticleActivity extends AppCompatActivity {

    Article article = new Article();

    JoyjetDAO dao = new JoyjetDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        article = (Article) getIntent().getSerializableExtra("article");

        setupUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_fav:
                if(dao.isFav(article.getCategory(), article.getTitle())){
                    dao.delete(article.getCategory(), article.getTitle());
                    item.setIcon(R.drawable.ic_heart_outline_white_48dp);
                    Toast.makeText(this, R.string.message_article_removed_favorities, Toast.LENGTH_SHORT).show();
                }else {
                    dao.insertFav(article);
                    dao.close();
                    item.setIcon(R.drawable.ic_heart_white_48dp);
                    Toast.makeText(this, R.string.message_article_added_favorities, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fav, menu);

        if(article!=null) {
            if (dao.isFav(article.getCategory(), article.getTitle())) {
                menu.getItem(0).setIcon(R.drawable.ic_heart_white_48dp);
            } else {
                menu.getItem(0).setIcon(R.drawable.ic_heart_outline_white_48dp);
            }
        }

        return true;
    }

    private void setupUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView_toolbar);
        Glide.with(this)
                .load(article.getUrlBanner())
                .into(imageView);

        TextView tv_title = (TextView) findViewById(R.id.article_details_title);
        tv_title.setText(article.getTitle());
        TextView tv_category = (TextView) findViewById(R.id.article_details_category);
        tv_category.setText(article.getCategory());
        TextView tv_text = (TextView) findViewById(R.id.article_details_text);
        tv_text.setText(article.getDescription());
    }
}
