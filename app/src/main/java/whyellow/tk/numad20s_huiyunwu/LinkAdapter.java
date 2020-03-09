package whyellow.tk.numad20s_huiyunwu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public LinkAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public LinkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex("name"));
        String url = cursor.getString(cursor.getColumnIndex("url"));

        holder.display_name.setText(name);
        holder.display_url.setText(url);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }

        cursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView display_name;
        TextView display_url;

        public ViewHolder(View v)  {
            super(v);
            v.setOnClickListener(this);
            context = v.getContext();
            display_name = v.findViewById(R.id.display_name);
            display_url = v.findViewById(R.id.display_url);
        }

        @Override
        public void onClick(View view) {
            String s = this.display_url.getText().toString();
            if (!s.startsWith("http://") && !s.startsWith("https://")){
                s = "http://" + s;
            }
            Uri url = Uri.parse(s);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, url);
            context.startActivity(launchBrowser);
        }


    }

}