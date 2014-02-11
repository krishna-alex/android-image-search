package com.codepath.imagesearch;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;


public abstract class EndlessScrollListener implements OnScrollListener {private int visibleThreshold = 5;
	private int total = 0;
	private int lastRequested = 0;

	public int getTotal() {
		return total;
	}

	public void setTotal( int t ) {
		this.total = t;
		lastRequested = 0;
	}
	
	public EndlessScrollListener() {
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		Log.d("DEBUG", "onScroll" + firstVisibleItem + " and firstVisibleItem " + visibleItemCount+ " and total " + totalItemCount);
		if( firstVisibleItem + visibleItemCount >= totalItemCount - visibleThreshold ) {
			if((totalItemCount < this.total) && (lastRequested < totalItemCount)) {
				lastRequested = totalItemCount;
				onLoadMore(totalItemCount);
			}
		}
	}		
	
	public abstract void onLoadMore(int start);
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

}