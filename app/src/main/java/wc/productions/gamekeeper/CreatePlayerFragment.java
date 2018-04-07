package wc.productions.gamekeeper;

import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreatePlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreatePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText playerNameInput;
    EditText playerPhoneInput;
    EditText playerEmailInput;

    private Team team;

    private OnFragmentInteractionListener mListener;

    public CreatePlayerFragment() {
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
    public static CreatePlayerFragment newInstance(Parcelable param1) {
        CreatePlayerFragment fragment = new CreatePlayerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            team = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_player, container, false);
        MainActivity.fab.hide();
        playerNameInput = (EditText) view.findViewById(R.id.playerNameInput);
        playerPhoneInput = (EditText) view.findViewById(R.id.playerPhoneInput);
        playerEmailInput = (EditText) view.findViewById(R.id.playerEmailInput);
        Button submit = (Button) view.findViewById(R.id.submitPlayer);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerNameInput.getText().length() != 0 && playerPhoneInput.getText().length() != 0
                        && playerEmailInput.getText().length() != 0){
                    //if numberInput is correct size
                    if(playerPhoneInput.getText().toString().length() == 10){

                        //Set number to the value in the editText
                        int number = Integer.parseInt(playerPhoneInput.getText().toString());

                        //Create the player
                        Player player = new Player(playerNameInput.getText().toString(),
                                Integer.parseInt(playerPhoneInput.getText().toString()),
                                playerEmailInput.getText().toString()
                        );

                        //Grab an instance of the database
                        DatabaseHandler db = new DatabaseHandler(getContext());

                        //Add the player to the database
                        db.addPlayer(player, team);

                        //Close the database
                        db.close();

                        hideKeyboard();

                        //Grab the fragment manager and move us back a page/fragment
                        fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();

                        //If the number is too long, display toast
                    }else if(playerPhoneInput.getText().toString().length() > 10){
                        Toast.makeText(getContext(), "The number you input was too long, use format (##########)", Toast.LENGTH_SHORT).show();

                        //If the number is too short, display toast
                    }else if(playerPhoneInput.getText().toString().length() < 10){
                        Toast.makeText(getContext(), "The number you input was too short, use format (##########)", Toast.LENGTH_SHORT).show();
                    }
                    //Toast popup
                }else{
                    Toast.makeText(getContext(), "Please make sure to fill out all fields", Toast.LENGTH_SHORT).show();
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
}
