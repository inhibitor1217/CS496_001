package com.example.user.cs496_001;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TabFragment3 extends Fragment {

    Intent manboService;
    BroadcastReceiver receiver;
    boolean flag = true;
    String serviceData;
    TextView count,cal;
    Button button;
    int weight,sec=0,min=0,hour=0;
    long start,end;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View resultView = inflater.inflate(R.layout.tab_fragment_3, container, false);

        manboService = new Intent(getActivity(), StepCheckService.class);
        receiver = new PlayingReceiver();

        count = (TextView) resultView.findViewById(R.id.count);
        button = (Button) resultView.findViewById(R.id.button);
        cal = (TextView)resultView.findViewById(R.id.cal);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( button.getText().equals("게임 시작") ){
                    AlertDialog.Builder popup = new AlertDialog.Builder(v.getContext());
                    popup.setTitle("만보기 건강하게 사용하기!");
                    popup.setMessage("몸무게는?");
                    final EditText ask = new EditText(v.getContext());
                    popup.setView(ask);

                    popup.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{
                                weight = Integer.parseInt(ask.getText().toString());
                            }catch(NumberFormatException e){
                                Toast.makeText(getActivity(), "시작 버튼을 눌러주세요", Toast.LENGTH_SHORT).show();
                                flag = !flag;
                            }

                        }
                    });
                    popup.show();

                }
                if (flag) {
                    button.setText("정지");
                    try {
                        start = System.currentTimeMillis();
                        end = 0;
                        IntentFilter mainFilter = new IntentFilter("counter");
                        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, mainFilter);
                        getActivity().startService(manboService);
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    button.setText("시작");
                    try {
                        end = System.currentTimeMillis();
                        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
                        getActivity().stopService(manboService);
                    } catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                flag = !flag;
            }
        });

        return resultView;
    }

    public void timeCalculate(){
        end = System.currentTimeMillis();
        sec = (int) ((end-start) / 1000);
        int calories;
        if (sec>=60){
            min = sec / 60;
            sec = sec % 60;
        }else{
            cal.setText(sec+"초 동안 걸으셨습니다.");
            return;
        }
        if (min>=60){
            hour = min / 60;
            min = min % 60;
        }else{
            calories = (int) (0.07*weight*min);
            cal.setText(min+"분 "+sec+"초 동안 "+calories+" 칼로리를 소모했습니다.");
            return;
        }
        calories = (int) (0.07*weight*(min+60*hour));
        cal.setText(hour+"시간 "+min+"분 "+sec+"초 동안 "+calories+" 칼로리를 소모했습니다.");
    }

    class PlayingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            serviceData = intent.getStringExtra("count");
            count.setText(serviceData);
            timeCalculate();
        }
    }

}
