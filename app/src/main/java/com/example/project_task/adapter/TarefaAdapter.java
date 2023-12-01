package com.example.project_task.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.model.Tarefa;
import com.example.project_task.R;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.ViewHolder> {
    private List<Tarefa> listaTarefas;
    private OnTarefaClickListener listener;

    public interface OnTarefaClickListener {
        void onTarefaClick(Tarefa tarefa);
    }


    public TarefaAdapter(List<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }

    public TarefaAdapter(List<Tarefa> listaTarefas, OnTarefaClickListener listener) {
        this.listaTarefas = listaTarefas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarefa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarefa tarefa = listaTarefas.get(position);
        holder.itemView.setOnClickListener(view -> listener.onTarefaClick(tarefa));

        holder.tvNomeTarefa.setText(tarefa.getNome());
        holder.tvDescricaoTarefa.setText(tarefa.getDescricao());

        // Verificando se a data e hora são null
        if (tarefa.getHorasTrabalhadas() != null) {
            holder.tvHorasTrabalhadas.setText("Horas Trabalhadas: " + tarefa.getHorasTrabalhadas());
        } else {
            holder.tvHorasTrabalhadas.setText("");
        }

        if (tarefa.getDataEntrega() != null) {
            holder.tvDataEntrega.setText("🗓️" + tarefa.getDataEntrega());
        } else {
            holder.tvDataEntrega.setText("");
        }
    }


    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeTarefa, tvDescricaoTarefa, tvHorasTrabalhadas, tvDataEntrega;

        ViewHolder(View itemView) {
            super(itemView);
            tvNomeTarefa = itemView.findViewById(R.id.tvNomeTarefa);
            tvDescricaoTarefa = itemView.findViewById(R.id.tvDescricaoTarefa);
            tvHorasTrabalhadas = itemView.findViewById(R.id.tvHorasTrabalhadas);
            tvDataEntrega = itemView.findViewById(R.id.tvDataEntrega);
            // ...
        }
    }
}
