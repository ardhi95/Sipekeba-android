package co.id.sipekeba.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import co.id.sipekeba.Activity.Laporan.AddLaporanActivity;
import co.id.sipekeba.Activity.Layanan.LayananActivity;
import co.id.sipekeba.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    CardView
            cardLayanan,
            cardLaporan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cardLayanan = root.findViewById(R.id.cardLayanan);
        cardLaporan = root.findViewById(R.id.cardLaporan);

        cardLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LayananActivity.class);
                startActivity(i);
            }
        });

        cardLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddLaporanActivity.class);
                startActivity(i);
            }
        });

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}