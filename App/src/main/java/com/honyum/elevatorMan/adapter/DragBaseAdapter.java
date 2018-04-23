package com.honyum.elevatorMan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.honyum.elevatorMan.view.DragGridView;

import java.util.ArrayList;
import java.util.List;

public abstract class DragBaseAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected Context context;
    protected DragGridView view;
    protected abstract int getLayoutId();
    protected abstract void initView(ViewHolder holder);
    protected abstract void setViewValue(ViewHolder holder,int position);


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public DragBaseAdapter(Context context,List<T>list,DragGridView view){
        this.context=context;
        this.list=list;
        this.view = view;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view= LayoutInflater.from(context).inflate(getLayoutId(),null);
            viewHolder=new ViewHolder(view);
            initView(viewHolder);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        setViewValue(viewHolder,i);
        return view;
    }


    public void moveItem(int start, int end){
        List<T>tmpList=new ArrayList<>();
        if(start<end){
            tmpList.clear();
            for(T s:list)tmpList.add(s);
            T endMirror=tmpList.get(end);

            tmpList.remove(end);
            tmpList.add(end,getItem(start));

            for(int i=start+1;i<=end;i++){
                tmpList.remove(i-1);
                if(i!=end){
                    tmpList.add(i-1,getItem(i));
                }else {
                    tmpList.add(i-1,endMirror);
                }

            }

        }else{
            tmpList.clear();
            for(T s:list)tmpList.add(s);
            T startMirror=tmpList.get(end);
            tmpList.remove(end);
            tmpList.add(end,getItem(start));

            for(int i=start-1;i>=end;i--){
                tmpList.remove(i+1);
                if(i!=start){
                    tmpList.add(i+1,getItem(i));
                }else {
                    tmpList.add(i+1,startMirror);
                }
            }

        }
        list.clear();
        list.addAll(tmpList);


        notifyDataSetChanged();
    }
}