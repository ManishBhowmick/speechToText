package com.example.pranoy.assign16;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class Main2Activity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    ImageButton ib;
    Button b1;
    EditText e1;
    String voice=null;
    TextToSpeech tts;
    NotificationManager notificationManager;
    Notification notification;
    Uri uri;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        e1=(EditText) findViewById(R.id.editText);
        ib=(ImageButton) findViewById(R.id.imageButton);
        b1=(Button) findViewById(R.id.button);
        tts=new TextToSpeech(this,this);
        notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                voiceToText();
            }
        });
/*        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"random snackbar",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
        });*/
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private void voiceToText() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"pranoy");
        try{
            startActivityForResult(i,100);
        }
        catch (Exception e){
            Toast.makeText(Main2Activity.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
    private void textToVoice(){
        tts.speak(voice,TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode==RESULT_OK && null!=data){
                voice=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                e1.setText(voice);
                if(voice.equals("notification")){
                    notification=new Notification.Builder(Main2Activity.this).setSmallIcon(R.drawable.noti1)
                            .setContentTitle("voice activated notification ").setContentText("blah blah").setSound(uri).build();
                    notificationManager.notify(0,notification);
                   // Toast.makeText(Main2Activity.this,"blah",Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(Main2Activity.this,"yes",Toast.LENGTH_SHORT).show();
                else if(voice.equals("2"))
                {
                    View parentLayout=(View) findViewById(R.id.root_view);
                    Snackbar.make(parentLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(voice.equals("3")){
                    Toast.makeText(Main2Activity.this,"yes",Toast.LENGTH_SHORT).show();
                }
                else if(voice.equals("4")){
                    Dialog d=new Dialog(Main2Activity.this);
                    d.setTitle("voice activated dialog");
                    TextView tv=new TextView(Main2Activity.this);
                    tv.setText("blah");
                    d.setContentView(tv);
                    d.show();
                }
 //               voice="wow pranoy you are a genius. i love you!!";
                textToVoice();
                voice=null;
            }
            else{
                try{
                    voice="data not recognized please try again";
                    textToVoice();
                }
                catch(Exception e){
                    Toast.makeText(Main2Activity.this,"error"+e,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onInit(int status) {
        try{
            if(status==TextToSpeech.SUCCESS){
                int result=tts.setLanguage(Locale.ENGLISH);
                if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(Main2Activity.this,"language not supported",Toast.LENGTH_SHORT).show();
                }else{
                    textToVoice();
                }
            }
            else
                Toast.makeText(Main2Activity.this,"something went wrong",Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(Main2Activity.this,"error= "+e,Toast.LENGTH_SHORT).show();
        }
    }
}
