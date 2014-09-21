package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.SingleChoiceDialogFragm.NoticeDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class HitOnDialogFragment extends DialogFragment{
	
	public HitOnDialogFragment(){}
	
	
	public static HitOnDialogFragment newInstance(String name, Long userId, Long postId){
		HitOnDialogFragment dialog= new HitOnDialogFragment();
		
		Bundle bundle = new Bundle();
		bundle.putString("name", name);
		bundle.putLong("userId", userId);
		bundle.putLong("postId", postId);
		dialog.setArguments(bundle);
		
		return dialog;
	}
	
	public interface HitOnDialogListener {
        public void onDialogPositiveClick(String faculty, Bundle bundle);
    }
	
	HitOnDialogListener listener;
	
	

    // Override the Fragment.onAttach() method to instantiate the DialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the DialogListener so we can send events to the host
            listener = (HitOnDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement HitOnDialogListener");
        }
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
    	
    	final EditText input = new EditText(getActivity());
    	final TextView output = new TextView(getActivity());
    	final Bundle bundle = getArguments();
    	AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
    	dialog.setTitle("Manda un tuo contatto o un breve messaggio");
        dialog.setView(input);
    	dialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            		String message = input.getText().toString();
            		if (!message.isEmpty()){
            		listener.onDialogPositiveClick(message,bundle);
               	 	dialog.dismiss();
            		}
            	
            }
        }
        		);
    	
    	return dialog.create();
    	
    	
    
    }

}
