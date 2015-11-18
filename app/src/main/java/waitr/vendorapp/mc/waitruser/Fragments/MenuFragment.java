package waitr.vendorapp.mc.waitruser.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import waitr.vendorapp.mc.waitruser.DbUtils.DbSingleton;
import waitr.vendorapp.mc.waitruser.Events.RefreshUiEvent;
import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.UserApp;
import waitr.vendorapp.mc.waitruser.adapters.MenuAdapter;

/**
 * Created by siddharth on 23/10/15.
 */

public class MenuFragment extends Fragment {

    private static final int INITIAL_DELAY_MILLIS = 300;

    private MenuAdapter menuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_fragment_screen, container, false);
        RecyclerView listView = (RecyclerView) rootView.findViewById(R.id.list_tracks);
        UserApp.getEventBus().register(this);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_menu);
//        Item m1 = new Item(1, "Item 1", "http://globe-views.com/dcim/dreams/food/food-06.jpg", "contents", 100, 3.5, 2,1);
//        Item m2 = new Item(1, "Item 1", "http://globe-views.com/dcim/dreams/food/food-06.jpg", "contents", 100, 3.5, 2,1);
//        Item m3 = new Item(1, "Item 1", "http://globe-views.com/dcim/dreams/food/food-06.jpg", "contents", 100, 3.5, 2,1);
//        Item m4 = new Item(1, "Item 1", "http://globe-views.com/dcim/dreams/food/food-06.jpg", "contents", 100, 3.5, 2,1);
//        ArrayList<Item> list = new ArrayList<>();
//        list.add(m1);
//        list.add(m2);
//        list.add(m3);
//        list.add(m4);

        DbSingleton dbSingleton = DbSingleton.getInstance();
        menuAdapter = new MenuAdapter(dbSingleton.getItemsList());
        listView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(menuAdapter);
        return rootView;
    }

    @Subscribe
    public void RefreshData(RefreshUiEvent event){
        Log.d("retro", "refresh items");
        menuAdapter.refresh();
    }
//    @Override
//    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
//
//    }
}
