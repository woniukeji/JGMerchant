package com.woniukeji.demo;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Se7enGM on 2016/8/20.
 */
public abstract class TagAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangeListener;



    public void setmOnDataChangeListener(OnDataChangedListener mOnDataChangeListener) {
        this.mOnDataChangeListener = mOnDataChangeListener;
    }


    public TagAdapter(List<T> datas) {
        mTagDatas =  datas;
    }

    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<>(Arrays.asList(datas));
    }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public void notifyDataChanged() {
        mOnDataChangeListener.onChange();
    }

    public abstract View getView(FlowLayout parent, int postion, T t);

    HashSet<Integer> getPreCheckedList()
    {
        return mCheckedPosList;
    }

    public boolean setSelected(int position, T t)
    {
        return false;
    }

    public void setSelectedList(int... pos) {
        Set<Integer> set = new HashSet<>();
        for (int position :
                pos) {
            set.add(position);

        }
    }
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();
    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
            notifyDataChanged();
        }
    }


}
