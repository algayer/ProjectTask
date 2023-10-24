package com.example.project_task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Projeto;
import com.example.project_task.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    private List<Projeto> projetoList;
    private LayoutInflater inflater;

    public ProjectAdapter(Context context, List<Projeto> projetoList) {
        this.inflater = LayoutInflater.from(context);
        this.projetoList = projetoList;
    }

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
        holder.tvProjectLeaderName.setText(currentProject.getDescricao()); // Adapte conforme necessário, coloquei descrição como nome do líder

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
            tvProjectLeaderName = itemView.findViewById(R.id.tvDescProject);
            projectDate = itemView.findViewById(R.id.projectDate);
            projectIcon = itemView.findViewById(R.id.projectIcon);
        }
    }

    public void setProjects(List<Projeto> projetoList) {
        this.projetoList = projetoList;
        notifyDataSetChanged();
    }
}
