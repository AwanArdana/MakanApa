package com.nefele.makanapa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterCabang extends ArrayAdapter<ItemCabang> {
    private final ArrayList<ItemCabang> itemCabangs;

    public AdapterCabang(Context context, ArrayList<ItemCabang> itemCabangs){
        super(context, R.layout.frag_list_cabang, itemCabangs);
        this.itemCabangs = itemCabangs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext()); // Use getContext()
            view = inflater.inflate(R.layout.frag_list_cabang, parent, false);
        } else {
            view = convertView;
        }
        AppCompatImageView img_cover = (AppCompatImageView) view.findViewById(R.id.img_cover);
        TextView txt_nama = (TextView) view.findViewById(R.id.txt_nama);
        TextView txt_kategori = (TextView) view.findViewById(R.id.txt_kategori);

        txt_nama.setText(itemCabangs.get(position).getNama());
        txt_kategori.setText(itemCabangs.get(position).getKategori());
        if (itemCabangs.get(position).getCover().equals("")) {
            img_cover.setImageResource(R.drawable.image_svgrepo_com);
        }else {
//Picasso.get()
//        .load(itemCabangs.get(position).getCover()) // Replace with the actual URL
//        .placeholder(R.drawable.placeholder_image) // Optional: Placeholder image while loading
//        .error(R.drawable.error_image) // Optional: Image to show if loading fails
//        .into(img_cover);
            Picasso.get()
                    .load("https://awanapp.000webhostapp.com/makanapa/cover/"+itemCabangs.get(position).getCover())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.image_svgrepo_com)
                    .error(R.drawable.image_svgrepo_com)
                    .into(img_cover);
        }

        return view;
    }
}
