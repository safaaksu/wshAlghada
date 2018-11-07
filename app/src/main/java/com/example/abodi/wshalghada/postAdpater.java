package com.example.abodi.wshalghada;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class postAdpater extends RecyclerView.Adapter<postAdpater.ViewHolder> {

    private Context context;
    private List<post> postList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageButton image, edit, delete;
        TextView name;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            image = (ImageButton) itemView.findViewById(R.id.recipeImage);
            name = (TextView) itemView.findViewById(R.id.recipeName);
            edit = (ImageButton) itemView.findViewById(R.id.editButton);
            delete = (ImageButton) itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }


    public postAdpater(Context c, List<post> p){
        context=c;
        postList=p;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view, mListener);
    }

    public void onBindViewHolder(ViewHolder holder, final int position){

        final post p = postList.get(position);

        holder.image.setImageBitmap(p.getImage());
        holder.name.setText((CharSequence) p.getName());
        holder.edit.setBackgroundResource(p.getEdit());
        holder.delete.setBackgroundResource(p.getDelete());

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(context, DisplayRecipe.class);
                        intent.putExtra("ID", p.getID());
                        context.startActivity(intent);
                    }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditRecipe.class);
                intent.putExtra("ID", p.getID());
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("هل متأكد تريد حذف وصفة "+p.getName())
                            .setNegativeButton("نعم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        Class.forName("com.mysql.jdbc.Driver");
                                        Connection con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
                                        Statement stmt = con.createStatement();
                                        String sql1 = "DELETE FROM recipe WHERE RecipeID= '"+p.getID()+"'";
                                        int result1 = stmt.executeUpdate(sql1);
                                        if (result1 == 1) {
                                            Intent intent = new Intent(context, AddedRecipe.class);
                                            context.startActivity(intent);
                                        }
                                        else{
                                            Toast done = Toast.makeText(context, " لم يتم الحذف بنجاح ", Toast.LENGTH_SHORT);
                                            done.show();
                                        }
                                    }
                                    catch (SQLException se){
                                        Toast errorToast = Toast.makeText(context,"يجب أن تكون متصلا ًبالانترنت"+se.getMessage() ,Toast.LENGTH_SHORT);
                                        errorToast.show();
                                    }
                                    catch (Exception e){
                                        Toast errorToast = Toast.makeText(context," "+e.getMessage() ,Toast.LENGTH_SHORT);
                                        errorToast.show();
                                    }
                                }
                            })
                             .setPositiveButton("لا",null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
        });
    }


    public int getItemCount(){
        return postList.size();
    }

}