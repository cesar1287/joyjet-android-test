package com.joyjet.digitalspace.controller.custom_widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyMainTextView extends TextView {

    public MyMainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/PoiretOne-Regular.ttf"));
    }
}
