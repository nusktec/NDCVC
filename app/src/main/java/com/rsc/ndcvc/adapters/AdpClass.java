package com.rsc.ndcvc.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rsc.ndcvc.R;
import com.rsc.ndcvc.databinding.ListClassesBinding;
import com.rsc.ndcvc.models.ModelListClass;

import java.util.List;

public class AdpClass extends RecyclerView.Adapter<AdpClass.VH> {
    List<ModelListClass> classes;
    onClassClick onClassClick;

    public AdpClass(List<ModelListClass> tmp_cls, onClassClick classClick) {
        this.classes = tmp_cls;
        this.onClassClick = classClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListClassesBinding bdx = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_classes, parent, false);
        return new VH(bdx);
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bdx.golive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add click listener
                onClassClick.goLive(classes.get(position));
            }
        });
        holder.bdx.markAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClassClick.goMark(classes.get(position));
            }
        });
        //dump data
        ModelListClass md = classes.get(position);
        holder.bdx.clsAlias.setText(md.getCalias());
        holder.bdx.clsCode.setText(md.getUsnumber());
        holder.bdx.clsPermit.setText(md.getCpermit());
    }


    static class VH extends RecyclerView.ViewHolder {
        private ListClassesBinding bdx;

        VH(ListClassesBinding ls) {
            super(ls.getRoot());
            this.bdx = ls;
        }
    }

    //onclick lister
    public interface onClassClick {
        void goLive(ModelListClass md);

        void goMark(ModelListClass md);
    }

}
