package com.ginkgoavenue.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

public class SetGifText {

	private Context context;
	//表情
    private static Integer[] faceIds = FaceDate.getFaceIds();
    private static String[] faceNames = FaceDate.getFaceNames();
    private final int Face_Text_MaxLength = 7;

    private static List<GifObj> gifList = new ArrayList<GifObj>();
   //存储正文中表情符号的位置
   class facePos
   {
		int s;//表情文字起始
		int e;//表情文字结束点
		int i;//该表情文字表示的表情的序号

		public facePos(int s,int e,int i)
		{
			this.s=s;
			this.e=e;
			this.i=i;
		}

   }
    
   public SetGifText(Context context)
   {
	   this.context = context;
	   
   }

    /**
     * 
     * @param tv TextView
     * @param content 待设置到TextView上的内容
     * @param TextId 对该段内容的标识ID,作为GIFID的前缀将本段文本中的GIF与其他文本中的GIF区别开来。如果只有一个TextView可以设为0
     */
	public void setSpannableText(final TextView tv,final String content,final int TextId)
	    {

	        //先显示文字
	    	tv.setText(content);
	    	//新线程计算是否需要替换表情，需要替换就替换
	        new Thread(new Runnable(){

				public void run() {
					
					//用以存储需替换的表情的位置
					List<facePos> faceList = new ArrayList<facePos>();
					//查找表情的位置
				   	for(int i=0;i<content.length();i++)
			    	{
			    		if(content.charAt(i)=='[')
			    		{

			    			for(int k=i;k<i+Face_Text_MaxLength;k++)
			    			{
			    				if(content.charAt(k)==']')
			    				{
			                        for(int j =0;j<faceNames.length;j++)
			                        {
			                        	if(faceNames[j].equals(content.substring(i+1,k)))
			                        	{
			                        		//保存需替换的表情文字的位置
			                        		facePos fp = new facePos(i,k,j);
			                        		faceList.add(fp);
			                        	}
			                        }

			    	    			
			    	    			break;
			    				}
			    			}

			    			
			    		}
			    	}
			    	

			    	
			    	
			    	//如果无表情
			    	if(faceList.size()==0)
			    	{
						
			    	}
			    	else
			    	{
			      	 //如果有表情
    		
			     		//计算该gif的标识
			    		final String gifID = TextId+"";
			            //是否该表情已有动画
			            int isExist = gifExist(gifID);
			           // Log.v("hwLog","gifID:"+gifID+"/nisExist="+isExist);
			            if(isExist!=-1)
			            {
			            	Log.e("hwLog","终止:"+isExist);
			            	//已有动画，终止旧动画
			            	 List<GifTextDrawable> mFaceList = gifList.get(isExist).getGifTextDrawableList();
			            	 
							for(int i=0;i<mFaceList.size();i++)
							{
								mFaceList.get(i).stop();
							}
			            	gifList.remove(isExist);
			            }
			            
			   
			    		SpannableString ss = new SpannableString(content); 
			    		final List<GifTextDrawable> mFaceList = new ArrayList<GifTextDrawable>();
				     	for(int j = 0;j<faceList.size();j++)
				     	{

				            //开始新动画

				     		GifTextDrawable mFace = new GifTextDrawable(tv);
		
				     		final com.ginkgoavenue.util.gifOpenHelper gHelper = new com.ginkgoavenue.util.gifOpenHelper();
				     		gHelper.read(context.getResources().openRawResource(faceIds[faceList.get(j).i]));
				     		BitmapDrawable bd = new BitmapDrawable(gHelper.getImage());
				     		mFace.addFrame(bd, gHelper.getDelay(0));
				     		for (int i = 1; i < gHelper.getFrameCount(); i++) {
				     			mFace.addFrame(new BitmapDrawable(gHelper.nextBitmap()), gHelper
				     					.getDelay(i));
				     		}
		
		                   
		                    float faceH = bd.getIntrinsicHeight();
		                    float faceW = bd.getIntrinsicWidth();
		                    
		                    //视为为dp
		                    faceH = faceH;
		                    faceW = faceW;
		                 
		                    //再转回px
		                    faceH = UnitTransformUtil.dip2px(context, faceH);
		                    faceW = UnitTransformUtil.dip2px(context, faceW);
		                    
		                    /*
		                    //表情高度固定为文字高度
		                    float x=faceH/tv.getLineHeight();
		                    faceW = faceW / x;
		                    faceH = tv.getLineHeight();
		                    */
				     		mFace.setBounds(0, 0, (int)faceW,(int)faceH);
				     		mFace.setOneShot(false);
				     	
				             ImageSpan span = new ImageSpan(mFace, ImageSpan.ALIGN_BOTTOM);  
				             //替换一个表情
				             ss.setSpan(span,faceList.get(j).s,faceList.get(j).e+1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				             mFaceList.add(mFace);

				     	}
				     	gifList.add(new GifObj(TextId+"",mFaceList));
				     	
				     	final SpannableString ssForPost = ss;
			             //显示新的已经替换表情的text
			             tv.post(new Runnable(){

							public void run() {
								// TODO 自动生成的方法存根
								tv.setText(ssForPost);
						
								for(int i=0;i<mFaceList.size();i++)
								{
									mFaceList.get(i).start();
								}
							}
			            	 
			             });

			             
			    	}
				}
	        	
	        }).start();
	        
	        
	        
	        
	    }
	
    /**
     * 是否已存在该GIF
     * @param gifID 计算得到的gifID
     * @return 不存在返回-1，存在返回序号
     */
	
    private int gifExist(String gifID)
    {
    	for(int i=0;i<gifList.size();i++)
    	{
    		if(gifList.get(i).getGifId().equals(gifID))
    		{
    			return i;
    		}
    	}
		return -1;
    	
    }

}
