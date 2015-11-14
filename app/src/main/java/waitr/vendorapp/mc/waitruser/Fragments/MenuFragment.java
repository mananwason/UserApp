package waitr.vendorapp.mc.waitruser.Fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import waitr.vendorapp.mc.waitruser.R;
import waitr.vendorapp.mc.waitruser.adapters.MenuAdapter;
import waitr.vendorapp.mc.waitruser.dataObjects.Item;


/**
 * Created by siddharth on 23/10/15.
 */
public class MenuFragment extends Fragment {
    private static final int INITIAL_DELAY_MILLIS = 300;

    private MenuAdapter menuAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_fragment_screen, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_menu);
        Item m1 = new Item(bitmap, "Item 1", "contents", 100, 3.5, 1);
        Item m2 = new Item(bitmap, "Item 2", "contents", 100.9, 3.5, 2);
        Item m3 = new Item(bitmap, "Item 3", "contents", 111, 3.5, 3);
        Item m4 = new Item(bitmap, "Item 4", "contents", 1000, 3.5, 4);
        List<Item> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);

        menuAdapter = new MenuAdapter(getContext(), list);
        listView.setClipToPadding(false);
        listView.setDivider(null);
        Resources r = getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());
        listView.setDividerHeight(px);
        listView.setFadingEdgeLength(0);
        listView.setFitsSystemWindows(true);
        px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                r.getDisplayMetrics());
        listView.setPadding(px, px, px, px);
        listView.setScrollBarStyle(ListView.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setAdapter(menuAdapter);
        return rootView;
    }

//    @Override
//    public void onDismiss(@NonNull ViewGroup listView, @NonNull int[] reverseSortedPositions) {
//
//    }
}
