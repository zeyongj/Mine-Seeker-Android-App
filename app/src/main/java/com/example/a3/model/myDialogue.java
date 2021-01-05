package com.example.a3.model;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a3.R;


public class myDialogue extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Create the view to show
       View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_dialogue,null);

        //Create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                     /*   TextView tv = getActivity().findViewById(R.id.dialog_tv2);
                        tv.setText("Congratulations! You Win!");
                        Intent intent = new Intent(myDialogue.this, MainActivity.class);
                        startActivity(intent);
                        break;*/
                    getActivity().finish();
                    break;
                }
            }
        };

        //Build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Game Over!")
                .setView(v)
                .setPositiveButton(android.R.string.ok,listener)
                .create();
    }
}