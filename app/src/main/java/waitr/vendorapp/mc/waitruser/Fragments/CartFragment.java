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

//        Snackbar.make(frameLayout,
//                "Total price: " + cartAdapter.getTotalCost(),
//                Snackbar.LENGTH_INDEFINITE)
//                .setAction("Proceed to payment", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Service = PaytmPGService.getStagingService(); //for testing environment
//                        Map<String, String> paramMap = new HashMap<>();
//                        Random randomGenerator = new Random();
//                        String randomInt = String.valueOf(randomGenerator.nextInt(10000));
//                        //these are mandatory parameters
//                        paramMap.put("REQUEST_TYPE", "DEFAULT");
//                        paramMap.put("ORDER_ID", randomInt);
//                        paramMap.put("MID", "shopma19998593390567");
//                        paramMap.put("CUST_ID", "sid0893");
//                        paramMap.put("CHANNEL_ID", "WAP");
//                        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
//                        paramMap.put("WEBSITE", "shpmteswap");
//                        paramMap.put("TXN_AMOUNT", String.valueOf(cartAdapter.getTotalCost()));
//                        paramMap.put("THEME", "merchant");
//                        PaytmOrder Order = new PaytmOrder(paramMap);
//                        PaytmMerchant Merchant = new PaytmMerchant("http://www.theshopmates.com/paytm/generate_checksum", "http://www.theshopmates.com/paytm/verify_checksum");
//                        PaytmClientCertificate Certificate = null;
//                        Service.initialize(Order, Merchant, Certificate);
//                        Service.startPaymentTransaction(getContext(), true, true, new PaytmPaymentTransactionCallback() {
//                            @Override
//                            public void onTransactionSuccess(Bundle bundle) {
//                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onTransactionFailure(String s, Bundle bundle) {
//                                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void networkNotAvailable() {
//                                Toast.makeText(getContext(), "Nw unavailable", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void clientAuthenticationFailed(String s) {
//                                Toast.makeText(getContext(), "Client auth failed", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void someUIErrorOccurred(String s) {
//
//                            }
//
//                            @Override
//                            public void onErrorLoadingWebPage(int i, String s, String s2) {
//
//                            }
//                        });
//                    }
//                })
//                .setActionTextColor(Color.RED)
//                .show();
//        return view;
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