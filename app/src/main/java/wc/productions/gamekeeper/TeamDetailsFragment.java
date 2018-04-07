package wc.productions.gamekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private Team team;

    private OnFragmentInteractionListener mListener;

    public TeamDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 team you want to see details of
     * @return A new instance of fragment TeamDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDetailsFragment newInstance(Parcelable param1) {
        TeamDetailsFragment fragment = new TeamDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_team_details, container, false);

        TextView teamName = view.findViewById(R.id.detailsTeamName);
        TextView coachName = view.findViewById(R.id.detailsCoachName);

        //Set values of title and coach name
        if(team != null){
            teamName.setText(team.getName());
            coachName.setText("Coached by: " + team.getCoach());
        }

        RecyclerView list = view.findViewById(R.id.playersRecyclerList);

        //Grab from database
        DatabaseHandler db = new DatabaseHandler(getContext());
        final ArrayList<Player> playerList = db.getAllTeamPlayers(team.getId());
        db.close();

        //Create layout manager for animations
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean supportsPredictiveItemAnimations() {
                return true;
            }
        };

        //Set manager and adapter
        list.setLayoutManager(layoutManager);
        final CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(getContext(), playerList);
        list.setAdapter(adapter);

        /**
         * Button to create a team, for now only test values input
         */
        fm = getActivity().getSupportFragmentManager();
        MainActivity.fab.hide();



        return view;
    }

    public class CustomRecyclerViewAdapter extends RecyclerView.Adapter {
        private ArrayList<Player> players;
        private Context context;

        public CustomRecyclerViewAdapter(Context c, ArrayList<Player> players){
            this.players = players;
            this.context = c;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item_view, parent, false);
            final CustomViewHolder viewHolder = new CustomViewHolder(view);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int location = viewHolder.getAdapterPosition();

                    //Alert to confirm
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete " + players.get(location).getName() + "?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Grab an instance of the database
                                    DatabaseHandler db = new DatabaseHandler(getContext());
                                    //Remove the team from the database
                                    int player = players.get(location).getId();
                                    db.deletePlayer(player);
                                    //Close the database
                                    db.close();

                                    //Refresh
                                    players.remove(location);
                                    notifyItemRemoved(location);
                                }
                            })
                            .setNegativeButton("No", null);
                    AlertDialog alert = builder.create();
                    alert.show();

                    return false;
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Player player = players.get(position);
            ((CustomViewHolder) holder).playerName.setText(player.getName());
            ((CustomViewHolder) holder).playerPhone.setText(player.getPhone() + "");
            ((CustomViewHolder) holder).playerEmail.setText(player.getEmail());
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder{
            protected TextView playerName;
            protected TextView playerPhone;
            protected TextView playerEmail;

            /**
             * Set the holder items to the corresponding view locations
             * @param view parent view
             */
            public CustomViewHolder(View view){
                super(view);
                this.playerName = view.findViewById(R.id.playerName);
                this.playerPhone = view.findViewById(R.id.playerPhone);
                this.playerEmail = view.findViewById(R.id.playerEmail);
            }

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
}
