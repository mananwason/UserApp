package waitr.vendorapp.mc.waitruser.adapters;

import android.content.Context;
import android.net.Uri;
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

import java.util.List;

import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;

/**
 * Created by siddharth on 10/23/15.
 */

public class MenuAdapter extends ArrayAdapter<Item> implements View.OnClickListener {

    private LayoutInflater mInflater;

    public MenuAdapter(Context context, List<Item> items) {
        super(context, 0, items);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.menu_card_layout, parent, false);
            holder = new ViewHolder();
            holder.foodImage = (ImageView) convertView
                    .findViewById(R.id.foodImage);
            holder.foodName = (TextView) convertView
                    .findViewById(R.id.foodName);
            holder.contents = (TextView) convertView
                    .findViewById(R.id.foodContent);
            holder.price = (TextView) convertView
                    .findViewById(R.id.foodPrice);
            holder.rating = (RatingBar) convertView
                    .findViewById(R.id.ratingBar);
            holder.addToCartButton = (Button) convertView
                    .findViewById(R.id.addToCartButton);
            holder.openItemButton = (ImageButton) convertView
                    .findViewById(R.id.openItemButton);
            holder.addToCartButton.setOnClickListener(this);
            holder.openItemButton.setOnClickListener(this);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = getItem(position);
//        ImageUtil.displayRoundImage(holder.profileImage, item.getImageURL(),
//                null);
//        ImageUtil.displayImage(holder.image, item.getImageURL(), null);
        Uri itemImage = Uri.parse(item.getFoodImage());
        Picasso.with(holder.foodImage.getContext()).load(itemImage).into(holder.foodImage);
        holder.foodName.setText(item.getFoodName());
        holder.contents.setText(item.getContents());
        holder.price.setText(item.getPrice() + "");
        holder.rating.setRating((float) item.getRating());
        holder.addToCartButton.setTag(position);
        holder.openItemButton.setTag(position);

        return convertView;
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

    private static class ViewHolder {

        public ImageView foodImage;
        public TextView foodName;
        public TextView contents;
        public TextView price;
        public RatingBar rating;
        public Button addToCartButton;
        public ImageButton openItemButton;
    }



}