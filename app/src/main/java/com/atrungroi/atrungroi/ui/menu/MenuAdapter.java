package com.atrungroi.atrungroi.ui.menu;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atrungroi.atrungroi.R;
import com.atrungroi.atrungroi.models.MenuObject;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by huyphamna.
 */

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int HEADER_MENU = 0;
    private static final int ITEM_MENU = 1;

    private Context mContext;
    private List<MenuObject> mMenuObjects;
    private OnItemClickListener mOnItemClickListener;
    private OnMenuItemClickListener mOnMenuItemClickListener;

    public MenuAdapter(Context context, List<MenuObject> menuObjects, OnItemClickListener clickListener) {
        this.mContext = context;
        this.mMenuObjects = menuObjects;
        this.mOnItemClickListener = clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_MENU:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_menu, parent, false);
                return new HeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
                return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder header = (HeaderViewHolder) holder;
        }

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder item = (ItemViewHolder) holder;
            item.setData(position - 1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_MENU;
        }
        return ITEM_MENU;
    }

    @Override
    public int getItemCount() {
        return mMenuObjects.size() + 1;
    }

    /**
     * View holder for header menu
     */
    class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView mImgAvatar;
        private TextView mTvName;
        private TextView mTvMail;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            mImgAvatar = itemView.findViewById(R.id.imgAvatar);
            mTvName = itemView.findViewById(R.id.tvName);
            mTvMail = itemView.findViewById(R.id.tvMail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Header...Profile", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * View holder for item menu.
     */
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTvTittleMenu;
        private ImageView mImgIcon;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvTittleMenu = itemView.findViewById(R.id.tvTitleMenu);
            mImgIcon = itemView.findViewById(R.id.imgIconMenu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition() - 1);
            }
            if (mOnMenuItemClickListener != null) {
                mOnMenuItemClickListener.onMenuClick(view, getAdapterPosition() - 1);
            }
        }

        private void setData(int position) {
            MenuObject object = mMenuObjects.get(position);
            mImgIcon.setImageResource(object.getIcon());
            mTvTittleMenu.setText(object.getName());

            if (object.isChoose()) {
                mTvTittleMenu.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed700));
            } else {
                mTvTittleMenu.setTextColor(Color.BLACK);
            }
        }
    }

    /**
     * Interface item click listener.
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnMenuItemClickListener {
        void onMenuClick(View v, int pos);
    }

    void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }
}
