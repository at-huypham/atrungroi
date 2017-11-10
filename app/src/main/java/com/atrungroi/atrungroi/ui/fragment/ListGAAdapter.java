package com.atrungroi.atrungroi.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.custom.TicketView;
import com.atrungroi.atrungroi.models.News;

import java.util.List;

/**
 * Created by huyphamna.
 */

public class ListGAAdapter extends RecyclerView.Adapter<ListGAAdapter.ViewHolder> {
    private List<News> mNews;
    private Context mContext;
    private OnClickPostListener mPostListener;

    public ListGAAdapter(List<News> news, Context context) {
        this.mNews = news;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ga, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(position);
        if (mNews.get(position).getImage() == 0) {
            holder.mImgIllustration.setVisibility(View.GONE);
        } else {
            holder.mImgIllustration.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTittleGA;
        private TextView mTvContentGA;
        private ImageView mImgIllustration;
        private LinearLayout mLlTicket;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTittleGA = itemView.findViewById(R.id.tvTittleGA);
            mTvContentGA = itemView.findViewById(R.id.tvContentGA);
            mImgIllustration = itemView.findViewById(R.id.imgIllustration);
            mLlTicket = itemView.findViewById(R.id.llTicket);
            mLlTicket.setOnClickListener(this);
        }

        private void setData(int position) {
            News news = mNews.get(position);
            mTvContentGA.setText(news.getContent());
            mTvTittleGA.setText(news.getTitle());
            mImgIllustration.setImageResource(news.getImage());
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llTicket:
                    Toast.makeText(mContext, "Pos: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    break;
            }

            if (mPostListener != null){
                mPostListener.clickPost(getAdapterPosition(), view);
            }
        }
    }

    public interface OnClickPostListener{
        void clickPost(int position, View view);
    }

    public void setPostListener(OnClickPostListener postListener) {
        this.mPostListener = postListener;
    }
}
