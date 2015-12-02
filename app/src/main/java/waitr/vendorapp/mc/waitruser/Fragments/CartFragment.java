package waitr.vendorapp.mc.waitruser.Fragments;

/**
 * Created by siddharth on 11/14/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.activities.PaymentActivity;
import waitr.vendorapp.mc.waitruser.adapters.CartAdapter;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;

public class CartFragment extends Fragment {

    public static TextView displayCost;
    Button payNowButton;
    double totalCost;
    private RecyclerView tracksRecyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Item> list;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        tracksRecyclerView = (RecyclerView) view.findViewById(R.id.list_tracks);

        totalCost = 0;

        frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
        DbSingleton dbSingleton = DbSingleton.getInstance();
        list = dbSingleton.getCartList();
        cartAdapter = new CartAdapter(list);
        tracksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tracksRecyclerView.setAdapter(cartAdapter);
        displayCost = (TextView) view.findViewById(R.id.displayCostTextView);
        displayCost.setText("Total Cost: " + cartAdapter.getTotalCost());
        payNowButton = (Button) view.findViewById(R.id.payNowButton);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 0) {
                    Intent payNow = new Intent(view.getContext(), PaymentActivity.class);
                    payNow.putExtra("cost", displayCost.getText());
                    startActivity(payNow);
                } else
                    Toast.makeText(view.getContext(), "No item in cart!", Toast.LENGTH_SHORT).show();

            }

        });

        list =  dbSingleton.getCartList();
        cartAdapter = new CartAdapter(list);
        for(Item mItem: list){
            totalCost += mItem.getPrice();
        }
        tracksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        tracksRecyclerView.setAdapter(cartAdapter);
        return view;
    }
}