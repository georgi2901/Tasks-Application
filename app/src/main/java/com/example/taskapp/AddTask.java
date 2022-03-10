package com.example.taskapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.taskapp.Model.ToDoModel;
import com.example.taskapp.Utils.DBHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Button newTaskAddButton;
    private DBHelper db;

    public static AddTask newInstance(){
        return new AddTask();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =  inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskET);
        newTaskAddButton = getView().findViewById(R.id.newTaskButton);

        db = new DBHelper(getActivity());
        db.openDatabase();

        boolean isUpdated = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdated = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if(task.length()>0)
                newTaskAddButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
        }
        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().equals("")){
                    newTaskAddButton.setEnabled(false);
                    newTaskAddButton.setTextColor(Color.GRAY);
                }
                else{
                    newTaskAddButton.setEnabled(true);
                    newTaskAddButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        boolean finalIsUpdated = isUpdated;
        newTaskAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text = newTaskText.getText().toString();
                if(finalIsUpdated){
                    db.updateTask(bundle.getInt("id"), text);
                }
                else{
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    db.insertTask(task);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }

    }
}
