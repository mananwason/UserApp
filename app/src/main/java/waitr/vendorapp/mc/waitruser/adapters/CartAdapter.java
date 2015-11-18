package waitr.vendorapp.mc.waitruser.adapters;

/**
 * Created by siddharth on 10/26/15.
 */

import android.content.Context;
import android.database.DatabaseUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import waitr.vendorapp.mc.waitruser.DbUtils.DbContract;
import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Fragments.CartFragment;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> implements View.OnClickListener {

    private static Context sContext;
    ArrayList<Item> menuitem;
    private double totalCost;

    public CartAdapter(ArrayList<Item> menuItem, Context context) {
        this.menuitem = menuItem;
        sContext = context;
        for(Item mItem: menuitem){
            totalCost += mItem.getPrice();
        }
    }

   public double getTotalCost(){
       return totalCost;
   }

    @Override
    public CartAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cart_card_layout, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }


    @Override
    public void onBindViewHolder(CartAdapter.Viewholder holder, int position) {
        Item current = menuitem.get(position);
        holder.name.setText(current.getFoodName());
        holder.price.setText("Price : " + current.getPrice());
        holder.itemId.setText(current.getId() + "");
        holder.quantity.setText("Quantity : " + current.getQuantityOrdered());

    }

    @Override
    public int getItemCount() {
        return menuitem.size();
    }

    /* public void refresh() {

         notifyDataSetChanged();

     }

     public void animateTo(List<Item> menuitem) {
         applyAndAnimateRemovals(menuitem);
         applyAndAnimateAdditions(menuitem);
         applyAndAnimateMovedItems(menuitem);
     }

     private void applyAndAnimateRemovals(List<Item> newMicrolocations) {
         for (int i = menuitem.size() - 1; i >= 0; i--) {
             final MenuItemObject microlocation = menuitem.get(i);
             if (!newMicrolocations.contains(microlocation)) {
                 removeItem(i);
             }
         }
     }

     private void applyAndAnimateAdditions(List<Item> newMicrolocations) {
         for (int i = 0, count = newMicrolocations.size(); i < count; i++) {
             final MenuItemObject microlocation = newMicrolocations.get(i);
             if (!menuitem.contains(microlocation)) {
                 addItem(i, microlocation);
             }
         }
     }

     private void applyAndAnimateMovedItems(List<Item> newMicrolocations) {
         for (int toPosition = newMicrolocations.size() - 1; toPosition >= 0; toPosition--) {
             final MenuItemObject microlocation = newMicrolocations.get(toPosition);
             final int fromPosition = menuitem.indexOf(microlocation);
             if (fromPosition >= 0 && fromPosition != toPosition) {
                 moveItem(fromPosition, toPosition);
             }
         }
     }



     public void addItem(int position, Item item) {
         menuitem.add(position, Item);
         notifyItemInserted(position);
     }

     public void moveItem(int fromPosition, int toPosition) {
         final Item location = menuitem.remove(fromPosition);
         menuitem.add(toPosition, location);
         notifyItemMoved(fromPosition, toPosition);
     }
 */
    public Item removeItem(int position) {
       // int id = menuitem.get(position).getId();
        final Item mMenuItemObject = menuitem.remove(position);
        totalCost -= mMenuItemObject.getPrice();
        //CartFragment.mSnackbar.setText("Total price: " + totalCost);
        DbSingleton dbSingleton = DbSingleton.getInstance();
        dbSingleton.deleteRecord(DbContract.Cart.TABLE_NAME,DbContract.Cart.ITEM_ID+" = '"+mMenuItemObject.getId()+"'");
        notifyItemRemoved(position);
        return mMenuItemObject;
    }

    @Override
    public void onClick(View v) {
        ViewGroup parent = (ViewGroup) v.getParent().getParent();
        int pid = Integer.parseInt(((TextView) parent.findViewById(R.id.itemId)).getText().toString());
        int pos = -1;
        Log.d("ABC", "PID : " + pid);
        for (int i = 0; i < menuitem.size(); i++)
            if (menuitem.get(i).getId() == pid) {
                pos = i;
                break;
            }
        removeItem(pos);
    }





    class Viewholder extends RecyclerView.ViewHolder {
        TextView name;
        TextView quantity;
        TextView price;
        TextView itemId;
        ImageButton removeItem;

        public Viewholder(View itemView) {
            super(itemView);
//            itemView.setClickable(true);
            name = (TextView) itemView.findViewById(R.id.foodName);
            quantity = (TextView) itemView.findViewById(R.id.foodQuantity);
            price = (TextView) itemView.findViewById(R.id.foodPrice);
            itemId = (TextView) itemView.findViewById(R.id.itemId);
            removeItem = (ImageButton) itemView.findViewById(R.id.removeItemButton);
            removeItem.setOnClickListener(CartAdapter.this);
//            removeItem.setClickable(true);

        }

    }
}