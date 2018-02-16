package com.prices.products.productsprices;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import model.Aluno;

public class ListCellAlunos extends BaseAdapter {

    public List<Aluno> alunos;
    private final Activity context;

    public ListCellAlunos(Activity context, List<Aluno> list) {
        this.context = context;
        this.alunos = list;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.nome);
        TextView txtId = (TextView) rowView.findViewById(R.id.cpf);

        txtTitle.setText(alunos.get(position).getNome());
        txtId.setText(alunos.get(position).getCpf());

        return rowView;
    }
}
