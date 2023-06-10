package org.tsinghua.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class HomeFragment extends Fragment {


    private View view;

    private PagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public String username ;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Put initial data into the word list.
        /*MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            // 访问MainActivity中的username变量
            username = mainActivity.getUsername();
        }*/
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewpager);
        PagerAdapter adapter = new PagerAdapter(this);
        viewPager.setAdapter(adapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("新发表");
                            break;
                        case 1:
                            tab.setText("新回复");
                            break;
                        case 2:
                            tab.setText("关注");
                            break;
                        case 3:
                            tab.setText("我的发帖");
                            break;
                    }
                }
        );
        tabLayoutMediator.attach();
        return view;
    }


}
