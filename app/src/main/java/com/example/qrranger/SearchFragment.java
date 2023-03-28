package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.qrranger.PlayerCollection;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment {
    EditText searchInput;
    ImageButton confirmButton;
    PlayerCollection pc = new PlayerCollection(null);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        confirmButton = view.findViewById(R.id.SearchSubmitButton);
        searchInput = view.findViewById(R.id.SearchInput);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("button clicked");

                String user_search = searchInput.getText().toString();

                // Search for similar usernames
                pc.searchSimilarUsernames(user_search, data -> {
                    if (data != null && !data.isEmpty()) {
                        System.out.println("Similar usernames found");
                        // Launch SimilarUsernamesActivity with the list of similar usernames
                        startSearchResultsActivity(data);
                    } else {
                        System.out.println("No similar usernames found");
                        // Handle no similar usernames found (e.g., show a message or a Toast)
                    }
                }, error -> {
                    // Error occurred while searching for similar usernames
                    System.out.println("Error searching for similar usernames: " + error);
                });
            }
        });

        return view;
    }

    /**
     * Starts the SimilarUsernamesActivity to display the list of similar usernames.
     *
     * @param data A list of maps containing the data of the similar users.
     */
    private void startSearchResultsActivity(List<Map<String, Object>> data) {
        Intent intent = new Intent(requireActivity(), SearchResultsActivity.class);
        intent.putExtra("similarUsernames", (Serializable) data);
        startActivity(intent);
    }
}
