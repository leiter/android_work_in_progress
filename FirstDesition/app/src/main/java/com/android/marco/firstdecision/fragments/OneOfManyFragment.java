package com.android.marco.firstdecision.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.marco.firstdecision.R;
import com.android.marco.firstdecision.activities.MainActivity;
import com.android.marco.firstdecision.adapters.ListThingsAdapter;
import com.android.marco.firstdecision.utils.AnimationUtil;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class OneOfManyFragment extends Fragment {

    private boolean listIsShowing = true;
    public OneOfManyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity a = (MainActivity) getActivity();
        ListThingsAdapter adapter = new ListThingsAdapter(a,
                R.id.lv_oneofmany_things, a.listData, true);
        View rootView = inflater.inflate(R.layout.fragment_one_of_many, container, false);
        final ListView listView = (ListView) rootView.findViewById(R.id.lv_oneofmany_things);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_one_of_many, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_show_list) {
            View resultBtn = getActivity().findViewById(R.id.btn_ma_result);
            if (resultBtn.getVisibility() == View.VISIBLE && !listIsShowing) {
                resultBtn.setVisibility(View.INVISIBLE);
            } else if (resultBtn.getVisibility() == View.INVISIBLE && listIsShowing) {  // && notplaying
                resultBtn.setVisibility(View.VISIBLE);
            }
            slideList();
            return true;
        }

//        if (item.getItemId() == R.id.action_flip_now) {
//            interactionListener.onInteraction(InteractionListener.Interaction.IA__GO_FLIP);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void slideList() {
        final ViewGroup listView = ((ViewGroup) getActivity().findViewById(R.id.container_rel));
        AnimationUtil.slideInOut(getActivity(),
                listView, listIsShowing);
        final int viewState;
        if (listIsShowing) {
            listView.setEnabled(false);
            viewState = View.GONE;
        } else {
            listView.setEnabled(true);
            viewState = View.VISIBLE;
        }
//        listView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                listView.clearAnimation();
//                listView.setVisibility(viewState);
//            }
//        }, 810);
        listIsShowing = !listIsShowing;
    }



}
