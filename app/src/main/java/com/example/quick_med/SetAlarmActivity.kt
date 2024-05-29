import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quick_med.R
import java.util.Calendar

class SetAlarmActivity : AppCompatActivity() {

    private lateinit var spinnerAmPm: Spinner
    private lateinit var numberPickerHour: NumberPicker
    private lateinit var numberPickerMinute: NumberPicker
    private lateinit var editTextAlarmName: EditText
    private lateinit var dayCheckBoxes: Array<CheckBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 초기화
        spinnerAmPm = findViewById(R.id.spinner_am_pm)
        numberPickerHour = findViewById(R.id.numberPicker_hour)
        numberPickerMinute = findViewById(R.id.numberPicker_minute)
        editTextAlarmName = findViewById(R.id.editText_alarm_name)

        dayCheckBoxes = arrayOf(
            findViewById(R.id.checkBox_sunday),
            findViewById(R.id.checkBox_monday),
            findViewById(R.id.checkBox_tuesday),
            findViewById(R.id.checkBox_wednesday),
            findViewById(R.id.checkBox_thursday),
            findViewById(R.id.checkBox_friday),
            findViewById(R.id.checkBox_saturday)
        )

        // NumberPicker 범위 설정
        numberPickerHour.minValue = 1
        numberPickerHour.maxValue = 12
        numberPickerMinute.minValue = 0
        numberPickerMinute.maxValue = 59

        val buttonCreateAlarm: Button = findViewById(R.id.button_create_alarm)
        val buttonCancelAlarm: Button = findViewById(R.id.button_cancel_alarm)

        buttonCreateAlarm.setOnClickListener { setAlarm() }
        buttonCancelAlarm.setOnClickListener { finish() }
    }

    private fun setAlarm() {
        val amPm = spinnerAmPm.selectedItemPosition // 0은 AM, 1은 PM
        var hour = numberPickerHour.value
        val minute = numberPickerMinute.value
        val alarmName = editTextAlarmName.text.toString()

        // 24시간 형식으로 변환
        if (amPm == 1 && hour != 12) {
            hour += 12
        } else if (amPm == 0 && hour == 12) {
            hour = 0
        }

        // 선택된 시간으로 캘린더 설정
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        // 선택된 요일 확인
        val repeatDays = BooleanArray(7)
        for (i in dayCheckBoxes.indices) {
            repeatDays[i] = dayCheckBoxes[i].isChecked
        }

        // 알람 설정
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("ALARM_NAME", alarmName)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (repeatDays[calendar.get(Calendar.DAY_OF_WEEK) - 1]) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7, pendingIntent
            )
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }

        Toast.makeText(this, "알람이 설정되었습니다", Toast.LENGTH_SHORT).show()
    }
}
