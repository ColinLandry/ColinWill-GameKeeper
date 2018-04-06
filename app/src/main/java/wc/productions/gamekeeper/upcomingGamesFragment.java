package wc.productions.gamekeeper;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link upcomingGamesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link upcomingGamesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class upcomingGamesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Team team;


    private OnFragmentInteractionListener mListener;

    public upcomingGamesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upcomingGamesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static upcomingGamesFragment newInstance(String param1, String param2) {
        upcomingGamesFragment fragment = new upcomingGamesFragment();
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
    /**
     * Detach the view and return it at the end
     * Grab the view pager
     * Set the Custom adapter
     * Set a page Transformer
     * Create the left and right chevron buttons
     * Set on click listeners which check the location of the pager
     * This allows us to create an infinite scroll.
     * Activate the fab button and change it's function to allow them to schedule games
     */

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_games, container, false);
        fm = getActivity().getSupportFragmentManager();
        MainActivity.fab.setImageResource(R.drawable.ic_add_black_24dp);
        MainActivity.fab.show();
        MainActivity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.main_content, new CreateGameFragment());
                ft.commit();
            }
        });

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.gamesViewPager);
        //Set the adapter
        final CustomPagerAdapter adapter = new CustomPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //Setting the page transformer
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        return view;

    }

    /**
     * The Custom Page Adapter which extends the FragmentAdapter
     * Contains a switch statement which changes based on pager location
     */
    public class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Requires param1 String and param2 int
            DatabaseHandler db = new DatabaseHandler(getContext());

            final ArrayList<Team> test = db.getAllTeams();
            db.addGame("test","test",test.get(0),test.get(1));
            db.addGame("test","test",test.get(0),test.get(1));
            db.addGame("test","test",test.get(0),test.get(1));
            db.addGame("test","test",test.get(0),test.get(1));
            db.addGame("test","test",test.get(0),test.get(1));
            db.addGame("test","test",test.get(0),test.get(1));
            db.addGame("test","test",test.get(0),test.get(1));
            final ArrayList<Game> list = db.getAllGames();

            switch (position){
                case 0:
                    team = db.getTeam(list.get(position).getTeam1());
                    return viewPagerItemFragment.newInstance (team.getName().toString(),team.getName().toString(),list.get(position).getDate());
                case 1:
                    team = db.getTeam(list.get(position).getTeam1());
                    return viewPagerItemFragment.newInstance("Windsor Wolves","Windsor Sharks","02-19-2019");
                case 2:
                    team = db.getTeam(list.get(position).getTeam1());
                    return viewPagerItemFragment.newInstance("Windsor Wolves","Windsor Sharks","02-19-2019");
                case 3:
                    team = db.getTeam(list.get(position).getTeam1());
                    return viewPagerItemFragment.newInstance("Windsor Wolves","Windsor Sharks","02-19-2019");
                case 4:
                    team = db.getTeam(list.get(position).getTeam1());
                    return viewPagerItemFragment.newInstance("Windsor Wolves","Windsor Sharks","02-19-2019");
                default:
                    return viewPagerItemFragment.newInstance("Windsor Wolves","Windsor Sharks","02-19-2019");
            }

        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public  class DepthPageTransformer implements ViewPager.PageTransformer{
        private static final float MIN_SCALE = 0.75f;

        /**
         * transform page method
         * @param view Required view needed to apply the transformation
         * @param position current view pager position
         */
        public void transformPage(View view, float position){
            int pageWidth = view.getWidth();

            if (position < -1) {
                view.setAlpha(0);
            }else if (position <= 0){
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            }else if (position <= 1){
                view.setAlpha(1 - position);

                view.setTranslationX(pageWidth * -position);

                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                        * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }else {
                view.setAlpha(0);
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