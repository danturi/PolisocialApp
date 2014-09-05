package it.polimi.dima.polisocial;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;



public class SingleChoiceDialogFragm extends DialogFragment
{
  
    public String faculty;
    private ArrayList<String> faculty_list = new ArrayList<String>();
    
    public SingleChoiceDialogFragm(){}
     
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(String faculty);
    }
    
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
    
    	faculty_list.add("Ing. Industriale,Informazione");
    	faculty_list.add("Ing. Civile,Ambientale,Territoriale");
    	faculty_list.add("Ing. Edile,Architettura");
    	faculty_list.add("Design");
    	faculty_list.add("Architettura e Società");
    	faculty_list.add("Architettura Civile");
    	
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
         
        dialog.setTitle("Seleziona la tua facoltà:");
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	if(faculty == null) {
            		//non esce
            	}else{
            		mListener.onDialogPositiveClick(SingleChoiceDialogFragm.this.faculty);
               	 	dialog.dismiss();
            	}
            	
            }
        }
        		);
         
         
        CharSequence[] cs = faculty_list.toArray(new CharSequence[faculty_list.size()]);
        dialog.setSingleChoiceItems(cs, -1, selectItemListener);
         
        return dialog.create();
    }
     
     


	OnClickListener selectItemListener = new OnClickListener()
    {
 
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
        	
        	SingleChoiceDialogFragm.this.faculty = faculty_list.get(which);
        }
     
    };


}   