package home.liuxl.per.godriving.ui.activity;

import android.os.Bundle;

import home.liuxl.per.godriving.R;
import home.liuxl.per.godriving.presenter.MainPresenter;


public class MainActivity extends BaseActivity<MainPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}
