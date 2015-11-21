package waitr.vendorapp.mc.waitruser.adapters;


import android.content.ContentValues;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;

import waitr.vendorapp.mc.waitruser.DbUtils.DbHelper;
import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Helpers.CircleTransform;
import waitr.vendorapp.mc.waitruser.Helpers.CommonTaskLoop;
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
            case R.id.addToCartButton: addToCart(v);
                break;
            case R.id.openItemButton:
                break;
        }

    }

    private void addToCart(View v){
        ViewGroup parent = (ViewGroup) v.getParent().getParent();
        int item_id = Integer.parseInt(((TextView) parent.findViewById(R.id.cart_item_id)).getText().toString());
        int pos = -1;
        Log.d("Menu Adapter", "item id : " + item_id);
        for (int i = 0; i < menuitem.size(); i++)
            if (menuitem.get(i).getId() == item_id) {
                generateSql(menuitem.get(i),parent);

                break;
            }
    }

    public void generateSql(final Item selected, final ViewGroup parent) {
        CommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
               // String query_normal = "INSERT INTO %s VALUES ('%d', %s, '%s', '%f', '%f');";
                String Image = "http://globe-views.com/dcim/dreams/food/food-06.jpg";
                DbHelper  mDbHelper = new DbHelper(parent.getContext());
                SQLiteDatabase mSqLiteDatabase = mDbHelper.getWritableDatabase();
                ContentValues insertVals = new ContentValues();
                insertVals.put(DbContract.Cart.ITEM_ID, selected.getId());
                insertVals.put(DbContract.Cart.ITEM_NAME,selected.getFoodName());
                insertVals.put(DbContract.Cart.IMAGE_URL,selected.getFoodImage());
                insertVals.put(DbContract.Cart.QUANTITY_ORDERED,selected.getQuantityOrdered());
                insertVals.put(DbContract.Cart.PRICE,selected.getPrice());

                try {
                    mSqLiteDatabase.insertOrThrow(DbContract.Cart.TABLE_NAME, null, insertVals);
                } catch (SQLiteConstraintException ex) {
                    // do something with ex or nothing
                    Toast.makeText(parent.getContext(),"Item already in cart",Toast.LENGTH_SHORT).show();
                }

            }

        });


        //TODO: Change image, contains in query


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
        holder.foodItemId.setText(current.getId() + "");


    }
    public void refresh() {

        DbSingleton dbSingleton = DbSingleton.getInstance();
        menuitem.clear();
        menuitem = dbSingleton.getItemsList();
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return menuitem.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView foodImage;

         public TextView foodItemId;
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
             foodItemId = (TextView)itemView.findViewById(R.id.cart_item_id);
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