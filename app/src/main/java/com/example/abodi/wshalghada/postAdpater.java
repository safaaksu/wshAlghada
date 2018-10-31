package com.example.abodi.wshalghada;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class postAdpater extends RecyclerView.Adapter<postAdpater.ViewHolder> {

    private static final String TAG = "postAdpater";
    // private Bitmap imageS;
    // private int editS, deleteS;
    // private String nameS;
    private Context context;
    private List<post> postList;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageButton image, edit, delete;
        TextView name;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageButton) itemView.findViewById(R.id.recipeImage);
            name = (TextView) itemView.findViewById(R.id.recipeName);
            edit = (ImageButton) itemView.findViewById(R.id.editButton);
            delete = (ImageButton) itemView.findViewById(R.id.deleteButton);
        }

        @Override
        public void onClick(View v) {

        }
    }


    public postAdpater(Context c, List<post> p){
        context=c;
        postList=p;
    }

    /*
    public postAdpater(Context context, Bitmap Image, int Edit, int Delete, String Name) {
        imageS = Image;
        editS = Edit;
        deleteS = Delete;
        nameS = Name;
        this.context = context;
    }*/


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        Log.d(TAG, "onBindViewHolder: called.");

        post p = postList.get(position);
        holder.image.setImageBitmap(p.getImage());
        holder.name.setText((CharSequence) p.getName());
        holder.edit.setBackgroundResource(p.getEdit());
        holder.delete.setBackgroundResource(p.getDelete());

       /* holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + (postList.get(position)).getImage());
                Intent intent = new Intent(this, DisplayRecipe.class);
                startActivity(intent);
            }
        });*/
    }


    public int getItemCount(){
        return postList.size();
    }


}
