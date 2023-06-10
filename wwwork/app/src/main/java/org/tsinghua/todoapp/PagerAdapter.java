package org.tsinghua.todoapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStateAdapter {


    private final ArrayList<Fragment> mFragments = new ArrayList<>();
    public PagerAdapter(HomeFragment fragment) {
        super(fragment);
        mFragments.add(new ListFragment());
        mFragments.add(new EmptyFragment());
        mFragments.add(new FriendFragment());
        mFragments.add(new MyBlogFragment());
    }



    // 返回Fragment数量
    @Override
    public int getItemCount() {
        return mFragments.size();
    }

    // 返回Fragment实例
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }
}

