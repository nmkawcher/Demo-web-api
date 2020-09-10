package com.kawcher.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Callback;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<ApiModel>list;

    public Adapter(Context context, List<ApiModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String id= list.get(position).getId().toString();

        String albumId= list.get(position).getAlbumId().toString();
        String title=list.get(position).getTitle().toString();
        String body=list.get(position).getThumbnailUrl().toString();
        holder.albumId.setText("albumId\n"+albumId);
        holder.id.setText("Id:"+id);
        holder.title.setText("Title: "+title);
        holder.body.setText("ThumbnailUrl: "+body);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id,albumId,title,body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.idTV);
            albumId=itemView.findViewById(R.id.albumIdTV);
            title=itemView.findViewById(R.id.titleTV);
            body=itemView.findViewById(R.id.bodyTV);
        }
    }
}
