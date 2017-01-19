package com.example.ahmed.bodybuillding;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ahmed on 23/12/2016.
 */
public class DetailFragment extends android.support.v4.app.Fragment {
    ImageView img1,img2,sondplay;
    TextView name,type, muscle,equipment,level,guide;
    Boolean ifplay=false;
    MediaPlayer mediaPlayer=null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.detailfragment,container,false);
        sondplay= (ImageView) view.findViewById(R.id.soundplay);
         mediaPlayer=MediaPlayer.create(view.getContext(),R.raw.sound1);
        sondplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ifplay==false){
                    sondplay.setImageResource(R.drawable.son);
                    mediaPlayer.start();
                    ifplay=true;
                }else{
                    sondplay.setImageResource(R.drawable.sof);
                    mediaPlayer.stop();
                    mediaPlayer=MediaPlayer.create(view.getContext(),R.raw.sound1);
                    ifplay=false;
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.release();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StopWatch stopWatch=new StopWatch();
        android.support.v4.app.FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.watch,stopWatch);
        transaction.commit();

    }

    public  void getData(Data data){

        img1= (ImageView) getActivity().findViewById(R.id.dimg1);
        img2= (ImageView) getActivity().findViewById(R.id.dimg2);
        type= (TextView) getActivity().findViewById(R.id.type);
        name= (TextView) getActivity().findViewById(R.id.dname);
        muscle= (TextView) getActivity().findViewById(R.id.muscle);
        equipment= (TextView) getActivity().findViewById(R.id.equipment);
        level= (TextView) getActivity().findViewById(R.id.level);
        guide= (TextView) getActivity().findViewById(R.id.guide);

        byte[] img1_byte=data.img1_byte;
        Bitmap img1_Bit=Convert_Img_Source.getBitmap_Sourse(img1_byte);
        img1.setImageBitmap(img1_Bit);

        byte[] img2_byte=data.img2_byte;
        Bitmap img2_Bit=Convert_Img_Source.getBitmap_Sourse(img2_byte);
        img2.setImageBitmap(img2_Bit);

       name.setText(data.name);
        type.setText(  "Type :   "+data.type);
        muscle.setText("Muscle : "+data.muscle);
        equipment.setText("Equipment : "+data.equipment);
        level.setText("Level :  "+data.level);
        guide.setText(data.guide);




    }
}
