package com.joyjet.digitalspace.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.joyjet.digitalspace.controller.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class JoyjetDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "bd_joyjet";
    private static final int VERSAO = 1;
    private static final String TABELA_FAV = "fav";

    public JoyjetDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_fav = "CREATE TABLE " + TABELA_FAV + "(" +
                "category TEXT, " +
                "title TEXT, " +
                "description TEXT," +
                "descriptionMainActivity TEXT, " +
                "url_banner TEXT, " +
                "PRIMARY KEY (category, title)" +
                ");";
        try {
            db.execSQL(sql_fav);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql_fav = "DROP TABLE IF EXISTS " + TABELA_FAV;
        db.execSQL(sql_fav);
        onCreate(db);
    }

    public void insertFav(Article article){

        ContentValues cv = new ContentValues();
        cv.put("category", article.getCategory());
        cv.put("title", article.getTitle());
        cv.put("description", article.getDescription());
        cv.put("descriptionMainActivity", article.getDescriptionMainActivity());
        cv.put("url_banner", article.getUrlBanner());

        getWritableDatabase().insert(TABELA_FAV, null, cv);
    }

    public List<Article> getFavs() {

        String sql = "SELECT * FROM " + TABELA_FAV;
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        List<Article> articles= new ArrayList<>();
        Article article;

        while(cursor.moveToNext()){

            article = new Article();

            article.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            article.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            article.setDescriptionMainActivity(cursor.getString(cursor.getColumnIndex("descriptionMainActivity")));
            article.setUrlBanner(cursor.getString(cursor.getColumnIndex("url_banner")));
            articles.add(article);
        }
        cursor.close();

        return articles;
    }

    public boolean isFav(String category, String title) {
        String sql = "SELECT * FROM " + TABELA_FAV + " WHERE category = ? and title = ?;";
        String[] args = {category, title};
        final Cursor cursor = getReadableDatabase().rawQuery(sql, args);

        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public long delete(String category, String title) {

        String args[] = {category, title};
        return getWritableDatabase().delete(TABELA_FAV, "category=? and title=?", args);
    }
}
