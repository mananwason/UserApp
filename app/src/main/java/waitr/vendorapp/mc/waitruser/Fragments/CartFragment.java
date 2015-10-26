package waitr.vendorapp.mc.waitruser.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.adapters.CartAdapter;
import waitr.vendorapp.mc.waitruser.dataObjects.MenuItemObject;

public class CartFragment extends Fragment {

        private RecyclerView tracksRecyclerView;
        private CartAdapter cartAdapter;
        private List<MenuItemObject> list;
        private FrameLayout frameLayout;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View view = inflater.inflate(R.layout.fragment_cart, container, false);
            tracksRecyclerView = (RecyclerView) view.findViewById(R.id.list_tracks);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_menu);
            MenuItemObject m1 = new MenuItemObject(bitmap, "Item 1", "contents", 100, 3.5, 1);
            MenuItemObject m2 = new MenuItemObject(bitmap, "Item 2", "contents", 100.9, 3.5, 2);
            MenuItemObject m3 = new MenuItemObject(bitmap, "Item 3", "contents", 111, 3.5, 3);
            MenuItemObject m4 = new MenuItemObject(bitmap, "Item 4", "contents", 1000, 3.5, 4);
            frameLayout = (FrameLayout) view.findViewById(R.id.frame_layout);
            list = new ArrayList<>();
            list.add(m1);list.add(m2);list.add(m3);list.add(m4);
            cartAdapter = new CartAdapter(list);
            tracksRecyclerView.setAdapter(cartAdapter);
            tracksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            Snackbar.make(frameLayout,
                    "Total price: "+ 1311.9,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Proceed to payment", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setActionTextColor(Color.RED)
                    .show();
            return view;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            menu.clear();

        }


}
