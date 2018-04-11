package wc.productions.gamekeeper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CreateTeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateTeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int IMAGE_INTENT_LABEL = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText coachNameInput;
    EditText teamNameInput;
    LinearLayout imageLayout;

    private String logoLocation;
    private int picID;

    private OnFragmentInteractionListener mListener;

    public CreateTeamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateTeamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateTeamFragment newInstance(String param1, String param2) {
        CreateTeamFragment fragment = new CreateTeamFragment();
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

    FragmentManager fm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_team, container, false);
        MainActivity.fab.hide();
        coachNameInput = (EditText) view.findViewById(R.id.teamCoachInput);
        teamNameInput = (EditText) view.findViewById(R.id.teamNameInput);
        Button submit = (Button) view.findViewById(R.id.submitTeam);
        ImageView addLogo = view.findViewById(R.id.addImageButton);
        imageLayout = view.findViewById(R.id.imageLayout);

        addLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                File picture = null;
//                try {
//                    picture = createTempImageFile();
//                }
//                catch(IOException e){
//                    e.printStackTrace();
//                }
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
//                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picture));
                if(i.resolveActivity(getActivity().getPackageManager())!= null) {
                    startActivityForResult(Intent.createChooser(i, "Select Picture"), IMAGE_INTENT_LABEL);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if all values filled
                if (teamNameInput.getText().length() != 0 && coachNameInput.getText().length() != 0){
                    //Create the team
                    Team team = new Team(teamNameInput.getText().toString(), coachNameInput.getText().toString());

                    //Grab an instance of the database
                    DatabaseHandler db = new DatabaseHandler(getContext());

                    //Add the team to the database
                    db.addTeam(team);

                    //Connect image logo to team
                    db.addTeamLogo(picID, team.getId());

                    //Close the database
                    db.close();

                    hideKeyboard();

                    //Grab the fragment manager and move us back a page/fragment
                    fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();

                    //If not make toast popup
                }else{
                    Toast.makeText(getContext(), "Please make sure to fill out all fields", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_INTENT_LABEL && resultCode == RESULT_OK){
            //Show the photo
            logoLocation = data.getData().getPath();
            Bitmap image = BitmapFactory.decodeFile(logoLocation);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageBitmap(image);
            imageLayout.addView(imageView);

            //Add to db
            DatabaseHandler db = new DatabaseHandler(getContext());
            picID = db.addLogo(new Logo(logoLocation));
            if(picID != -1){
                Toast.makeText(getContext(),
                        "Photo Added Successfully",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getContext(),
                    "Photo Not Added",
                    Toast.LENGTH_SHORT).show();
        }
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

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    File createTempImageFile() throws IOException {
        //Create the name
        String fileName = "gamekeeper_" + System.currentTimeMillis();
        //Grab the directory
        File directory =
                Environment.
                        getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES);
        File picture  = File.createTempFile(fileName, ".jpg", directory);
        logoLocation = picture.getAbsolutePath();
        return picture;
    }
}
