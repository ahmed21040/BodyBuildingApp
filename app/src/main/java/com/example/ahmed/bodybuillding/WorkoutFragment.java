package com.example.ahmed.bodybuillding;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 21/12/2016.
 */
public class WorkoutFragment extends Fragment {
    List<Data> list;
    Context context;
    Conector conector;
    RecyclerView recyclerView;
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.workoutfragment,container,false);
        context=view.getContext();
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        conector= (Conector) getActivity();
        list=new ArrayList<>();
        recyclerView= (RecyclerView) getActivity().findViewById(R.id.rec);
        adapter=new MyAdapter(list,context);
        new LoadDataFromNetwork().execute("https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1I5Z-j6_ApERBUtWZqQOTRyFMRat2VjXCLuq0GwXzR78&sheet=bodybuilding");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder>{
        List<Data> list;
        Context context;
        public MyAdapter(List<Data> list,Context context){
            this.list=list;
            this.context=context;

        }
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(context).inflate(R.layout.row,parent,false);

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(final Holder holder, int position) {
            final Data current=list.get(position);
            holder.id.setText(current.id);
            holder.name.setText(current.name);
            holder.number.setText(current.number);
            Picasso.with(context).load(current.img1).fit().centerCrop().into(holder.img1);
            Picasso.with(context).load(current.img2).fit().centerCrop().into(holder.img2);
            holder.img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateAction(current,holder);

                }
            });
            holder.img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateAction(current,holder);

                }
            });
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    generateAction(current,holder);

                }
            });


        }
        public void generateAction(Data current,Holder holder){
            BitmapDrawable drawable1 = (BitmapDrawable) holder.img1.getDrawable();
            Bitmap Bit_img1 = drawable1.getBitmap();

            byte[] img1_byte = Convert_Img_Source.getBytes(Bit_img1);

            BitmapDrawable drawable2 = (BitmapDrawable) holder.img2.getDrawable();
            Bitmap Bit_img2 = drawable2.getBitmap();
            byte[] img2_byte = Convert_Img_Source.getBytes(Bit_img2);
            current.img1_byte = img1_byte;
            current.img2_byte = img2_byte;
            conector.MoveData(current);


        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class Holder extends RecyclerView.ViewHolder {
            TextView id,name,number;
            ImageView img1,img2;
            public Holder(View itemView) {
                super(itemView);
                id= (TextView) itemView.findViewById(R.id.id);
                name= (TextView) itemView.findViewById(R.id.name);
                number= (TextView) itemView.findViewById(R.id.number);
                img1= (ImageView) itemView.findViewById(R.id.img1);
                img2= (ImageView) itemView.findViewById(R.id.img2);

            }
        }
    }

    class LoadDataFromNetwork extends AsyncTask<String, Void, Void> {

        private ProgressDialog Dialog = new ProgressDialog(context);
        private String Content;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog.setMessage("Please wait..");
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected Void doInBackground(String... urls) {

            URL url = null;
            BufferedReader reader = null;
            try {
                url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line + "");
                }
                Content = builder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Dialog.dismiss();
           // Toast.makeText(context,Content,Toast.LENGTH_LONG).show();

            JSONObject jsonData;
            try {

                jsonData = new JSONObject(Content);
                JSONArray jsonArray = jsonData.getJSONArray("bodybuilding");

                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonOneItem = jsonArray.getJSONObject(i);
                        Data current = new Data();

                        current.name = jsonOneItem.getString("name");
                        current.id = jsonOneItem.getString("id");
                        current.number = jsonOneItem.getString("number");
                        current.type = jsonOneItem.getString("type");
                        current.muscle = jsonOneItem.getString("muscle");
                        current.equipment = jsonOneItem.getString("equipment");
                        current.level = jsonOneItem.getString("level");
                        current.guide = jsonOneItem.getString("guide");
                        current.img1 = jsonOneItem.getString("img1");
                        current.img2 = jsonOneItem.getString("img2");
                        list.add(current);


                    }
                }catch (NullPointerException e){
                    Toast.makeText(context,"Sorry their is network fail ",Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    }
}
