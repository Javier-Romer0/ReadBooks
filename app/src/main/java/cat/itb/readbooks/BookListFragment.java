package cat.itb.readbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BookListFragment extends Fragment {
    RecyclerView recyclerView;
    BookViewModel bookViewModel;
    BookAdapter adapter;
    FloatingActionButton bt_add;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.book_list_fragment, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(BookViewModel.bookList);
        recyclerView.setAdapter(adapter);

        bt_add = v.findViewById(R.id.addButton);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections listToNewBook = BookListFragmentDirections.actionBookListFragmentToBookDataFragmentAdd();
                Navigation.findNavController(v).navigate(listToNewBook);
            }
        });

        return v;
    }

}
