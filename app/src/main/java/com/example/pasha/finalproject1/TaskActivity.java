package com.example.pasha.finalproject1;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.View.OnClickListener;
public class TaskActivity extends AppCompatActivity implements View.OnClickListener  {

    EditText Nazvanie,Mesto;
    TextView baseContext;
    Button Save,Exit, showButton;
    DBHelper dbHelper;
    Switch aSwitch;

    private TextView TimeDisplay;
    private TimePicker mTimePicker;
    private Button SetTime;

    private int hours;
    private int minutes;
    Intent i;
    int mYear;
    int mMonth;
    int mDay ;
    int defaultValue = 0;

    static final int TIME_DIALOG_ID = 999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Nazvanie = findViewById(R.id.naz);
        Mesto = findViewById(R.id.mesto);
        Save = findViewById(R.id.save);
        Save.setOnClickListener(this);
        baseContext = findViewById(R.id.base_context);
        showButton = findViewById(R.id.show_records);
        showButton.setOnClickListener(this);

        aSwitch = findViewById(R.id.swi);
        aSwitch.setOnClickListener(this);

        Exit = findViewById(R.id.exit);
        Exit.setOnClickListener(this);
        setCurrentTime();
        ButtonListener();
        aSwitch = findViewById(R.id.swi);
        aSwitch.setOnClickListener(this);

         i = getIntent();
        mYear = i.getIntExtra("Year", defaultValue);
        mMonth = i.getIntExtra("Month", defaultValue);
        mDay = i.getIntExtra("Day", defaultValue);
        dbHelper = new DBHelper(this);
        SetTime.setEnabled(false);

        String DATA1 = mDay + "." + mMonth + "." + mYear;
      //  dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
        String SRAVNENIE3 = "SELECT * FROM "+DBHelper.TABLE_CONTACTS+" WHERE "+DBHelper.KEY_DATA+"="+ "\""+DATA1+"\";";
        Cursor cursor3 = db.rawQuery(SRAVNENIE3,null);
        cursor3.moveToFirst();
        //if (cursor3 != null){
        if (cursor3.getCount()!=0){
            while (cursor3.moveToNext()) {

                String nazvanie = cursor3.getString(cursor3.getColumnIndex(DBHelper.KEY_NAZVANIE));
                String time = cursor3.getString(cursor3.getColumnIndex(DBHelper.KEY_TIME));
                String mesto = cursor3.getString(cursor3.getColumnIndex(DBHelper.KEY_MESTO));

                Nazvanie.setText(nazvanie);
                TimeDisplay.setText(time);
                Mesto.setText(mesto);
                baseContext.append(nazvanie+" "+time+" "+mesto+"\n");
            }
        }
        cursor3.close();
        db.close();
    }

    //TODO всё что ниже это время /////////////////////////////////////////////////////////////////////////////////////////////
    public void setCurrentTime() {
        TimeDisplay = (TextView) findViewById(R.id.CurTime);
        mTimePicker = (TimePicker) findViewById(R.id.TimePick);

        final Calendar calendar = Calendar.getInstance();
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);

        //Настраиваем текущее время в TextView:
        TimeDisplay.setText(
                new StringBuilder().append(pad(hours))
                        .append(":").append(pad(minutes)));

        //Настраиваем текущее время в TimePicker:
        mTimePicker.setCurrentHour(hours);
        mTimePicker.setCurrentMinute(minutes);

    }

    //Добавляем слушателя нажатий кнопки:
    public void ButtonListener() {
        SetTime = (Button) findViewById(R.id.button);
        SetTime.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //При нажатии кнопки запускается диалог TimePickerDialog для выбора времени:
                showDialog(TIME_DIALOG_ID);

            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                //Задаем в TimePicker текущее время:
                return new TimePickerDialog(this,
                        timePickerListener, hours, minutes,false);
        }
        return null;
    }

    //Настраиваем диалоговое окно TimePickerDialog:
    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,int selectedMinute) {
                    hours = selectedHour;
                    minutes = selectedMinute;

                    //Настраиваем выбранное время в TextView:
                    TimeDisplay.setText(new StringBuilder().append(pad(hours))
                            .append(":").append(pad(minutes)));

                    //Настраиваем выбранное время в TimePicker:
                    mTimePicker.setCurrentHour(hours);
                    mTimePicker.setCurrentMinute(minutes);

                }
            };
    //Для показания минут настраиваем отображение 0 впереди чисел со значением меньше 10:
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

