package com.doschechko.matylionak.wcguide.citaty;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.doschechko.matylionak.domain.entity.Author;
import com.doschechko.matylionak.wcguide.databinding.ItemAuthorQuoteBinding;
import java.util.ArrayList;


public class MyGridAdaptor extends RecyclerView.Adapter<MyGridHolder> {
    private ArrayList<Author> list = new ArrayList<>();
    private FragmentManager manager;
    private Activity activity;


    public void setList(ArrayList<Author> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyGridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemAuthorQuoteBinding binding = ItemAuthorQuoteBinding.inflate(inflater, parent, false);
        return new MyGridHolder(binding.getRoot());


    }

    @Override
    public void onBindViewHolder(MyGridHolder holder, int position) {
        Item_AuthorViewModel model = new Item_AuthorViewModel();
        model.setManager(manager);
        model.setActivity(activity);
        model.setName(list.get(position).getName());
        model.setUrl(list.get(position).getImage());
        holder.binding.setViewModel(model);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
