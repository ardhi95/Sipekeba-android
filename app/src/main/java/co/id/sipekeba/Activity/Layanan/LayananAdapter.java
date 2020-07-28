package co.id.sipekeba.Activity.Layanan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import co.id.sipekeba.Models.Layanan;
import co.id.sipekeba.R;

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.LayananViewHolder> {

    private boolean isLoading;
    private Context context;
    private List<Layanan> list;

    public LayananAdapter(Context context, List<Layanan> list) {
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() : 0;
    }

    public class LayananViewHolder extends RecyclerView.ViewHolder{
        private TextView txtNama, txtDesc, txtDate;

        public LayananViewHolder(View itemView) {
            super(itemView);
            txtNama     = (TextView) itemView.findViewById(R.id.textViewSub3Title);
            txtDesc     = (TextView) itemView.findViewById(R.id.txtDesc);
//            txtDate     = (TextView) itemView.findViewById(R.id.txt_add);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }
}
