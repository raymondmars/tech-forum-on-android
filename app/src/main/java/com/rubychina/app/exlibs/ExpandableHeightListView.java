package com.rubychina.app.exlibs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by robot on 5/19/14.
 */
public class ExpandableHeightListView extends ListView {

    boolean expanded = true;
    boolean STATE_REFRESH_ENABLED = false;
    boolean STATE_REFRESHING = false;
    float THRESHOLD = 10;
    float startY = 0;

    public ExpandableHeightListView(Context context)
    {
        super(context);
    }

    public ExpandableHeightListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ExpandableHeightListView(Context context, AttributeSet attrs,int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        if (isExpanded())
        {
            // Calculate entire height by providing a very large height hint.
            // But do not use the highest 2 bits of this integer; those are
            // reserved for the MeasureSpec mode.
            int expandHeightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandHeightSpec);

            //ViewGroup.LayoutParams params = getLayoutParams();
            //params.height = getMeasuredHeight();

        }
        else
        {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //System.out.println("First ["+this.getFirstVisiblePosition()+"]");

        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if ( ( y - startY) > THRESHOLD && STATE_REFRESH_ENABLED && !STATE_REFRESHING ) {
                    Log.d("Move","Move..................");
                }
            }
            break;
            case MotionEvent.ACTION_DOWN: {
                startY = y;
                STATE_REFRESH_ENABLED = getFirstVisiblePosition() == 0; // We are on the first element so we can enable refresh
            }
            case MotionEvent.ACTION_UP: {
                STATE_REFRESHING = false;
            }

        }
        return super.onTouchEvent(event);
    }
}
