package com.midea.wallet.ui.fragment.base;


import com.wytiger.common.base.BaseFragment;

/**
 * Author: wujiejiang
 * Time: 2016/7/4
 * Desc: 用于ViewPager设定惰性加载Fragment
 */
public abstract class LazyLoadFragment extends BaseFragment {

    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {

    }

    protected abstract void lazyLoad();
}
