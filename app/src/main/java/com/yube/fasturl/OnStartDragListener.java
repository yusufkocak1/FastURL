package com.yube.fasturl;

/**
 * Created by yusuf on 12.05.2018.
 */

import android.support.v7.widget.RecyclerView;

/**
 * Listener for manual initiation of a drag.
 */
public interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);

}