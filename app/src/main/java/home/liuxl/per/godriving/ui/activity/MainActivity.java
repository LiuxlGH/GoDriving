package per.liuxl.home.godriving.ui.activity;

import android.os.Bundle;

import per.liuxl.home.godriving.R;
import per.liuxl.home.godriving.presenter.MainPresenter;


public class MainActivity extends BaseActivity<MainPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
