package com.isteer.service.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum BitmapManagerNew {  
	
	    INSTANCE;  
	  
	    public static Map<String, Bitmap> cache = new HashMap<String, Bitmap>();
	    private final ExecutorService pool;
	    private Map<ImageView, String> imageViews = Collections
	            .synchronizedMap(new WeakHashMap<ImageView, String>());
	    private Bitmap placeholder;
	 
	     BitmapManagerNew() {  
	        
	        pool = Executors.newFixedThreadPool(5);
	    }  
	  
	     public void setPlaceholder(Bitmap bmp) {
	         placeholder = bmp;  
	     }  
	   
	     public Bitmap getBitmapFromCache(String url) {
	         if (cache.containsKey(url)) {  
	             return cache.get(url);  
	         }  
	   
	         return null;  
	     }  
	   
	     public void queueJob(final String url, final ImageView imageView,
                              final int width, final int height) {

	         final Handler handler = new Handler() {
	             @Override
	             public void handleMessage(Message msg) {
	                 String tag = imageViews.get(imageView);
	                 if (tag != null && tag.equals(url)) {  
	                     if (msg.obj != null) {  
	                         Log.e("BitmapManager queueJob", "success : " + url);
	                         imageView.setImageBitmap((Bitmap) msg.obj);
	                     } else {  
	                         Log.e("BitmapManager queueJob", "failed : " + url);
	                         imageView.setImageBitmap(null);  
	                     }  
	                 } 
	                 //else
                         //Log.e("BitmapManager queueJob else ", "tag | url : " + tag + " | "+ url);  
 
	             }  
	         };  
	   
	         pool.submit(new Runnable() {
	             @Override
	             public void run() {  
	                 final Bitmap bmp = downloadBitmap(url, width, height);
	                 Message message = Message.obtain();
	                 message.obj = bmp;  
	                 //Log.e("BitmapManager queueJob", "Item downloaded : " + url);  
	   
	                 handler.sendMessage(message);  
	             }  
	         });  
	     }  
	   
	     public void loadBitmap(final String url, final ImageView imageView,
                                final int width, final int height) {
	         imageViews.put(imageView, url);

	         Bitmap bitmap = getBitmapFromCache(url);
	   
	        if (bitmap != null) {  
	             Log.e("BitmapManager loadBitmap", "Item loaded from cache : " + url);
	             bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
	             imageView.setImageBitmap(bitmap);  
	         } else {  
	             Log.e("BitmapManager loadBitmap", "Item not exists in cache : " + url);
	             imageView.setImageBitmap(null);  
	             queueJob(url, imageView, width, height);  

		         /*if(!url.startsWith(BMAppConstant.PREFIX_FB_IMAGE))
	             	queueJob(url, imageView, width, height);  
	             else
	             {
					try {
		            	 HttpGet httpRequest = new HttpGet(URI.create(url));
		                 HttpClient httpclient = new DefaultHttpClient();
		                 HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
		                 HttpEntity entity = response.getEntity();
		                 BufferedHttpEntity bufHttpEntity;
		                 bufHttpEntity = new BufferedHttpEntity(entity);
		                 bitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
			             cache.put(url, new SoftReference<Bitmap>(bitmap));  
			             bitmap = Bitmap.createScaledBitmap(getCroppedBitMap(bitmap), width, height, true);  
		                 httpRequest.abort();
			             imageView.setImageBitmap(bitmap);  
					} catch (IOException e) {
			             Log.e("BitmapManager loadBitmap", "IOException : " + e.toString());  
			             e.printStackTrace();
					}
	             }*/
	         }
	     }  
	   
	     private Bitmap downloadBitmap(String url, int width, int height) {
	         try {  
	             Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
	                     url).getContent());  
            	 cache.put(url, bitmap);  
	             bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
	             return bitmap;  
	         } catch (MalformedURLException e) {
	             Log.e("BitmapManager downloadBitmap", "MalformedURLException : " + url);
	             e.printStackTrace();  
	         } catch (Exception e) {
	             Log.e("BitmapManager downloadBitmap", "Exception : " + url);
	            e.printStackTrace();  
	         }  
	   
	         return null;  
	     }  
	     
	     private Bitmap getCroppedBitMap(Bitmap srcBmp)
	     {
	    	 Bitmap dstBmp;
	    	 
	    	 if (srcBmp.getWidth() >= srcBmp.getHeight()){

	    		  dstBmp = Bitmap.createBitmap(
	    		     srcBmp, 
	    		     srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
	    		     0,
	    		     srcBmp.getHeight(), 
	    		     srcBmp.getHeight()
	    		     );

	    		}else{

	    		  dstBmp = Bitmap.createBitmap(
	    		     srcBmp,
	    		     0, 
	    		     srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
	    		     srcBmp.getWidth(),
	    		     srcBmp.getWidth() 
	    		     );
	    		}
	    	 
	    	 return dstBmp;
	     }
	     
	 }  