package com.example.gerardo.calcu;

/**
 * Created by Shade on 5/9/2016.
 */

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String[] titles = {"1",
            "2",
            "3",
            "4",
            };

    private String[] details = {"FORMULA GENERAL",
            "ECUACIONES DE PRIMER GRADOs", "ECUACIONES DE SEGUNDO GRADO",
            "ECUACIONES DE TERCER GRADO"};

    private int[] images = { R.drawable.android_image_1,
            R.drawable.android_image_2,
            R.drawable.android_image_3,
            R.drawable.android_image_4,
             };

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
       // public TextView itemTitle;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            //itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    switch (position){
                        case 0:
                        Snackbar.make(v, "Se resolverá por formula general",
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                            break;
                        case 1:

                            Snackbar.make(v, "Se resolverá ecuacion de primer grado",
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case 2:

                            Snackbar.make(v, "Se resolverá ecuacion de segundo grado",
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case 3:

                            Snackbar.make(v, "Se resolverá ecuacion de tercer grado",
                                    Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
       // viewHolder.itemTitle.setText(titles[i]);
        viewHolder.itemDetail.setText(details[i]);
        viewHolder.itemImage.setImageResource(images[i]);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}