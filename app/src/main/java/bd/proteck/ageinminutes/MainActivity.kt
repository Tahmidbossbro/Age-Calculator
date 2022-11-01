
package bd.proteck.ageinminutes

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Calendar
import java.util.Calendar.*

class MainActivity : AppCompatActivity() {
    private var tvSelectedDate : TextView? = null
    private var ageInDays : TextView? = null
    private var ageInMonths : TextView? = null
    private var ageInYears : TextView? = null
    private var daysTillBirthday : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val button1: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        ageInYears = findViewById(R.id.ageInYears)
        ageInMonths = findViewById(R.id.ageInMonths)
        ageInDays = findViewById(R.id.ageInDays)
        daysTillBirthday = findViewById(R.id.daysLeft)

        button1.setOnClickListener{
            clickDatePicker()

        }
    }
    @SuppressLint("SimpleDateFormat")
    fun clickDatePicker(){

        val myCalender = getInstance()
        val cal2 = getInstance()

        val year = myCalender.get(YEAR)
        val month = myCalender.get(MONTH)
        val day = myCalender.get(DAY_OF_MONTH)


        DatePickerDialog(this,android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth,{ _, selectedYear, selectedMonth, selectedDateOfMonth ->

            cal2.set(selectedYear, selectedMonth, selectedDateOfMonth)

            val selectedDate = "$selectedDateOfMonth/${selectedMonth+1}/$selectedYear" /// Remember var has been changed to val

            tvSelectedDate?.text = selectedDate
            convertToMinutes(myCalender, cal2)

        }, year, month, day).show()

    }
    private fun convertToMinutes(cal1: Calendar, cal2: Calendar) {

        val yearOld: Int?
        val monthOld: Int?
        val daysOld: Int?
        val daysLeftTillBirthday: String

        val currentBirthDay = getInstance()
        currentBirthDay.set(MONTH, cal2.get(MONTH))
        currentBirthDay.set(DAY_OF_MONTH, cal2.get(DAY_OF_MONTH))
        // Difference in years
        val yearDiff =
            (kotlin.math.abs(cal1.timeInMillis - cal2.timeInMillis)) / 31556952000
        val monthDiff =
            (kotlin.math.abs(cal1.timeInMillis - cal2.timeInMillis)) /2629746000

        yearOld = yearDiff.toInt()
        monthOld = (monthDiff%12).toInt()

        when {
            cal2.get(MONTH) > cal1.get(MONTH) -> {
                currentBirthDay.set(YEAR, cal1.get(YEAR)-1)
                daysOld = ((kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000 ) + 1).toInt()

                currentBirthDay.set(YEAR, currentBirthDay.get(YEAR) + 1)
                daysLeftTillBirthday = "${(kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000)} Days Till Birthday"


            }
            cal2.get(MONTH) == cal1.get(MONTH) -> {
                when {
                    cal2.get(DAY_OF_MONTH) > cal1.get(DAY_OF_MONTH) -> {
                        currentBirthDay.set(YEAR, cal1.get(YEAR) - 1)
                        daysOld = ((kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000 ) + 1).toInt()

                        currentBirthDay.set(YEAR, currentBirthDay.get(YEAR) + 1)
                        daysLeftTillBirthday = "${(kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000)} Days Till Birthday"

                    }
                    cal2.get(DAY_OF_MONTH) < cal1.get(DAY_OF_MONTH) -> {
                        daysOld = ((kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000 ) + 1).toInt()

                        currentBirthDay.set(YEAR, currentBirthDay.get(YEAR) + 1)
                        daysLeftTillBirthday = "${(kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000)} Days Till Birthday"

                    }
                    else -> {
                        daysOld = 0
                        daysLeftTillBirthday = "Happy Birthday"
                    }
                }

            }
            else -> {
                daysOld = ((kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000 ) + 1).toInt()

                currentBirthDay.set(YEAR, currentBirthDay.get(YEAR) + 1)
                daysLeftTillBirthday = "${(kotlin.math.abs(currentBirthDay.timeInMillis - cal1.timeInMillis)/ 86400000)} Days Till Birthday"

            }
        }
        ageInYears?.text = yearOld.toString()
        ageInMonths?.text = monthOld.toString()
        ageInDays?.text = daysOld.toString()
        daysTillBirthday?.text = daysLeftTillBirthday
    }




//        val sdf = SimpleDateFormat("dd/M/yyyy")
//        val currentDate = sdf.format(Date())
//
//        val mDateFormat = SimpleDateFormat("dd/MM/yyyy")
//
//        // // Converting the dates
//        // // from string to date format
//        val mDate11 = mDateFormat.parse(currentDate)
//        val mDate22 = mDateFormat.parse(selectedDate)
//
//        val mDifference = kotlin.math.abs(mDate11.time - mDate22.time)
//
//        val mDifferenceYears = mDifference / 31556952000
//        val mDifferenceMonths = mDifference / 2629746000
//        val mDifferenceDates = mDifference / (24 * 60 * 60 * 1000)
//
//        val yearsOld = mDifferenceYears.toString()
//        val monthsOld =mDifferenceMonths.toString()
//        val daysOld = mDifferenceDates.toString()
//
//
//        ageInYears?.text = yearsOld
//        ageInMonths?.text = monthsOld
//        ageInDays?.text = daysOld
}
