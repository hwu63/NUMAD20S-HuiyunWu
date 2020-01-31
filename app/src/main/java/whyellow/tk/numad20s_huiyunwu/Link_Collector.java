package whyellow.tk.numad20s_huiyunwu;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;



public class Link_Collector extends AppCompatActivity {

    ArrayList<Link> links = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter lAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(0xffffffff);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link__collector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //back button
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //the list
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        lAdapter = new LinkAdapter(links);
        recyclerView.setAdapter(lAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setup popup window
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.add_link_popup,null);
                final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                //Dim the background
                //https://stackoverflow.com/questions/3221488/blur-or-dim-background-when-android-popupwindow-active
                View container = (View) popupView.getParent();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.3f;
                wm.updateViewLayout(container, p);

                //"paste" button
                ImageButton paste = popupView.findViewById(R.id.paste);
                paste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //https://stackoverflow.com/questions/19177231/android-copy-paste-from-clipboard-manager
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        EditText link_address = popupView.findViewById(R.id.Link_address);
                        String pasteData = "";
                        if (!(clipboard.hasPrimaryClip())) {
                        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                        } else {
                            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                            pasteData = item.getText().toString();
                        }
                        link_address.setText(pasteData);
                    }
                });

                //check if the url is empty
                final Button add_button = popupView.findViewById(R.id.Add_Link);
                final EditText link_name = popupView.findViewById(R.id.Link_name);
                final EditText link_address = popupView.findViewById(R.id.Link_address);
                final TextView url_warning = popupView.findViewById(R.id.url_warning);

                TextWatcher urlWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String url = link_address.getText().toString().trim();
                        add_button.setEnabled(!url.isEmpty());
                        url_warning.setVisibility(url.isEmpty()?View.VISIBLE:View.INVISIBLE);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                };
                link_address.addTextChangedListener(urlWatcher);


                //add link button action
                add_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //add to list
                        String name = link_name.getText().toString();
                        String url = link_address.getText().toString();
                        if (name.isEmpty()){
                            name = "New Bookmark";
                        }
                        Link link = new Link(name, url);
                        add_link(link);

                        //Snackbar Message
                        CoordinatorLayout LCLayout = findViewById(R.id.link_collector);
                        Snackbar s = Snackbar.make(LCLayout, "Link Created!", Snackbar.LENGTH_SHORT);
                        setSnackbarColor(s,0xff2195f3);
                        s.show();
                        popupWindow.dismiss();
            }
        });

    }

});

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void add_link(Link link) {
        int pos;
        if(links.size() < 1){
            pos = 0;
        } else{
         pos = links.size()-1;
        }
        links.add(pos, link);
        lAdapter.notifyItemInserted(pos);
        empty_warning();
    }

    private void remove_link(int pos) {
        links.remove(pos);
        lAdapter.notifyItemRemoved(pos);
        empty_warning();
    }

    private void empty_warning(){
    //"Your list is empty!"
    TextView empty_warning = findViewById(R.id.empty_warning);
    empty_warning.setVisibility(links.size()==0?View.VISIBLE:View.INVISIBLE);
    }
}
