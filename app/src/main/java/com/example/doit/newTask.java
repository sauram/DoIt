package com.example.doit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newTask#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newTask extends DialogFragment {
    private TextInputEditText title;
    private TextInputEditText description;
    private MaterialButton done;
    private MaterialButton cancel;

    public interface newTaskDialogListener{
        void ondone(String title, String description);
    }
    public newTask() {
        // Required empty public constructor
    }
    public static newTask newInstance(String title) {
        newTask fragment = new newTask();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.new_task_title);
        description=view.findViewById(R.id.new_task_description);
        done = view.findViewById(R.id.done_button);
        cancel = view.findViewById(R.id.cancel_button_1);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(title.getText().toString().isEmpty() && description.getText().toString().isEmpty()))
                    sendDataBack(title.getText().toString(), description.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    private void sendDataBack(String title, String description) {
        newTaskDialogListener listener= (newTaskDialogListener) getTargetFragment();
        listener.ondone(title, description);
        dismiss();
    }
}