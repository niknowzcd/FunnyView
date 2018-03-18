package com.mysample.application.md;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mysample.application.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dly on 2018/1/11.
 */

public class TabLayoutFragment extends Fragment {

    public static final String EXTRA_TITLE = "extra_title";

    public static TabLayoutFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);

        TabLayoutFragment fragment = new TabLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tablayout, container, false);
        String title = getArguments().getString(EXTRA_TITLE);
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            testList.add(title);
        }
        ListView listView = rootView.findViewById(R.id.list_item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, testList));
        return rootView;
    }
}
