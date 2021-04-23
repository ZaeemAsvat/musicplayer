package com.zaeemasvat.musicplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new SuggestedPlaylistsFragment();
                break;
            case 1:
                fragment = new MyPlaylistsFragment();
                break;
            case 2:
                fragment = new SongsFragment();
                break;
            case 3:
                fragment = new ArtistsFragment();
                break;
            case 4:
                fragment = new AlbumsFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}