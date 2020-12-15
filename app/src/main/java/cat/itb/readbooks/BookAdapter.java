package cat.itb.readbooks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_author, tv_rate;
        ImageView iv_status;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.textview_itemtitle);
            tv_author = itemView.findViewById(R.id.textview_itemauthor);
            tv_rate = itemView.findViewById(R.id.textview_itemrate);
            iv_status = itemView.findViewById(R.id.imageview_itemstatus);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavDirections listToDetailDirections = BookListFragmentDirections.actionBookListFragmentToBookDataFragmentUpdate(bookList.get(getAdapterPosition()));
                    Navigation.findNavController(v).navigate(listToDetailDirections);
                }
            });
        }

        public void bindData(Book book){
            if (book != null){
                tv_title.setText(book.getTitle());
                tv_author.setText(book.getAuthor());
                String rate = String.format("%.1f", book.getRate()) + "/5";
                tv_rate.setText(rate);
                switch (book.getStatus()){
                    case 1:
                        iv_status.setImageResource(R.drawable.want);
                        tv_rate.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        iv_status.setImageResource(R.drawable.reading);
                        tv_rate.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        iv_status.setImageResource(R.drawable.read);
                        tv_rate.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        holder.bindData(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


}
