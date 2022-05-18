package com.example.cw6;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
public class MainActivity extends Activity implements OnClickListener
{
    private RadioButton rb1, rb2;
    private EditText et1, et2;
    private DatePicker dp;
    private CheckBox chk1, chk2, chk3, chk4;
    private Button btn1, btn2,btn3,btn6;
    private String s;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rb1 = (RadioButton) this.findViewById(R.id.radio0);
        rb2 = (RadioButton) this.findViewById(R.id.radio1);
        rb1.setOnClickListener(this);
        rb2.setOnClickListener(this);
        et1 = (EditText) this.findViewById(R.id.editText1);
        et2 = (EditText) this.findViewById(R.id.editText2);
        dp = (DatePicker) this.findViewById(R.id.datePicker1);
        chk1 = (CheckBox) this.findViewById(R.id.checkBox1);
        chk2 = (CheckBox) this.findViewById(R.id.checkBox2);
        chk3 = (CheckBox) this.findViewById(R.id.checkBox3);
        chk4 = (CheckBox) this.findViewById(R.id.checkBox4);
        chk1.setOnClickListener(this);
        chk2.setOnClickListener(this);
        chk3.setOnClickListener(this);
        chk4.setOnClickListener(this);
        btn1 = (Button) this.findViewById(R.id.button1);
        btn2 = (Button) this.findViewById(R.id.button2);
        btn3 = (Button) this.findViewById(R.id.button3);
        btn6 = (Button) this.findViewById(R.id.button6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn6.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId())
        { case R.id.button1: wczytaj(); break;
            case R.id.button2: this.finish(); break;
            case R.id.button3:this.wyslijSMS1(wczytaj(),"795197647");break;
            case R.id.button6:this.wyslijSMS2(wczytaj(),"795197647");break;}
    }
    private String wczytaj()
    {
        CharSequence imie = (CharSequence) et1.getText();
        CharSequence nazwisko = (CharSequence) et2.getText();
        int r = dp.getYear();
        int m = dp.getMonth()+1; // miesiące liczone od 0
        int d = dp.getDayOfMonth();
         s = (rb1.isChecked() ? "Pan " : "Pani ") + imie.toString() + ' ' +
                nazwisko.toString();
        s = s + "\nurodz. " + r + '-' + m + '-' + d + "\nzainteresowania: ";
        if (chk1.isChecked()) s = s + chk1.getText().toString() + ", ";
        if (chk2.isChecked()) s = s + chk2.getText().toString() + ", ";
        if (chk3.isChecked()) s = s + chk3.getText().toString() + ", ";
        if (chk4.isChecked()) s = s + chk4.getText().toString();
        Toast toast = Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG);
        toast.show();
        return s;
    }


    // z powodu tej metody wymagane jest uprawnienie android.permission.SEND_SMS
    private void wyslijSMS1(String wiadomosc, String nrtelefonu)
    {
        SmsManager smsmgr = SmsManager.getDefault(); // pobranie menadżera SMS
        smsmgr.sendTextMessage(nrtelefonu, null, wiadomosc, null, null);
        // argumenty: nr telefonu, nr centrum SMS (null to nr domyślny), tekst wiadomości,
        // intencja wywoływana po wysłaniu wiadomości, intencja wywoływana po dostarczeniu powiadomienia
        // (obie intencje to obiekty klasy PendingIntent, null to nie wykorzystywane)
        Toast toast = Toast.makeText(this, "Wysłano SMS", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void wyslijSMS2(String wiadomosc, String nrtelefonu)
    {
       // Intent intencja = new Intent(Intent.ACTION_VIEW); // intencja systemowa i jej rodzaj akcji
        // w najnowszych wersjach systemu Android:
         //Intent intencja = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
        // lub:
        Intent intencja = new Intent(Intent.ACTION_VIEW, Uri.parse("smsto:" + nrtelefonu));
        intencja.putExtra("sms_body", wiadomosc); // dane dla intencji
        intencja.setType("vnd.android-dir/mms-sms"); // typ MIME danych
        startActivity(intencja); // uruchomienie aktywności w systemowej aplikacji do obsługi SMS
    }
}