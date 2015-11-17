package waitr.vendorapp.mc.waitruser.Fragments;
/**
 * Created by siddharth on 11/14/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Date;

import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.adapters.OrderAdapter;
import waitr.vendorapp.mc.waitruser.dataObjects.Order;


public class CompletedOrderFragment extends Fragment {

    private RecyclerView tracksRecyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<Order> myList;
    private FrameLayout frameLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_completed_order, container, false);
        tracksRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_tracks);
        frameLayout = (FrameLayout) rootView.findViewById(R.id.frame_layout);
        Order m1 = new Order(1, 1, "sid", "1,2,3",  "12/10/2015", 10, true, true);
        Order m2 = new Order(1, 1, "sid", "1,2,3",  "12/10/2015", 10, true, true);
        Order m3 = new Order(1, 1, "sid", "1,2,3",  "12/10/2015", 10, true, true);
        Order m4 = new Order(1, 1, "sid", "1,2,3",  "12/10/2015", 10, true, true);
        Order m5 = new Order(1, 1, "sid", "1,2,3",  "12/10/2015", 10, true, true);
        myList = new ArrayList<>();
        myList.add(m1);
        myList.add(m2);
        myList.add(m3);
        myList.add(m4);
        myList.add(m5);
        orderAdapter = new OrderAdapter(myList);
        tracksRecyclerView.setAdapter(orderAdapter);
        tracksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }


}
