package com.doschechko.matylionak.wcguide.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.databinding.ActivityAboutBinding;


public class AboutFragmentViewModel implements BaseFragmentViewModel {

    private ActivityAboutBinding binding;
    private Button button;
    private Activity activity;

    @Override
    public void init() {

        button = binding.shareButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mvd.developer@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Новый туалет");
                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivity(intent);
                }


//                Intent shareIntent = ShareCompat.IntentBuilder.from(activity.getParent())
//                        .setType("text/plain")
//                        .setText("Чтобы искать общественные туалеты на карте Минска, " +
//                                "я использую бесплатное приложение \"Минский туалетный гид\"! " +
//                                "Качай бесплатно в GooglePlay")
//                        .getIntent();
//
//                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                if (shareIntent.resolveActivity(activity.getParent().getPackageManager()) != null) {
//                    activity.getParent().startActivity(shareIntent);
//                }


//                Intent shareIntent = new Intent();
//                shareIntent.setAction(Intent.ACTION_SEND);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using this WC app");
//                shareIntent.setType("text/plain");
//                activity.startActivity(shareIntent);
//                Toast.makeText(activity, "TTT", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void resume() {
        Log.e("FINILAZE", "resume");
    }

    @Override
    public void pause() {
        Log.e("FINILAZE", "pause");
    }

    @Override
    public void release() {
        Log.e("FINILAZE", "pause");
    }

    public void setBinding(ActivityAboutBinding binding) {
        this.binding = binding;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
