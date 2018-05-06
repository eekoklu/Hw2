package com.example.edako_000.hw2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Tab2Fragment extends Fragment implements AdapterView.OnItemClickListener{
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> links = new ArrayList<>();

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        listView = view.findViewById(R.id.lView);
        getData task = new getData();
        task.execute("http://www.ybu.edu.tr/muhendislik/bilgisayar/");
        adapter = new ArrayAdapter<String>(getContext(),R.layout.tab2_fragment,R.id.textV,list);
        listView.setAdapter(adapter);
        System.out.print("\t\t\tANNOUNCEMENTS\n\n\n");

        listView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Uri href = Uri.parse(links.get(i));
        Intent intent = new Intent(Intent.ACTION_VIEW, href);
        startActivity(intent);

    }

    private class getData extends AsyncTask<String,Integer, ArrayList<String>> {
        final StringBuilder builder = new StringBuilder();
        @Override
        protected ArrayList<String> doInBackground(String... href) {

            String uri = href[0];
            ArrayList<String> array = new ArrayList<String>();

            try {
                Document doc = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();


                for (int i=0; i<=5; i++){
                    Elements ann = doc.select("div.cncItem a#ContentPlaceHolder1_ctl02_rpData_hplink_"+i);
                    Elements link = doc.select("div.cncItem a#ContentPlaceHolder1_ctl02_rpData_hplink_"+i+"[href]");

                    array.add(ann.text());
                    links.add(link.attr("abs:href"));

                }

            } catch (IOException e) {
                builder.append("Error : ").append(e.getMessage()).append("\n");
            }
            return array;
        }

        @Override
        protected void onPostExecute(ArrayList<String> a) {

            list.clear();
            list.addAll(a);
            adapter.notifyDataSetChanged();

        }
    }


}