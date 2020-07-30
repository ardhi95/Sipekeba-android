package co.id.sipekeba.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.colindodd.gradientlayout.GradientRelativeLayout;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import co.id.sipekeba.Activity.Layanan.DetailLayananActivity;
import co.id.sipekeba.Models.Layanan;
import co.id.sipekeba.R;

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.LayananViewHolder> {

    private boolean isLoading;
    private AppCompatActivity activity;
//    private Context context;

    private List<Layanan> list;

    public LayananAdapter(
            AppCompatActivity activity,
            List<Layanan> list)
    {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public LayananViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layanan, parent, false);
        return new LayananViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LayananViewHolder holder, int position) {
        final Layanan layanan       =   list.get(position);

        if(layanan == null){
            return;
        }

        holder.txtNama.setText(layanan.getJenisBarang());
        holder.txtDesc.setText(layanan.getKeterangan());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle   =   new Bundle();
                bundle.putString("id", layanan.getId());
                activity.startActivity(new Intent(activity, DetailLayananActivity.class).putExtras(bundle));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class LayananViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtDesc, txtDate;
        private GradientRelativeLayout card;

        public LayananViewHolder(View itemView) {
            super(itemView);
            txtNama     = (TextView) itemView.findViewById(R.id.textViewSub3Title);
            txtDesc     = (TextView) itemView.findViewById(R.id.txtDesc);
//            txtDate     = (TextView) itemView.findViewById(R.id.txt_add);
            card        = (GradientRelativeLayout) itemView.findViewById(R.id.cardLayanan);

        }
    }

    public void setLoaded() {
        isLoading = false;
    }
}
