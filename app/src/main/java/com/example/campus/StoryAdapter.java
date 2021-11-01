package com.example.campus;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryViewHolder>{
    Context mContext;
    List<Stories> mData;
    SharedPreferences pref;
    Dialog dialog;
    DBHelper databaseHelper;

    public StoryAdapter(Context mContext, List<Stories> mData) {
        this.mContext = mContext;
        this.mData = mData;
        pref = mContext.getSharedPreferences("mypref", Context.MODE_PRIVATE);
    }


    @NonNull
    @Override
    public StoryAdapter.StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.story_card_layout, parent, false);
        StoryAdapter.StoryViewHolder storyViewHolder = new StoryAdapter.StoryViewHolder(v);
        return storyViewHolder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final StoryViewHolder holder, final int position) {
        holder.name.setText(mData.get(position).getName());
        holder.company.setText(mData.get(position).getCompany());
        holder.pack.setText("Pack. : " + mData.get(position).getPack());
//        holder.story.setText("Experience : " + mData.get(position).getStory());
        holder.yop.setText("Year. : " + mData.get(position).getYop());

        if(pref.getString("KEY_USER",null).equals("admin")){
            holder.v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder abuild = new AlertDialog.Builder(mContext);
                    abuild.setMessage("Do You Want To Delete This Item ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DBHelper db = new DBHelper(mContext);
                                    db.deleteStory(mData.get(position).getId());
                                    Intent intent = new Intent(mContext, checkHome.class);
                                    intent.putExtra("load", 0);
                                    mContext.startActivity(new Intent(intent));
                                    Toast.makeText(mContext,"Story Deleted!",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(" No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert = abuild.create();
                    alert.setTitle("Alert !");
                    alert.show();
                    return true;
                }
            });
        }
        final Dialog dialog = new Dialog(mContext);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.story_popup);
                TextView name = dialog.findViewById(R.id.storyPopUpName);
                TextView comp = dialog.findViewById(R.id.storyPopUpComp);
                TextView pack = dialog.findViewById(R.id.storyPopUpPack);
                TextView yop = dialog.findViewById(R.id.storyPopUpYear);
                TextView story = dialog.findViewById(R.id.storyPopUpDesc);

                name.setText(holder.name.getText());
                comp.setText(holder.company.getText());
                pack.setText(holder.pack.getText());
                yop.setText(holder.yop.getText());
                story.setText(mData.get(position).getStory());

                TextView txtClose = dialog.findViewById(R.id.txt_close_2);
                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder {
        TextView name, company, pack, story, yop;
        View v;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.storyLinearLayout);
            name = itemView.findViewById(R.id.nameTextView);
            company = itemView.findViewById(R.id.companyTextView);
            pack = itemView.findViewById(R.id.packageTextView);
            yop = itemView.findViewById(R.id.yearSTextView);
        }
    }
}
