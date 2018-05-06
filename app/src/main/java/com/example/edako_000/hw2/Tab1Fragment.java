package com.example.edako_000.hw2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private TextView result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);


        result = (TextView) view.findViewById(R.id.result);
        getWebsite();
        return view;
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://ybu.edu.tr/sks/").get();
                    String title = doc.title();
                    Elements rows = doc.select("font");

                    builder.append("\n\t\t\t\tFOOD-LIST");
                    builder.append("\n\n").append("\t\t\t\t").append(rows.get(0).text()).append("\n\n");
                    for (int i=1;i<rows.size();i++){
                        builder.append("\n\n").append("\t\t\t\t").append(rows.get(i).text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

}