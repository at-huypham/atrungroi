package com.atrungroi.atrungroi.ui.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by huyphamna.
 */

public class ListGAAdapter extends RecyclerView.Adapter<ListGAAdapter.ViewHolder> {
    private List<Event> mNews;
    private Context mContext;
    private OnClickPostListener mPostListener;

    public ListGAAdapter(List<Event> news, Context context) {
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
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTittleGA;
        private TextView mTvContentGA;
        private ImageView mImgIllustration;
        private TextView mTvTimePostEnd;
        private TextView mTvNameUserPost;
        private LinearLayout mLlTicket;
        private Button mBtnJoinNow;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvTittleGA = itemView.findViewById(R.id.tvTittleGA);
            mTvContentGA = itemView.findViewById(R.id.tvContentGA);
            mImgIllustration = itemView.findViewById(R.id.imgIllustration);
            mLlTicket = itemView.findViewById(R.id.llTicket);
            mTvTimePostEnd = itemView.findViewById(R.id.tvTimePostEnd);
            mTvNameUserPost = itemView.findViewById(R.id.tvNameUserPost);
            mBtnJoinNow = itemView.findViewById(R.id.btnJoinNow);
            mLlTicket.setOnClickListener(this);
            mBtnJoinNow.setOnClickListener(this);
        }

        private void setData(int position) {
            Event event = mNews.get(position);
            mTvContentGA.setText(event.getContent());
            mTvTittleGA.setText(event.getTitle());
            mTvTimePostEnd.setText(event.getDateTimeEnd());
            mTvNameUserPost.setText(event.getNameUser());
            loadImageFromUrl(event.getImagesEvent(), mImgIllustration);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.llTicket:
//                    Toast.makeText(mContext, "Pos: " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btnJoinNow:
                    Toast.makeText(mContext, "Bạn đã tham gia thành công", Toast.LENGTH_SHORT).show();
                    mBtnJoinNow.setText("Đã tham gia");
                    break;
            }

            if (mPostListener != null){
                mPostListener.clickPost(getAdapterPosition(), view);
            }
        }
    }

    private void loadImageFromUrl(String url, ImageView imageView) {
        Picasso.with(mContext.getApplicationContext())
                .load(url)
                .placeholder(R.drawable.ic_logo_red)
                .error(R.drawable.ic_logo_red)
                .resize(500, 500)
                .into(imageView);
    }


    public interface OnClickPostListener{
        void clickPost(int position, View view);
    }

    public void setPostListener(OnClickPostListener postListener) {
        this.mPostListener = postListener;
    }


}
