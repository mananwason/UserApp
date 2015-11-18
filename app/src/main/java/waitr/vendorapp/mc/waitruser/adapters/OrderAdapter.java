package waitr.vendorapp.mc.waitruser.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.dataObjects.Order;

/**
 * Created by siddharth on 11/14/15.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Viewholder> implements View.OnClickListener {

    ArrayList<Order> orderObjects;

    public OrderAdapter(ArrayList<Order> mOrderObjects) {
        orderObjects = mOrderObjects;
    }

    @Override
    public OrderAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_card_layout, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.Viewholder holder, int position) {
        Order mOrderObject = orderObjects.get(position);
        holder.vendorName.setText(mOrderObject.getVendorName());
        holder.orderId.setText(mOrderObject.getOrderId() + "");
        holder.dateOfOrder.setText(mOrderObject.getDateOfOrder());
//        holder.dateOfOrder.setText(mOrderObject.getDateOfOrder());
//        holder.timeOfOrder.setText(mOrderObject.getDateOfOrder().);
        holder.cost.setText(mOrderObject.getCostOfOrder() + "");
        holder.orderStatus.setText(mOrderObject.getIsOrderCompleted()+"");
        holder.paymentStatus.setText(mOrderObject.getIsPaymentMade()+"");
    }

    @Override
    public int getItemCount() {
        return orderObjects.size();
    }

    public void refresh(int source) {
        DbSingleton dbSingleton = DbSingleton.getInstance();
        orderObjects.clear();
        if(source==1)
            orderObjects = dbSingleton.getCompletedOrdersList();
        else
            orderObjects = dbSingleton.getPendingOrdersList();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    class Viewholder extends RecyclerView.ViewHolder {
        TextView vendorName;
        TextView orderId;
        TextView cost;
        TextView dateOfOrder;
        TextView timeOfOrder;
        TextView orderStatus;
        TextView paymentStatus;
        ImageButton viewOrderDetailsButton;

        public Viewholder(View itemView) {
            super(itemView);
//            itemView.setClickable(true);
            vendorName = (TextView) itemView.findViewById(R.id.vendorNameTextview);
            orderId = (TextView) itemView.findViewById(R.id.orderIdTextview);
            cost = (TextView) itemView.findViewById(R.id.costTextView);
            dateOfOrder = (TextView) itemView.findViewById(R.id.dateTextView);
            timeOfOrder = (TextView) itemView.findViewById(R.id.timeTextview);
            viewOrderDetailsButton = (ImageButton) itemView.findViewById(R.id.viewOrderDetailsButton);
            orderStatus = (TextView) itemView.findViewById(R.id.orderStatusTextView);
            paymentStatus = (TextView) itemView.findViewById(R.id.paymentStatusTextview);
            viewOrderDetailsButton.setOnClickListener(OrderAdapter.this);
//            removeItem.setClickable(true);

        }

    }
}
