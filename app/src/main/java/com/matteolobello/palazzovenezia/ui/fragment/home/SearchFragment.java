package com.matteolobello.palazzovenezia.ui.fragment.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.matteolobello.palazzovenezia.R;
import com.matteolobello.palazzovenezia.data.asset.AssetPaintingsGenerator;
import com.matteolobello.palazzovenezia.ui.adapter.recyclerview.SearchRecyclerViewAdapter;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends Fragment {

    private View mSearchContainer;
    private EditText mSearchEditText;
    private ImageView mSearchClearImageView;
    private RecyclerView mSearchRecyclerView;
    private View mEmptyResultView;

    private SearchRecyclerViewAdapter mSearchAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSearchContainer = view.findViewById(R.id.search_container);
        mSearchEditText = view.findViewById(R.id.search_view);
        mSearchClearImageView = view.findViewById(R.id.search_clear);
        mSearchRecyclerView = view.findViewById(R.id.search_rv);
        mEmptyResultView = view.findViewById(R.id.empty_result_wrapper);

        setupSearchView();
        setupRecyclerView();
    }

    private void setupSearchView() {
        try {
            // Set cursor colour to white
            // http://stackoverflow.com/a/26544231/1692770
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            field.set(mSearchEditText, R.drawable.white_cursor);
        } catch (Exception ignored) {
        }

        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mSearchAdapter.searchPainting(s.toString()) > 0) {
                    mSearchRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyResultView.setVisibility(View.GONE);
                } else {
                    mSearchRecyclerView.setVisibility(View.GONE);
                    mEmptyResultView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSearchClearImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchEditText.setText("");

                mSearchRecyclerView.setVisibility(View.GONE);
                mEmptyResultView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupRecyclerView() {
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSearchAdapter = new SearchRecyclerViewAdapter(AssetPaintingsGenerator.generatePaintings(getContext()));
        mSearchRecyclerView.setAdapter(mSearchAdapter);
    }
}