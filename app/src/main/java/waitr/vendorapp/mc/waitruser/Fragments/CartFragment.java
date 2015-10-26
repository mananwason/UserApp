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
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.adapters.CartAdapter;
import waitr.vendorapp.mc.waitruser.dataObjects.MenuItemObject;

import com.paytm.pgsdk.PaytmClientCertificate;
import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

public class CartFragment extends Fragment {

        private RecyclerView tracksRecyclerView;
        private CartAdapter cartAdapter;
        private List<MenuItemObject> list;
        private FrameLayout frameLayout;
        private PaytmPGService Service = null;


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
                            Service = PaytmPGService.getStagingService(); //for testing environment
                            Map<String, String> paramMap = new HashMap<>();
                            Random randomGenerator = new Random();
                            String randomInt = String.valueOf(randomGenerator.nextInt(10000));
                            //these are mandatory parameters
                            paramMap.put("REQUEST_TYPE", "DEFAULT");
                            paramMap.put("ORDER_ID", randomInt);
                            paramMap.put("MID", "shopma19998593390567");
                            paramMap.put("CUST_ID", "sid0893");
                            paramMap.put("CHANNEL_ID", "WAP");
                            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                            paramMap.put("WEBSITE", "shpmteswap");
                            paramMap.put("TXN_AMOUNT", String.valueOf(1311.9));
                            paramMap.put("THEME", "merchant");
                            PaytmOrder Order = new PaytmOrder(paramMap);
                            PaytmMerchant Merchant = new PaytmMerchant("http://www.theshopmates.com/paytm/generate_checksum","http://www.theshopmates.com/paytm/verify_checksum");
                            PaytmClientCertificate Certificate = null;
                            Service.initialize(Order, Merchant, Certificate);
                            Service.startPaymentTransaction(getContext(), true, true, new PaytmPaymentTransactionCallback() {
                                @Override
                                public void onTransactionSuccess(Bundle bundle) {
                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onTransactionFailure(String s, Bundle bundle) {
                                    Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void networkNotAvailable() {
                                    Toast.makeText(getContext(), "Nw unavailable", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void clientAuthenticationFailed(String s) {
                                    Toast.makeText(getContext(), "Client auth failed", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void someUIErrorOccurred(String s) {

                                }

                                @Override
                                public void onErrorLoadingWebPage(int i, String s, String s2) {

                                }
                            });
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
