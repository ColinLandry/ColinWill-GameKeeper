package wc.productions.gamekeeper;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danimahardhika.cafebar.CafeBar;
import com.danimahardhika.cafebar.CafeBarTheme;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdatePlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText playerNameInput;
    EditText playerPhoneInput;
    EditText playerEmailInput;

    private Player player;

    private OnFragmentInteractionListener mListener;

    public UpdatePlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 team you are passing.
     * @return A new instance of fragment CreatePlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePlayerFragment newInstance(Parcelable param1) {
        UpdatePlayerFragment fragment = new UpdatePlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_player, container, false);
        MainActivity.fab.hide();
        playerNameInput = (EditText) view.findViewById(R.id.playerNameInput);
        playerPhoneInput = (EditText) view.findViewById(R.id.playerPhoneInput);
        playerEmailInput = (EditText) view.findViewById(R.id.playerEmailInput);
        Button submit = (Button) view.findViewById(R.id.updatePlayer);

        //Populate values
        if(player != null){
            playerNameInput.setText(player.getName());
            playerPhoneInput.setText(player.getPhone());
            playerEmailInput.setText(player.getEmail());
        }

        //Submit updates
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerNameInput.getText().length() != 0 && playerPhoneInput.getText().length() != 0
                        && playerEmailInput.getText().length() != 0){
                    //if numberInput is correct size and is all numbers
                    if(playerPhoneInput.getText().toString().length() == 10 &&
                            playerPhoneInput.getText().toString().matches("[0-9]+")){
                        //If email is valid syntax
                        if(isEmailValid(playerEmailInput.getText().toString())){

                            //Update the passed players values to update in db
                            player.setName(playerNameInput.getText().toString());
                            player.setPhone(playerPhoneInput.getText().toString());
                            player.setEmail(playerEmailInput.getText().toString());

                            //Grab an instance of the database
                            DatabaseHandler db = new DatabaseHandler(getContext());

                            //Update the player in the database
                            db.updatePlayer(player);

                            //Close the database
                            db.close();

                            hideKeyboard();

                            //Grab the fragment manager and move us back a page/fragment
                            fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack();
                        }else{
                            CafeBar.builder(getContext())
                                    .content("Email input error, please use correct email format")
                                    .floating(true)
                                    .fitSystemWindow()
                                    .theme(CafeBarTheme.Custom(Color.parseColor("#ffde59")))
                                    .show();
                        }
                        //If the number is too long, display toast
                    }else{
                        CafeBar.builder(getContext())
                                .content("Number input error, use format (##########)")
                                .floating(true)
                                .fitSystemWindow()
                                .theme(CafeBarTheme.Custom(Color.parseColor("#ffde59")))
                                .show();                    }
                    //Toast popup
                }else{
                    CafeBar.builder(getContext())
                            .content("Please make sure to fill out all fields")
                            .floating(true)
                            .fitSystemWindow()
                            .theme(CafeBarTheme.Custom(Color.parseColor("#ff453f")))
                            .show();
                }

            }
        });

        return view;    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
