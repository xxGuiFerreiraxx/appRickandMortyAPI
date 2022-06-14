package com.example.rickandmorty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private final int layout;
    private final Context context;
    List<Personagem> personagemList;

    public ListViewAdapter(Context context, int layout, List<Personagem> personagem) {
        this.layout = layout;
        this.context = context;
        this.personagemList = personagem;
    }

    @Override
    public int getCount() {
        return personagemList.size();
    }

    @Override
    public Object getItem(int position) {
        return personagemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txt_name,txt_status;
        LinearLayout container_iten;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.container_iten= row.findViewById(R.id.container_iten);
            holder.txt_name = row.findViewById(R.id.name);
            holder.txt_status = row.findViewById(R.id.status);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Personagem personagem = personagemList.get(position);

        holder.container_iten.setOnClickListener(v -> {
            Intent intent = new Intent(context, Detalhes.class);
            intent.putExtra("id", personagem.getId());
            context.startActivity(intent);
        });

        holder.txt_name.setText(personagem.getName());
        holder.txt_status.setText(personagem.getStatus());


        return row;
    }
}
