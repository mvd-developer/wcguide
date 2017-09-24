package com.doschechko.matylionak.wcguide.citaty;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doschechko.matylionak.domain.entity.Quote;
import com.doschechko.matylionak.wcguide.databinding.ItemQuoteBinding;

import java.util.ArrayList;


public class MyAdaptor extends RecyclerView.Adapter<MyHolder> {
    private ArrayList<Quote> list = new ArrayList<>();
    private Activity activity;

    public void setList(ArrayList<Quote> arrayList) {
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemQuoteBinding binding = ItemQuoteBinding.inflate(inflater, parent, false);
        return new MyHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        Item_QuoteViewModel model = new Item_QuoteViewModel();
        model.setAuthor(list.get(position).getAuthor());
        model.setBody(list.get(position).getBody());
        holder.binding.setViewModel(model);
        //

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Log.e("FUCK", "setItemClickListener");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Отправлено из приложения \"Минский туалетный гид\":"
                        +"\n"
                        + list.get(position).getBody()
                        + "\n"
                        +list.get(position).getAuthor()
                        +"\n"
                        +"===> Все общественные туалеты на карте Минска! Качай! www.google.by");
                shareIntent.setType("text/plain");
                activity.startActivity(shareIntent);
                Toast.makeText(activity, "Поделиться...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
