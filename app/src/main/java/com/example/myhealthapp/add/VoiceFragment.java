package com.example.myhealthapp.add;

import android.media.AudioRecord;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myhealthapp.R;
import com.example.myhealthapp.ml.SoundclassifierWithMetadata;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.audio.TensorAudio;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.tensorflow.lite.task.audio.classifier.AudioClassifier;
import org.tensorflow.lite.task.audio.classifier.Classifications;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class VoiceFragment extends Fragment {
    static int REQUEST_VOICE = 1337;
    AudioClassifier ac;
    AudioRecord ar;
    TensorAudio tensor;
    Boolean k;

    public VoiceFragment() {
        try {
            ac = AudioClassifier.createFromFile(requireContext(), "soundclassifier_with_metadata.tflite");
            ar = ac.createAudioRecord();
            tensor = ac.createInputTensorAudio();
            k = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_voice, container, false);

        Button b = myView.findViewById(R.id.record_voice);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (k) {
                    startRecording();
                    k = false;
                } else {
                    startRecording();
                    k = true;
                }
            }
        });

        return myView;
    }

    public void startRecording() {
        ar.startRecording();
    }

    public void stopRecording() {
        ar.stop();
        tensor.load(ar);
        List<Classifications> output = ac.classify(tensor);

        for (Classifications it: output) {
            Log.d("IMAD", it.getCategories().get(0).getLabel());
        }
    }
}