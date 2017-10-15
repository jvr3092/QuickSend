package com.jware.quicksend;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnviarCorreo extends Activity {

    SharedPreferences mail;
    EditText to, cc, bcc, sbj, msg;
    Button snd;
    String s_to, s_cc, s_bcc, s_sbj, s_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo);

        to = (EditText) this.findViewById(R.id.editTo);
        cc = (EditText) this.findViewById(R.id.editCC);
        bcc = (EditText) this.findViewById(R.id.editBCC);
        sbj = (EditText) this.findViewById(R.id.editSbj);
        msg = (EditText) this.findViewById(R.id.editMsg);
        snd = (Button) this.findViewById(R.id.btnSend);

        mail = getSharedPreferences("Mail_Info", MODE_PRIVATE);

        s_to = mail.getString("To", "");
        s_cc = mail.getString("CC", "");
        s_bcc = mail.getString("BCC", "");
        s_sbj = mail.getString("Subject", "");
        s_msg = mail.getString("Message", "");

        to.setText(s_to);
        cc.setText(s_cc);
        bcc.setText(s_bcc);
        sbj.setText(s_sbj);
        msg.setText(s_msg);

        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sndM = new Intent(Intent.ACTION_SENDTO);
                sndM.setData(Uri.parse("mailto:"));
                //sndM.setType("text/html");
                sndM.putExtra(Intent.EXTRA_EMAIL, new String[]{ s_to});
                sndM.putExtra(Intent.EXTRA_CC, new String[]{ s_cc});
                sndM.putExtra(Intent.EXTRA_BCC, new String[]{ s_bcc});
                sndM.putExtra(Intent.EXTRA_SUBJECT, s_sbj);
                sndM.putExtra(Intent.EXTRA_TEXT, s_msg);
                startActivity(Intent.createChooser(sndM, "Send Mail"));
                Toast env = Toast.makeText(getApplicationContext(),"Sending...", Toast.LENGTH_SHORT);
                env.show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        s_to = to.getText().toString();
        s_cc = cc.getText().toString();
        s_bcc = bcc.getText().toString();
        s_sbj = sbj.getText().toString();
        s_msg = msg.getText().toString();

        SharedPreferences.Editor mailInfo = mail.edit();
        mailInfo.putString("To", s_to);
        mailInfo.putString("CC", s_cc);
        mailInfo.putString("BCC", s_bcc);
        mailInfo.putString("Subject", s_sbj);
        mailInfo.putString("Message", s_msg);

        mailInfo.commit();
    }
}
