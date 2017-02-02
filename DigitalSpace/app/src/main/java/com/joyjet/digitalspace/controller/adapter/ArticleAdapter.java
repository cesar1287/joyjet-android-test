package com.joyjet.digitalspace.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.joyjet.digitalspace.R;
import com.joyjet.digitalspace.controller.domain.Article;
import com.joyjet.digitalspace.controller.interfaces.RecyclerViewOnClickListenerHack;

import java.util.List;



public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder>{

    private List<Article> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context c;

    public ArticleAdapter(Context c, List<Article> l){
        this.c = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_article, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        Glide.with(c)
                .load(mList.get((position)).getUrlBanner())
                .into(myViewHolder.bannerArticle);
        myViewHolder.titleArticle.setText(mList.get(position).getTitle());
        myViewHolder.descriptionArticle.setText(mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener /*View.OnCreateContextMenuListener*/{
        ImageView bannerArticle;
        TextView titleArticle;
        TextView descriptionArticle;

        MyViewHolder(View itemView) {
            super(itemView);
            bannerArticle = (ImageView) itemView.findViewById(R.id.article_banner);
            titleArticle = (TextView) itemView.findViewById(R.id.article_title);
            descriptionArticle = (TextView) itemView.findViewById(R.id.article_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }
    }
}
