package com.example.project_task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Projeto;
import com.example.project_task.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    // Interface de callback do ItemClicker
    public interface OnItemClickListener {
        void onItemClick(Projeto projeto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<Projeto> projetoList;
    private List<Projeto> projetoListFull; // Lista completa para referência do searchBox
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public ProjectAdapter(Context context, List<Projeto> projetoList) {
        this.inflater = LayoutInflater.from(context);
        this.projetoList = projetoList;
        this.projetoListFull = new ArrayList<>(projetoList); // Cria uma cópia da lista
    }

    // construtor para passar o listener
    public ProjectAdapter(Context context, List<Projeto> projetoList, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.projetoList = projetoList;
        this.listener = listener;
    }

    public Filter getFilter() {
        return projectFilter;
    }

    private Filter projectFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Projeto> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(projetoListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Projeto item : projetoListFull) {
                    if (item.getNome().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            projetoList.clear();
            projetoList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item_project, parent, false);
        return new ProjectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
        Projeto currentProject = projetoList.get(position);
        holder.projectName.setText(currentProject.getNome());
        holder.tvProjectLeaderName.setText(currentProject.getDescricao());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (listener != null && currentPosition != RecyclerView.NO_POSITION) {
                    listener.onItemClick(projetoList.get(currentPosition));
                }
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.projectDate.setText(sdf.format(currentProject.getDataEntrega()));
    }


    @Override
    public int getItemCount() {
        return projetoList.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        TextView projectName, tvProjectLeaderName, projectDate;
        ImageView projectIcon;

        ProjectViewHolder(View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.projectName);
            tvProjectLeaderName = itemView.findViewById(R.id.tvDescProject); // hehe
            projectDate = itemView.findViewById(R.id.projectDate);
            projectIcon = itemView.findViewById(R.id.projectIcon);
        }
    }

    public void setProjects(List<Projeto> projetoList) {
        this.projetoList.clear();
        this.projetoList.addAll(projetoList);
        this.projetoListFull.clear();
        this.projetoListFull.addAll(projetoList);
        notifyDataSetChanged();
    }

    public List<Projeto> getProjects() {
        return projetoList;
    }

}
