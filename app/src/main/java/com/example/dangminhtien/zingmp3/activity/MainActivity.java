package com.example.dangminhtien.zingmp3.activity;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.dangminhtien.zingmp3.R;
import com.example.dangminhtien.zingmp3.helper.helper_tools;
import com.example.dangminhtien.zingmp3.data.music;
import com.example.dangminhtien.zingmp3.data.write_data_to_external_storage;
import com.example.dangminhtien.zingmp3.fragment.fragment_offline;
import com.example.dangminhtien.zingmp3.fragment.fragment_online;
import com.example.dangminhtien.zingmp3.fragment.fragment_search;
import com.example.dangminhtien.zingmp3.model.create_notification;
import com.example.dangminhtien.zingmp3.model.xuly_music;
import com.example.dangminhtien.zingmp3.service.service_music;

import java.io.IOException;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements ServiceConnection, xuly_music.on_play_pause_listener,MediaPlayer.OnCompletionListener {
    //Comment test hàng
    private BottomNavigationView btm_nav_menu;
    private Fragment fragment_offline, fragment_search, fragment_online;
    private helper_tools helper_tools;
    private BottomSheetBehavior behavior;
    private View btms;
    private ImageView img_song;
    private com.example.dangminhtien.zingmp3.service.service_music service_music;
    private ImageButton btn_play_and_pause, btn_prev, btn_next, btn_mode;
    private ImageButton btn_play_and_pause_btms,btn_prev_btms, btn_next_btms, btn_mode_btms;
    public static final int CONNECTED=0;
    public static final int DISCONNECTED=1;
    private SeekBar sb_music;
    private create_notification create_notification;
    public static int STATE=DISCONNECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        addControls();
        addEvents();
        xuly_bottom_sheet();
        create_notification = new create_notification(getApplicationContext());
        create_notification.create_noti(null, null);
        write_data_to_external_storage write_data_to_external_storage = new write_data_to_external_storage(getApplicationContext(), this);
////        try {
////            write_data_to_external_storage.write_music("ghen.mp3");
////            write_data_to_external_storage.write_music("1234.mp3");
////            write_data_to_external_storage.write_music("co3la.mp3");
////            write_data_to_external_storage.write_music("happyending.mp3");
////            write_data_to_external_storage.write_music("khitaconhau.mp3");
////            write_data_to_external_storage.write_music("yeu5.mp3");
////            write_data_to_external_storage.write_music("yeulathathu.mp3");
////        } catch (IOException e) {
////            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
////            e.printStackTrace();
////        }
//        music music1 = new music();
//        music1.setId_music("12312sd123as312");
//        music1.setSinger_name("Rhymastic");
//        music1.setSong_name("Yêu 5");
//        music1.setFavorite_music(true);
//        music1.setSong_name_path("yeu5");
//
//        music music2 = new music();
//        music2.setId_music("1231fds23121q123we23");
//        music2.setSinger_name("Erik");
//        music2.setSong_name("Ghen");
//        music2.setFavorite_music(false);
//        music2.setSong_name_path("ghen");
//
//        music music3 = new music();
//        music3.setId_music("12adas31fds23121q123we23");
//        music3.setSinger_name("Chi Dân");
//        music3.setSong_name("1234");
//        music3.setFavorite_music(false);
//        music3.setSong_name_path("1234");
//
//        music music4 = new music();
//        music4.setId_music("1zxc1q123we23");
//        music4.setSinger_name("Lê Hiếu");
//        music4.setSong_name("Ngày mai sẽ khác");
//        music4.setFavorite_music(true);
//        music4.setSong_name_path("ngaymaisekhac");
//
//        music music5 = new music();
//        music5.setId_music("11q123we23");
//        music5.setSinger_name("Erik");
//        music5.setSong_name("Happy ending");
//        music5.setFavorite_music(true);
//        music5.setSong_name_path("happyending");
//
//        music music6 = new music();
//        music6.setId_music("1231fds23121q1");
//        music6.setSinger_name("Min");
//        music6.setSong_name("Có em chờ");
//        music6.setFavorite_music(false);
//        music6.setSong_name_path("coemcho");
//
//        music music7 = new music();
//        music7.setId_music("1ada3121q123we23");
//        music7.setSinger_name("Will");
//        music7.setSong_name("Khi ta có nhau");
//        music7.setFavorite_music(false);
//        music7.setSong_name_path("khitaconhau");
//
//        write_to_realm write_to_realm = new write_to_realm(getApplicationContext());
//        write_to_realm.write_music(music1);
//        write_to_realm.write_music(music2);
//        write_to_realm.write_music(music3);
//        write_to_realm.write_music(music4);
//        write_to_realm.write_music(music5);
//        write_to_realm.write_music(music6);
//        write_to_realm.write_music(music7);
//
//        playlist playlist1 = new playlist();
//        RealmList<music> musics = new RealmList<music>();
//        musics.add(music1);
//        musics.add(music2);
//        musics.add(music3);
//        musics.add(music7);
//        playlist1.setId_playlist("czxczx");
//        playlist1.setName_playlist("Nhạc yêu thích 3");
//
//        playlist playlist2 = new playlist();
//        RealmList<music> musics2 = new RealmList<music>();
//        musics2.add(music5);
//        musics2.add(music4);
//        musics2.add(music3);
//        musics2.add(music7);
//        playlist2.setId_playlist("blahblah");
//        playlist2.setName_playlist("Nhạc yêu thích 2");
//
//        playlist playlist3 = new playlist();
//        RealmList<music> musics3 = new RealmList<music>();
//        musics3.add(music7);
//        musics3.add(music5);
//        musics3.add(music6);
//        musics3.add(music1);
//        playlist3.setId_playlist("dangminhtein");
//        playlist3.setName_playlist("Nhạc yêu thích3");
//        write_to_realm.write_playlist(playlist1);
//        write_to_realm.write_playlist(playlist2);
//        write_to_realm.write_playlist(playlist3);
    }

    private void addControls() {
        btms=findViewById(R.id.btms_play_music);
        behavior = BottomSheetBehavior.from(btms);
        btm_nav_menu= (BottomNavigationView) findViewById(R.id.btm_nav_menu);
        fragment_offline=new fragment_offline();
        fragment_search=new fragment_search();
        fragment_online=new fragment_online();
        helper_tools=new helper_tools(this,this);
        btn_prev= (ImageButton) findViewById(R.id.control_music).findViewById(R.id.btn_prev);
        btn_next= (ImageButton) findViewById(R.id.control_music).findViewById(R.id.btn_next);
        btn_mode= (ImageButton) findViewById(R.id.control_music).findViewById(R.id.btn_mode);
        btn_play_and_pause= (ImageButton) findViewById(R.id.control_music).findViewById(R.id.btn_play_and_pause);
        btn_prev_btms= (ImageButton) findViewById(R.id.control_music_btms).findViewById(R.id.btn_prev);
        btn_mode_btms= (ImageButton) findViewById(R.id.control_music_btms).findViewById(R.id.btn_mode);
        btn_next_btms= (ImageButton) findViewById(R.id.control_music_btms).findViewById(R.id.btn_next);
        btn_play_and_pause_btms= (ImageButton) findViewById(R.id.control_music_btms).findViewById(R.id.btn_play_and_pause);
        img_song= (ImageView) findViewById(R.id.img_song);
        sb_music= (SeekBar) findViewById(R.id.sb_music);

        btn_play_and_pause.setImageLevel(1);
        btn_play_and_pause_btms.setImageLevel(1);
        btn_mode.setImageLevel(1);
        btn_mode_btms.setImageLevel(1);
        xuly_music.setMode(1);
        xuly_music.get_instance().set_on_complete_listener(this);
        xuly_music.get_instance().set_on_play_listener(this);

    }

    private void addEvents() {
        btm_nav_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_item_search:
                        xuly_hienthi_fragment(fragment_search);
                            break;
                        case R.id.nav_item_music_offline:
                        xuly_hienthi_fragment(fragment_offline);
                            break;
                        case R.id.nav_item_music_online:
                            xuly_hienthi_fragment(fragment_online);
                            break;
                        default:
                            break;
                    }
                return true;
            }
        });

        btn_play_and_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((xuly_music.STATE==xuly_music.PLAYING)) {
                    xuly_music.get_instance().pause(null);
                } else {
                    xuly_music.get_instance().play(null);
                }}
        });


        btn_play_and_pause_btms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((xuly_music.STATE==xuly_music.PLAYING)) {
                    xuly_music.get_instance().pause(null);
                } else {
                    xuly_music.get_instance().play(null);
                }

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuly_music.get_instance().next_music(getApplicationContext());
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuly_music.get_instance().prev_music(getApplicationContext());
            }
        });

        btn_next_btms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuly_music.get_instance().next_music(getApplicationContext());
            }
        });

        btn_prev_btms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuly_music.get_instance().prev_music(getApplicationContext());
            }
        });
        sb_music.setProgress(xuly_music.get_instance().get_duration());
        sb_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    xuly_music xuly_music = com.example.dangminhtien.zingmp3.model.xuly_music.get_instance();
                    xuly_music.seek_to(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_mode.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                switch (xuly_music.getMode())  {
                    case 1:
                        xuly_music.setMode(2);
                        btn_mode.setImageLevel(2);
                        sync_state_btn_mode(2);
                        break;
                    case 2:
                        xuly_music.setMode(3);
                        btn_mode.setImageLevel(3);
                        sync_state_btn_mode(3);
                        break;
                    case 3:
                        xuly_music.setMode(1);
                        btn_mode.setImageLevel(1);
                        sync_state_btn_mode(1);
                        break;
                    default:
                        return;
                }
        }});

        btn_mode_btms.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                switch (xuly_music.getMode())  {
                    case 1:
                        xuly_music.setMode(2);
                        btn_mode_btms.setImageLevel(2);
                        sync_state_btn_mode(2);
                        break;
                    case 2:
                        xuly_music.setMode(3);
                        btn_mode_btms.setImageLevel(3);
                        sync_state_btn_mode(3);
                        break;
                    case 3:
                        xuly_music.setMode(1);
                        btn_mode_btms.setImageLevel(1);
                        sync_state_btn_mode(1);
                        break;
                    default:
                        return;
                }
        }});

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void xuly_hienthi_fragment(Fragment fragment_replaced) {
        FragmentManager fragment_manager=getSupportFragmentManager();
        FragmentTransaction fragment_transaction=fragment_manager.beginTransaction();
        fragment_transaction.replace(R.id.frame_main, fragment_replaced);
        fragment_transaction.commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void replace_fragment(AppCompatActivity activity, Fragment fragment_replaced) {
        FragmentManager fragment_manager=activity.getSupportFragmentManager();
        FragmentTransaction fragment_transaction=fragment_manager.beginTransaction();
        // cũng giống với activity thôi
        fragment_replaced.setEnterTransition(TransitionInflater.from(activity).inflateTransition(R.transition.transition_slide));
        fragment_replaced.setReenterTransition(TransitionInflater.from(activity).inflateTransition(R.transition.transition_slide));
        fragment_replaced.setSharedElementEnterTransition(TransitionInflater.from(activity).inflateTransition(R.transition.transition_slide));
//      Dùng phương thức này để set transaction cho từng element, giống với activity  fragment_transaction.addSharedElement();
//      Cần lưu ý là fragment không hỗ trợ việc sử dụng nhiều view (pair.create)
        fragment_transaction.replace(R.id.frame_main, fragment_replaced);
        // sử dung thằng này để commit
        fragment_transaction.commitAllowingStateLoss();
    }

    private void xuly_bottom_sheet () {

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState==BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setPeekHeight(helper_tools.convert_dp_to_px(39));
                } else if (newState==BottomSheetBehavior.STATE_EXPANDED){
                    behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        behavior.setPeekHeight(helper_tools.convert_dp_to_px(39));
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        com.example.dangminhtien.zingmp3.service.service_music.binder binder = (service_music.binder) service;
        service_music=binder.get_service();
        STATE=CONNECTED;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        STATE=DISCONNECTED;
    }

    @Override
    public void on_play(music music) {
        sb_music.setMax(xuly_music.get_instance().get_duration());
        sb_music.setProgress(0);
        update_seekBar update_seekBar = new update_seekBar();
        update_seekBar.execute();
                    if (music!=null) {
            create_notification.update_notifi(music.getSong_name(), xuly_music.get_instance().duration_toString(),true);
        }
        img_song.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_img_song_rotate));
        sync_state_btn_pause_play(true);
    }

    @Override
    public void on_pause(music music) {
        sb_music.setProgress(xuly_music.get_instance().get_current_position());
        sync_state_btn_pause_play(false);
        img_song.clearAnimation();
    }

    public void sync_state_btn_pause_play (boolean is_play) {
        if (is_play) {
            btn_play_and_pause.setImageLevel(1);
            btn_play_and_pause_btms.setImageLevel(1);
            create_notification.update_notifi(null, null, true);
        } else {
            btn_play_and_pause.setImageLevel(2);
            btn_play_and_pause_btms.setImageLevel(2);
            create_notification.update_notifi(null, null, false);
        }
    }

    public void sync_state_btn_mode (int mode) {
         btn_mode.setImageLevel(mode);
         btn_mode_btms.setImageLevel(mode);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        sync_state_btn_pause_play(false);
        img_song.clearAnimation();
        try {
            xuly_music.get_instance().process_mode(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class update_seekBar extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            while (xuly_music.STATE==xuly_music.PLAYING) {
                    try {
                        Thread.sleep(700);
                        publishProgress(xuly_music.get_instance().get_current_position());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            sb_music.setProgress(values[0]);
        }
    }
}
