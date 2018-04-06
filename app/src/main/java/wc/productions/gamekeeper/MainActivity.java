package wc.productions.gamekeeper;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        upcomingGamesFragment.OnFragmentInteractionListener,
        viewPagerItemFragment.OnFragmentInteractionListener,
        CreateGameFragment.OnFragmentInteractionListener,
        TeamManagementFragment.OnFragmentInteractionListener,
        TeamDetailsFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        CreateTeamFragment.OnFragmentInteractionListener,
        CreatePlayerFragment.OnFragmentInteractionListener{


    FragmentManager fm;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        fm = getSupportFragmentManager();
        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction tr = fm.beginTransaction();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            tr.replace(R.id.main_content, new SettingsFragment());
            tr.addToBackStack(null);
            tr.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fm = getSupportFragmentManager();
        int id = item.getItemId();

        //Create fragment transaction
        android.support.v4.app.FragmentTransaction tr = fm.beginTransaction();

        if (id == R.id.nav_home) {
            // Handle the home action
//            tr.replace(R.id.content, new MainFragment());
//            tr.addToBackStack(null);
//            tr.commit();
        } else if (id == R.id.nav_games) {
           tr.replace(R.id.main_content, new upcomingGamesFragment());
            tr.addToBackStack(null);
            tr.commit();
        } else if (id == R.id.nav_management) {
            // Handle the home action
            tr.replace(R.id.main_content, new TeamManagementFragment());
            tr.addToBackStack(null);
            tr.commit();
        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




}
