package com.example.abodi.wshalghada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

public class postAdpater extends RecyclerView.Adapter<postAdpater.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton image, edit, delete;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageButton) itemView.findViewById(R.id.recipeImage);
            edit = (ImageButton) itemView.findViewById(R.id.editButton);
            delete = (ImageButton) itemView.findViewById(R.id.deleteButton);
        }
    }

    private Context context;
    private List<post> postList;

    public postAdpater(Context c, List<post> p){
        this.context=c;
        postList=p;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(postAdpater.ViewHolder holder, int position){
        post p = postList.get(position);
        holder.image.setImageBitmap(p.getImage());
        holder.edit.setBackgroundResource(p.getEdit());
        holder.delete.setBackgroundResource(p.getDelete());
    }

    public int getItemCount(){
        return postList.size();
    }
}
