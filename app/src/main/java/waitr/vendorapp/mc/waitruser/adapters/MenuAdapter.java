package waitr.vendorapp.mc.waitruser.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import waitr.vendorapp.mc.waitruser.Helpers.CircleTransform;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;

/**
 * Created by siddharth on 10/23/15.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements View.OnClickListener {

    ArrayList<Item> menuitem;


    public MenuAdapter(ArrayList<Item> menuItem) {
        this.menuitem = menuItem;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addToCartButton:
                break;
            case R.id.openItemButton:
                break;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.menu_card_layout, parent, false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item current = menuitem.get(position);
        holder.foodName.setText(current.getFoodName());
        holder.contents.setText(current.getContents());
        holder.price.setText("Price : " + current.getPrice());
        holder.rating.setRating((float)current.getRating());
        Uri uri = Uri.parse(current.getFoodImage());
        Picasso.with(holder.foodImage.getContext()).load(uri).transform(new CircleTransform()).into(holder.foodImage);
//        holder.itemId.setText(current.getId() + "");
//        holder.quantity.setText("Quantity : " + current.getQuantityOrdered());

    }

    @Override
    public int getItemCount() {
        return menuitem.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView foodImage;
        public TextView foodName;
        public TextView contents;
        public TextView price;
        public RatingBar rating;
        public Button addToCartButton;
        public ImageButton openItemButton;

         public ViewHolder(View itemView) {
             super(itemView);
             foodImage = (ImageView) itemView
                     .findViewById(R.id.foodImage);
             foodName = (TextView) itemView
                     .findViewById(R.id.foodName);
             contents = (TextView) itemView
                     .findViewById(R.id.foodContent);
             price = (TextView) itemView
                     .findViewById(R.id.foodPrice);
             rating = (RatingBar) itemView
                     .findViewById(R.id.ratingBar);
             addToCartButton = (Button) itemView
                     .findViewById(R.id.addToCartButton);
             openItemButton = (ImageButton) itemView
                     .findViewById(R.id.openItemButton);
             addToCartButton.setOnClickListener(MenuAdapter.this);
             openItemButton.setOnClickListener(MenuAdapter.this);
         }
     }



}