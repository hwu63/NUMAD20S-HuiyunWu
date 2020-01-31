package whyellow.tk.numad20s_huiyunwu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
        private ArrayList<Link> links;
        private Context context;

        public LinkAdapter(ArrayList<Link> links) {
            this.links = links;
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
            final Link link = links.get(position);
            holder.display_name.setText(link.get_name());
            holder.display_url.setText(link.get_url());
        }

        @Override
        public int getItemCount() {
            return links.size();
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
