package wc.productions.gamekeeper;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link viewPagerItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link viewPagerItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class viewPagerItemFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    FragmentManager fm;
    private Game game;
    private Team team1;
    private Team team2;

    // TODO: Rename and change types of parameters
    private Game mParam1;


    private OnFragmentInteractionListener mListener;

    public viewPagerItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment viewPagerItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static viewPagerItemFragment newInstance(Parcelable param1) {
        viewPagerItemFragment fragment = new viewPagerItemFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,param1);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = getArguments().getParcelable(ARG_PARAM1);
            System.out.println(game.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager_item, container, false);
        //Sets the Text View to mParam1

            DatabaseHandler db = new DatabaseHandler(getContext());

        team1 = db.getTeam(game.getTeam1());
        team2 = db.getTeam(game.getTeam2());


        TextView teamOne = (TextView) view.findViewById(R.id.teamOne);
        teamOne.setText(team1.getName());


        TextView teamTwo = (TextView) view.findViewById(R.id.teamTwo);
        teamTwo.setText(team2.getName());


        TextView matchDate = (TextView) view.findViewById(R.id.date);
        matchDate.setText(game.getDate());

        TextView gameName = view.findViewById(R.id.gameName);
        gameName.setText(game.getName());

        ImageView logoTeam1 = view.findViewById(R.id.logoTeam1);
        ImageView logoTeam2 = view.findViewById(R.id.logoTeam2);
        Logo logo1 = db.getLogo(team1.getId());
        Logo logo2 = db.getLogo(team2.getId());

            Picasso.with(getContext()).load(new File(logo1.getResource()))
                    .placeholder(R.drawable.placeholder)
                    .into(logoTeam1);

            Picasso.with(getContext()).load(new File(logo2.getResource()))
                    .placeholder(R.drawable.placeholder)
                    .into(logoTeam2);



        ImageView calendarButton = view.findViewById(R.id.createCalendarEvent);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE, game.getName());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, team1.getName() + " VS " + team2.getName());
                intent.putExtra(CalendarContract.Events.EVENT_COLOR, "");
                if (intent.resolveActivity(getActivity().getPackageManager())!= null){
                    startActivity(intent);
                }
            }
        });
        //Passes the game to the edit fragment
        ImageView editButton = view.findViewById(R.id.editGameButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getFragmentManager();
                UpdateGameFragment td = UpdateGameFragment.newInstance(game);
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_content, td);
                ft.commit();
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
}
