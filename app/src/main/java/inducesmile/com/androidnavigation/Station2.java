package inducesmile.com.androidnavigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by User on 28/9/2558.
 */
public class Station2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Station2");
        tv.setTextSize(30);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        setContentView(tv);
    }
}