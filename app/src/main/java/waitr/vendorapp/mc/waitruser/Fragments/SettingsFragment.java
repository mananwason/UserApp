package waitr.vendorapp.mc.waitruser.Fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import waitr.vendorapp.mc.waitruser.Helpers.Constants;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.activities.MainActivity;


public class SettingsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    private Switch receiveNotificationsOrder;
    private Switch receiveNotificationsNewItem;
    SharedPreferences mSharedPreferences;
    Typeface mFont;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);
        mFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/font.ttf");
        receiveNotificationsOrder = (Switch)view.findViewById(R.id.switch_notifications_order);
        receiveNotificationsNewItem = (Switch)view.findViewById(R.id.switch_notifications_new_item);
        receiveNotificationsOrder.setTypeface(mFont);
        receiveNotificationsNewItem.setTypeface(mFont);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        boolean orderSwitch = mSharedPreferences.getBoolean(Constants.RECEIVE_NOTIFICATIONS_ORDER,false);
        boolean itemSwitch = mSharedPreferences.getBoolean(Constants.RECEIVE_NOTIFICATIONS_NEW_ITEM,false);
        receiveNotificationsOrder.setChecked(orderSwitch);
        receiveNotificationsNewItem.setChecked(itemSwitch);
        return view;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch(buttonView.getId()){
            case R.id.switch_notifications_order: if(isChecked)
                                                    mSharedPreferences.edit().putBoolean(Constants.RECEIVE_NOTIFICATIONS_ORDER,true).apply();
                                                    else
                                                    mSharedPreferences.edit().putBoolean(Constants.RECEIVE_NOTIFICATIONS_ORDER,false).apply();
                                                    break;
            case R.id.switch_notifications_new_item:if(isChecked)
                                                     mSharedPreferences.edit().putBoolean(Constants.RECEIVE_NOTIFICATIONS_NEW_ITEM,true).apply();
                                                    else
                                                        mSharedPreferences.edit().putBoolean(Constants.RECEIVE_NOTIFICATIONS_NEW_ITEM,false).apply();
                                                    break;
        }
    }
}
