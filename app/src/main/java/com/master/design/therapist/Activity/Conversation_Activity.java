package com.master.design.therapist.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.design.therapist.Adapter.MessageChatAdapter;
import com.master.design.therapist.DM.MessageChatModel;
import com.master.design.therapist.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class Conversation_Activity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    @BindView(R.id.rcvRcv)
    RecyclerView rcvRcv;
    @BindView(R.id.profileCircleImg)
    CircleImageView profileCircleImg;
    @BindView(R.id.userNameTxt)
    TextView userNameTxt;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.notificationImg)
    ImageView notificationImg;
    MessageChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        context = getApplicationContext();
        activity = Conversation_Activity.this;
        LinearLayoutManager manager = new LinearLayoutManager(Conversation_Activity.this, RecyclerView.VERTICAL, false);
        rcvRcv.setLayoutManager(manager);
        setChatData();
    }

    List<MessageChatModel> messageChatModelList = new ArrayList<>();

    private void setChatData() {
        MessageChatModel model1 = new MessageChatModel(
                "Hello. How are you today?",
                "10:00 PM",
                0
        );
        MessageChatModel model2 = new MessageChatModel(
                "Hey! I'm fine. Thanks for asking!",
                "10:00 PM",
                1
        );
        MessageChatModel model3 = new MessageChatModel(
                "Sweet! So, what do you wanna do today?",
                "10:00 PM",
                0
        );
        MessageChatModel model4 = new MessageChatModel(
                "Nah, I dunno. Play soccer.. or learn more coding perhaps?",
                "10:00 PM",
                1
        );


        messageChatModelList.add(model1);
        messageChatModelList.add(model2);
        messageChatModelList.add(model3);
        messageChatModelList.add(model4);
        messageChatModelList.add(model1);
        messageChatModelList.add(model2);
        messageChatModelList.add(model3);
        messageChatModelList.add(model4);
        messageChatModelList.add(model1);
        messageChatModelList.add(model2);
        messageChatModelList.add(model3);
        messageChatModelList.add(model4);

        rcvRcv.smoothScrollToPosition(messageChatModelList.size());
        adapter = new MessageChatAdapter(messageChatModelList, context);
        rcvRcv.setAdapter(adapter);
    }


    @OnClick(R.id.backImg)
    public void clickBack() {
        finish();
    }


    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_in);
    }
}