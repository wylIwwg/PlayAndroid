package com.sjjd.wyl.playandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sjjd.wyl.playandroid.R;
import com.sjjd.wyl.playandroid.view.activities.ArticlesActivity;
import com.sjjd.wyl.playandroid.bean.CategoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyl on 2018/5/2.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {
    Context mContext;
    List<CategoryBean.Data> groupList;


    public CategoryAdapter(Context context, List<CategoryBean.Data> groupList) {
        mContext = context;
        this.groupList = groupList;
    }

    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupList == null || groupList.get(groupPosition) == null ? 0 : groupList.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupList.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        CategoryBean.Data group = (CategoryBean.Data) getGroup(groupPosition);
        if (group != null) {
            holder.mTvCategory.setText(group.getName());
            holder.mImgExpanded.setImageResource(isExpanded ? R.drawable.up_arrow : R.drawable.down_arrow);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_category_child, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CategoryBean.Children child = (CategoryBean.Children) getChild(groupPosition, childPosition);
        if (child != null) {
            holder.mTvCategoryChild.setText(child.getName());

            holder.mTvCategoryChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent mIntent = new Intent(mContext, ArticlesActivity.class);
                    mIntent.putExtra("cid", child.getId());
                    mIntent.putExtra("title", child.getName());
                    mContext.startActivity(mIntent);

                }
            });

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void refreshData(List<CategoryBean.Data> cate) {
        if (groupList != null) {
            groupList.clear();
            groupList.addAll(cate);
            notifyDataSetChanged();
        }
    }

    static class GroupViewHolder {
        @BindView(R.id.tvCategory)
        TextView mTvCategory;
        @BindView(R.id.imgExpanded)
        ImageView mImgExpanded;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder {
        @BindView(R.id.tvCategoryChild)
        TextView mTvCategoryChild;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
