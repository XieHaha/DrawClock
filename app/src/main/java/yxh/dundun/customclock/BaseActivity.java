package yxh.dundun.customclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dundun on 16/2/17.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setLayoutID() != 0) {
            setContentView(setLayoutID());
        }

        initView();
        initClass();
        initData();
    }

    protected abstract int setLayoutID();

    protected void initClass() {
    }

    protected void initData() {

    }

    protected void initView() {

    }
}
