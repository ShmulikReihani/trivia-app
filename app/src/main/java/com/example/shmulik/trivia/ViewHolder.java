package com.example.shmulik.trivia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout root;
    public TextView txtName;
    public TextView txtScore;
    public TextView txtDate;

    public ViewHolder(View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.list_root);
        txtName = itemView.findViewById(R.id.list_title);
        txtScore = itemView.findViewById(R.id.list_desc);
        txtDate = itemView.findViewById(R.id.list_date);
    }

    public void setTxtName(String string) {
        txtName.setText(string);
    }


    public void setTxtScore(String string) {
        txtScore.setText(string);
    }

    public void setTxtDate(String string) {
        txtDate.setText(string);
    }
}
