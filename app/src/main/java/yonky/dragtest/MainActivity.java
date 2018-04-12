package yonky.dragtest;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexboxLayout;


public class MainActivity extends AppCompatActivity {
    Button bt;
    FlexboxLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll= findViewById(R.id.ll);
         bt=findViewById(R.id.bt);
        bt.setTag("i am a button");
        ll.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                final int action = event.getAction();

                switch (action){
                    case DragEvent.ACTION_DRAG_STARTED:
                        if(event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_INTENT)){
                            v.setBackgroundColor(Color.BLUE);
                            v.invalidate();
                        }else{
                            return (false);
                        }
                        break;
                        case DragEvent.ACTION_DRAG_ENTERED:
                            v.setBackgroundColor(Color.GREEN);
                            v.invalidate();

                            break;
                    case DragEvent.ACTION_DROP:
                        v.setBackgroundColor(Color.GRAY);
                        Button button = new Button(MainActivity.this);
                        String title = event.getClipData().getItemAt(0).getIntent().getStringExtra("data");
                        button.setText(title);
                        ll.addView(button);
                        v.invalidate();
                        break;
                        default:
                        return false;

                }
                return true;
            }
        });

        bt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("data",bt.getText());
                ClipData dragData=ClipData.newIntent("value",intent);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(bt);
                if(Build.VERSION.SDK_INT<26){
                    bt.startDrag(dragData,myShadow,null,0);
                }
                return false;
            }
        });

    }
}
