package cat.itb.readbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;

public class BookDataFragment extends Fragment {
    EditText edittext_title, edittext_author;
    RadioGroup rGroup_status;
    RadioButton rButton_want, rButton_reading, rButton_read;
    RatingBar ratingBar;
    TextView textview_ratingBar_title;
    Button rButton_confirm;

    private Book book;
    private boolean updatingData = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_data_fragment, container, false);

        edittext_title = v.findViewById(R.id.edittext_datatitle);
        edittext_author = v.findViewById(R.id.edittext_dataauthor);
        rGroup_status = v.findViewById(R.id.radiogroup_datastatus);
        rButton_want = v.findViewById(R.id.radiobutton_want);
        rButton_reading = v.findViewById(R.id.radiobutton_reading);
        rButton_read = v.findViewById(R.id.radiobutton_read);
        ratingBar = v.findViewById(R.id.ratingbar_datarate);
        textview_ratingBar_title = v.findViewById(R.id.textview_datarate);
        rButton_confirm = v.findViewById(R.id.button_confirm);

        getData();
        showView();

        rButton_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edittext_title.getText().toString();
                String author = edittext_author.getText().toString();
                int status = 0;
                switch (rGroup_status.getCheckedRadioButtonId()){
                    case R.id.radiobutton_want: status = 1;
                        break;
                    case R.id.radiobutton_reading: status = 2;
                        break;
                    case R.id.radiobutton_read: status = 3;
                        break;
                }
                double rate = ratingBar.getRating();
                if (!title.isEmpty() && !author.isEmpty() && (status == 1 || status == 2 || status == 3)){
                    if (updatingData) {
                        book.setTitle(title);
                        book.setAuthor(author);
                        book.setStatus(status);
                        book.setRate(rate);
                        Navigation.findNavController(v).navigate(R.id.action_updateBook_to_bookListFragment);
                    }else {
                        Book addBook = new Book(title, author, status, rate);
                        BookViewModel.bookList.add(addBook);
                        Navigation.findNavController(v).navigate(R.id.action_addBook_to_bookListFragment);
                    }
                }
            }
        });

        rGroup_status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radiobutton_read){
                    ratingBarVisible();
                }else {
                    ratingBarInvisible();
                }
            }
        });

        return v;
    }

    public void getData(){
        if (getArguments() != null && !getArguments().isEmpty()) {
            updatingData = true;
            book = getArguments().getParcelable("book");
        }
    }

    public void showView(){
        if (updatingData){
            edittext_title.setText(book.getTitle());
            edittext_author.setText(book.getAuthor());
            switch (book.getStatus()){
                case 1:
                    rButton_want.setChecked(true);
                    break;
                case 2:
                    rButton_reading.setChecked(true);
                    break;
                case 3:
                    rButton_read.setChecked(true);
                    break;
            }
            if (book.getStatus() != 3) ratingBarInvisible();
            else ratingBarVisible();
            ratingBar.setRating((float)book.getRate());
            rButton_confirm.setText(R.string.bt_confirm_update);
        }else {
            edittext_title.setText("");
            edittext_author.setText("");
            rButton_want.setChecked(false);
            rButton_reading.setChecked(false);
            rButton_read.setChecked(false);
            rButton_confirm.setText(R.string.bt_confirm_add);
            ratingBarInvisible();
        }
    }

    public void ratingBarInvisible(){
        textview_ratingBar_title.setVisibility(View.INVISIBLE);
        ratingBar.setVisibility(View.INVISIBLE);
    }

    public void ratingBarVisible(){
        textview_ratingBar_title.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);
    }
}
