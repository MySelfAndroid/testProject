package com.easyway.apps.smartcallrecorder;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

public class Recorder {

	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    static final String AUDIO_RECORDER_FOLDER = "PurposeOfCall-AudioRecorder";
    public static boolean onRuning = false;
    private static int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP };
    private static String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };
    private static MediaRecorder recorder = null;
    private static int currentFormat = 0;
    private static String flName = ""; 
    private static boolean onUse = false;
    
	public static void startRecording() {

		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {

			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			retryRecording();
		} catch (IOException e) {
			e.printStackTrace();
			retryRecording();
		} catch (Exception e) {
			e.printStackTrace();
			retryRecording();
		}
	}
	
	private static MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
                Log.d("Recorder",  "error:" + what + ", " + extra);
        }
    };

    private static MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
       	 Log.d("Recorder", "Warning:" +  what + ", " + extra);
        }
	};
	
	private static void retryRecording(){
   	 Log.d("@@@","retryRecording");
   
   	 recorder = new MediaRecorder();
        
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);
        
        try {
                recorder.prepare();
                recorder.start();
        } catch (IllegalStateException e) {
                e.printStackTrace();
                //recoverVol();
        } catch (IOException e) {
                e.printStackTrace();
                //recoverVol();
        }catch(Exception e){
       	 e.printStackTrace();
       	 //recoverVol();
        }
    }
	
	private static String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);
        
        if(!file.exists()){
                file.mkdirs();
        }
        
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = sdf.format(date);
        
        setFlName(fileName);
       
        return (file.getAbsolutePath() + "/" + fileName + file_exts[currentFormat]);
	}
	
	public static void setOnUse(boolean b){
   	 onUse = b;
    }
    
    public static boolean getOnUse(){
   	return onUse; 
    }
    
    public static String getFlName() {
		return flName;
	}

	public static void setFlName(String flName) {
		Recorder.flName = flName;
	}
    
	public static void stopRecording(){
 		
		 Thread thread = new Thread(){
	        public void run(){
	        	try{
	                if (recorder != null)                                   
	                    recorder.stop();
	                recorder.reset();
	                recorder.release();
	                 
	                	recorder = null;
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
	    };
	    thread.start();  
    }
}
