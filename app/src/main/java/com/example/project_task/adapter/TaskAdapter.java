package com.example.project_task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Tarefa;
import com.example.project_task.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Tarefa tarefa);
    }

    private List<Tarefa> tarefaList;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public TaskAdapter(Context context, List<Tarefa> tarefaList, OnItemClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.tarefaList = tarefaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_tarefa, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Tarefa currentTarefa = tarefaList.get(position);
        holder.tvNomeTarefa.setText(currentTarefa.getNome());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.tvDataFinalTarefa.setText(sdf.format(currentTarefa.getDataEntrega()));

        holder.itemView.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (listener != null && currentPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(tarefaList.get(currentPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tarefaList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeTarefa, tvDataFinalTarefa;

        TaskViewHolder(View itemView) {
            super(itemView);
            tvNomeTarefa = itemView.findViewById(R.id.tvNomeTarefa);
            tvDataFinalTarefa = itemView.findViewById(R.id.tvDataFinalTarefa);
        }
    }

    public void setTasks(List<Tarefa> tarefaList) {
        this.tarefaList = tarefaList;
        notifyDataSetChanged();
    }

}
