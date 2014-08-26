package com.ginkgoavenue.util;

import java.lang.ref.WeakReference;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.DrawableContainer.DrawableContainerState;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class GifTextDrawable extends AnimationDrawable {

	private TextView tv;
//	 private WeakReference<Callback> mCallback = null;
	 private int curFrame=-1;

	 private boolean Stop=false;
	 

	public GifTextDrawable(TextView textView)
	{
		this.tv=textView;

	}
		
	@Override
	public void invalidateDrawable(Drawable who) {
		// TODO Auto-generated method stub
		super.invalidateDrawable(who);
		
	//	Log.v("hwLog","invalidateDrawable");
	}

	@Override
	public boolean selectDrawable(int idx) {
		// TODO Auto-generated method stub
		//Log.v("hwLog","selectDrawable,idx="+idx);
		curFrame = idx;
		return super.selectDrawable(idx);
	}

	@Override
	public void scheduleSelf(Runnable what, long when) {
		// TODO Auto-generated method stub
		//Log.v("hwLog","scheduleSelf,when="+when);
		//super.scheduleSelf(what, when);

		if(!Stop)
		{
			tv.postInvalidate();
			tv.postDelayed(this, this.getDuration(curFrame));
		}
	//	Log.v("hwLog","scheduleSelf,"+text);
	}
	
	
@Override
	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
		Stop=true;
		
	}

	/*
	@Override
	public Callback getCallback() {

        Log.v("hwLog","getCallback,Callback="+super.getCallback());
		//return super.getCallback();
        if(mCallback!=null)
        {
        	//Log.v("hwLog", "mCallback.get()="+mCallback.get());
          return mCallback.get();
        }
        return super.getCallback();
	}
	*/

   
	

}
