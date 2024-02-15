package com.legendarysoftwares.livetv;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import java.lang.String;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ChannelAdapter extends FirebaseRecyclerAdapter<channel_model, ChannelAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChannelAdapter(@NonNull FirebaseRecyclerOptions<channel_model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull channel_model model) {


            holder.name.setText(model.getName());
            holder.category.setText(model.getCategory());
            Glide.with(holder.image.getContext())
                    .load(model.getIcon())
                    .into(holder.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(view.getContext(), videoplayer.class);
                    intent.putExtra("Link",model.getLink());
                    view.getContext().startActivity(intent);
            }
        });

        holder.card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //holder.card.setEnabled(false);
                // Initializing the popup menu and giving the reference as current context
                PopupMenu popupMenu2 = new PopupMenu(v.getContext(), holder.card);
                // Inflating popup menu from popup_menu.xml file
                popupMenu2.getMenuInflater().inflate(R.menu.menu_for_every_item, popupMenu2.getMenu());

                popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id=item.getItemId();
                        if (id==R.id.play){
                            Intent intent=new Intent(v.getContext(), videoplayer.class);
                            intent.putExtra("Link",model.getLink());
                            v.getContext().startActivity(intent);
                            return true;
                        } else if (id==R.id.save){
                            Toast toast = Toast.makeText(v.getContext(), "Channel Saved!", Toast.LENGTH_SHORT);
                            toast.show();
                            return true;
                        }
                        return true;
                    }
                });
                popupMenu2.show();
                return true;
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView image;
        TextView name, category;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R .id.cardview);
            image = itemView.findViewById(R .id.channel_icon);
            name = itemView.findViewById(R .id.channel_name);
            category = itemView.findViewById(R .id.channel_categories);


        }
    }

}