//TODO Здесь заканчивается определение времени ////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onClick (View view){

        String TIME = TimeDisplay.getText().toString();
        String NAZVANIE = Nazvanie.getText().toString();

        String MESTO = Mesto.getText().toString();
        String DATA = mDay + "." + mMonth + "." + mYear;
        if(aSwitch.isChecked()){
            SetTime.setEnabled(true);
             TIME = TimeDisplay.getText().toString();
        }else{
            SetTime.setEnabled(false);
            TIME=null;
        }
//TODO Здесь происходит проверка: нет ли в базе подобной ячейки и выход из активности
switch (view.getId()){
    case R.id.save:
        dbHelper = new DBHelper(getBaseContext());
        SQLiteDatabase db;
        db = dbHelper.getReadableDatabase();
//TODO Ниже проверка на совпадение
        String SRAVNENIE = "SELECT * FROM "+DBHelper.TABLE_CONTACTS+" WHERE "+DBHelper.KEY_DATA+"="+ "\""+DATA+"\"" +";";
        Cursor cursor = db.rawQuery(SRAVNENIE,null);
//TODO Ниже запрос для создания
        String insertQuery = "INSERT INTO " + DBHelper.TABLE_CONTACTS + " (" + DBHelper.KEY_DATA + "," +
                DBHelper.KEY_NAZVANIE + "," + DBHelper.KEY_MESTO + "," + DBHelper.KEY_TIME  +
                ") VALUES (\"" + DATA +"\", \""+NAZVANIE+"\", \"" +MESTO+ "\", \""+TIME+"\");";
        cursor.moveToFirst();
        String updateQuery = "UPDATE " + DBHelper.TABLE_CONTACTS + " SET " + DBHelper.KEY_NAZVANIE + " = \"" + NAZVANIE   + "\", " + DBHelper.KEY_MESTO + "= \"" + MESTO + "\", " + DBHelper.KEY_TIME + "= \"" + TIME+ "\" "  + " WHERE " + DBHelper.KEY_DATA  + "= \"" + DATA +"\";" ;
        db.close();
        if (cursor.getCount() == 0 ){
            dbHelper = new DBHelper(getBaseContext());
            db = dbHelper.getWritableDatabase();
            db.execSQL(insertQuery);
            db.close();
            cursor.close();
        }else{
            dbHelper = new DBHelper(getBaseContext());
            db = dbHelper.getWritableDatabase();
            db.execSQL(updateQuery);
            db.close();
            cursor.close();
        }
        i = new Intent(TaskActivity.this, MainActivity.class );
        startActivity(i);
        TaskActivity.this.finish();
        break;
    case R.id.exit:
        i = new Intent(TaskActivity.this, MainActivity.class );
        startActivity(i);
        TaskActivity.this.finish();
        break;
    case R.id.show_records:
        dbHelper = new DBHelper(getBaseContext());
        db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM "+DBHelper.TABLE_CONTACTS+";";
        Cursor cursor1 = db.rawQuery(query, null);
        cursor1.moveToFirst();
        while (cursor1.moveToNext()){
            String nazvanie = cursor1.getString(cursor1.getColumnIndex(DBHelper.KEY_NAZVANIE));
            String time = cursor1.getString(cursor1.getColumnIndex(DBHelper.KEY_TIME));
            String mesto = cursor1.getString(cursor1.getColumnIndex(DBHelper.KEY_MESTO));

            baseContext.append(nazvanie + " " + time + "" + mesto + "\n");
        }
        cursor1.close();
        db.close();
        break;

}}

}
