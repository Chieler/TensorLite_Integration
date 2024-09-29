package com.example.tensorlite_integrationtest;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class TensorFlowInterpretor{
    private Interpreter tfLite;
    private AssetManager manager;
    public void intitInterpretor(){
        try{
            tfLite = new Interpreter(loadModelFile("app/src/main/assets/efficientnet_model.tflite"));
        }catch(IOException e){
            e.printStackTrace();

        }

    }
    public MappedByteBuffer loadModelFile(String modelPath) throws IOException{
       AssetFileDescriptor fileDescriptor = manager.openFd(modelPath);
       FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
       FileChannel fileChannel =inputStream.getChannel();
       long startOffset = fileDescriptor.getStartOffset();
       long declaredLength = fileDescriptor.getDeclaredLength();
       return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    public float[] runInference(float[] input){
        if(tfLite ==null) {
            throw new IllegalStateException("No interpretor initialized");
        }else{
            int[] outputShape = tfLite.getOutputTensor(0).shape();
            float[][] output = new float[1][outputShape[1]];
            tfLite.run(input, output);
            return output[0];
        }
    }
    public void close(){
        if(tfLite!=null) tfLite.close();
    }



}

