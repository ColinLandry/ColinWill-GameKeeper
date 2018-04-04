package wc.productions.gamekeeper;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
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
 * {@link TeamManagementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamManagementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamManagementFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TeamManagementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamManagementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamManagementFragment newInstance(String param1, String param2) {
        TeamManagementFragment fragment = new TeamManagementFragment();
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
        View view = inflater.inflate(R.layout.fragment_team_management, container, false);

        fm = getActivity().getSupportFragmentManager();
        MainActivity.fab.setImageResource(R.drawable.ic_add_black_24dp);
        MainActivity.fab.show();
        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(getContext());
                Team test = new Team("Test team", 0);
                db.addTeam(test);
                db.close();

                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.content, new TeamManagementFragment());
                ft.commit();
            }
        });

        RecyclerView list = view.findViewById(R.id.teamRecyclerList);

        //Grab from database
        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<Team> teamList = db.getAllTeams();
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
        CustomRecyclerViewAdapter adapter = new CustomRecyclerViewAdapter(getContext(), teamList);
        list.setAdapter(adapter);

        return view;
    }

    public class CustomRecyclerViewAdapter extends RecyclerView.Adapter {
        private ArrayList<Team> teams;
        private Context context;

        public CustomRecyclerViewAdapter(Context c, ArrayList<Team> teams){
            this.teams = teams;
            this.context = c;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item_view, null);
            final CustomViewHolder viewHolder = new CustomViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    TeamDetailsFragment td = TeamDetailsFragment.newInstance(teams.get(position));
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.content, td);
                    ft.commit();
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int location = viewHolder.getAdapterPosition();

                    //Alert to confirm
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete " + teams.get(location).getName() + "?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Grab an instance of the database
                                    DatabaseHandler db = new DatabaseHandler(getContext());
                                    //Remove the team from the database
                                    int team = teams.get(location).getId();
                                    db.deleteTeam(team);
                                    //Close the database
                                    db.close();

                                    //Refresh
                                    teams.remove(location);
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
            Team team = teams.get(position);
            ((CustomViewHolder) holder).teamName.setText(team.getName());
            ((CustomViewHolder) holder).coachName.setText("" + team.getCoach());
            setAnimation(holder.itemView);

        }

        /**
         * Animation on each item
         */
        public void setAnimation(View view) {

            AlphaAnimation anim2 = new AlphaAnimation(0.0f, 1.0f);
            anim2.setDuration(700);

            ScaleAnimation anim3 = new ScaleAnimation(0f, 1.0f, 0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim3.setDuration(700);

            AnimationSet animSet = new AnimationSet(true);
            animSet.addAnimation(anim3);
            animSet.addAnimation(anim2);

            view.startAnimation(animSet);

        }

        @Override
        public int getItemCount() {
            return teams.size();
        }

        class CustomViewHolder extends RecyclerView.ViewHolder{
            protected TextView teamName;
            protected TextView coachName;

            /**
             * Set the holder items to the corresponding view locations
             * @param view parent view
             */
            public CustomViewHolder(View view){
                super(view);
                this.teamName = view.findViewById(R.id.teamName);
                this.coachName = view.findViewById(R.id.coachName);
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
