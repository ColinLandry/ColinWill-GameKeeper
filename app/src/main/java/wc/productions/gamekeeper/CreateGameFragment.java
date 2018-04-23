package wc.productions.gamekeeper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.widget.Toast;

import com.danimahardhika.cafebar.CafeBar;
import com.danimahardhika.cafebar.CafeBarTheme;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Calendar myCalendar = Calendar.getInstance();
    Spinner spinTeamOne;
    Spinner spinTeamTwo;
    Button createGame;
    EditText name;
    EditText dateText;
    Team teamOne;
    Team teamTwo;
    FragmentManager fm;
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };


    private OnFragmentInteractionListener mListener;

    public CreateGameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateGameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateGameFragment newInstance(String param1, String param2) {
        CreateGameFragment fragment = new CreateGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_game, container, false);
        MainActivity.fab.hide();
        // Inflate the layout for this fragment
        spinTeamOne = view.findViewById(R.id.firstTeamSpinner);
        spinTeamTwo = view.findViewById(R.id.secondTeamSpinner);
        name = view.findViewById(R.id.gameName);
        dateText = view.findViewById(R.id.gameDate);
        DatabaseHandler db = new DatabaseHandler(getContext());
        final ArrayList<Team> list = db.getAllTeams();
        //Link the ArrayList with the spinner
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinTeamOne.setAdapter(adapter);
        spinTeamTwo.setAdapter(adapter);

        spinTeamOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teamOne = list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinTeamTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teamTwo = list.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createGame = view.findViewById(R.id.submitGameButton);

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().length() != 0 &&dateText.getText().length() != 0
                        && teamOne.getName().length() != 0 && teamTwo.getName().length() != 0) {
                    //Grab an instance of the database
                    DatabaseHandler db = new DatabaseHandler(getContext());
                    //Add the location to the database
                    db.addGame(name.getText().toString(),
                            dateText.getText().toString(),
                            teamOne, teamTwo);
                    //Close the database
                    db.close();
                    //Hide keyboard
                    hideKeyboard();

                    //Grab the fragment manager and move us back a page/fragment
                    fm = getActivity().getSupportFragmentManager();


                    fm.popBackStack();
                }else{
                    CafeBar.builder(getContext())
                            .content("Please Fill Out All Fields")
                            .floating(true)
                            .fitSystemWindow()
                            .theme(CafeBarTheme.Custom(Color.parseColor("#ffde59")))
                            .show();
                }
            }
        });

        return view;
    }

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

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(myCalendar.getTime()));
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
