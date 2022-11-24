package com.example.myhealthapp.add;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.example.myhealthapp.R;
import com.example.myhealthapp.ml.Model;
import com.example.myhealthapp.search.SearchViewModel;


public class CameraFragment extends Fragment {
    TextView result, confidence;
    ImageView imageView;
    Button picture;
    int imageSize = 224;
    Context cntxt;
//    AddViewModel adm;
    FrameLayout fm;

    public CameraFragment(Context cntxt) {
        // Required empty public constructor
        this.cntxt = cntxt;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_camera, container, false);
        result = v.findViewById(R.id.result);
        confidence = v.findViewById(R.id.confidence);
        imageView = v.findViewById(R.id.imageView);
        picture = v.findViewById(R.id.button);
        fm = v.findViewById(R.id.takePic);

//        adm = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
//
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch camera if we have permission
//                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Log.d("IMAD", "HERE1");
                    startActivityForResult(cameraIntent, 1);
//                } else {
//                    //Request camera permission if we don't have it.
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
//                }
            }
        });

        return v;
    }

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(requireContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            // get 1D array of 224 * 224 pixels in image
            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Chapati","Kadhai Panner","Dal Fry","Fried Rice","Bhindi fry","Jeera Aloo","Steamed Rice"};
            String[] calories = {"297","156.15","129.98","172.84","129.53","101.57","127.06"};
            result.setText(classes[maxPos]);
            confidence.setText(calories[maxPos]);

//            adm.getSearched(classes[maxPos]).observe(getViewLifecycleOwner(), data -> {
//                Log.d("IMAD", data.getKnownAs());
//                getChildFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.takePic, new AddFoodFragment(data, "breakfast", "12-11-2022"));
//            });

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
            Log.d("IMAD", e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("IMAD", "HERE2");
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(cntxt.getContentResolver(), data.getData());
                Log.d("IMAD", String.valueOf(image));
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            } catch (IOException e) {
                Log.d("IMAD", e.getMessage());
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}