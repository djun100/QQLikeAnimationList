package com.ginkgoavenue.util;

import java.util.List;

public class GifObj {

	private String gifID;
	private List<GifTextDrawable> drawable; 
	
	public GifObj(String gifID,List<GifTextDrawable>  drawableList)
	{
		this.gifID = gifID;
		this.drawable = drawableList;
	}
	public String getGifId()
	{
		return this.gifID;
	}
	public List<GifTextDrawable> getGifTextDrawableList()
	{
		return this.drawable;
	}	
}
