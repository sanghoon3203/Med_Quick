import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

public class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmName = intent.getStringExtra("ALARM_NAME")
        Toast.makeText(context, "알람: $alarmName", Toast.LENGTH_LONG).show()

        // 추가로 알림 등을 표시하는 작업을 여기에 추가할 수 있습니다.
    }
}
