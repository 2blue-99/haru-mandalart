package com.coldblue.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.theme.HMColor
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    selectedDate: Long,
    onClickCancel: () -> Unit,
    onClickConfirm: (yyyyMMdd: String) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = { onClickCancel() },
        confirmButton = {},
        colors = DatePickerDefaults.colors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp)
    ) {
        val datePickerState = rememberDatePickerState(
            yearRange = LocalDate.now().year..LocalDate.now().year + 1,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return true
                }
            })

        DatePicker(
            state = datePickerState,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = HMColor.Gray),
                onClick = {
                onClickCancel()
            }) {
                Text(text = "취소")
            }
            Spacer(modifier = Modifier.width(12.dp))

            Button(
                modifier = Modifier.padding(end = 16.dp),
                onClick = {
                    datePickerState.selectedDateMillis?.let { selectedDateMillis ->
                        val yyyyMMdd = SimpleDateFormat(
                            "yyyyMMdd",
                            Locale.getDefault()
                        ).format(Date(selectedDateMillis))

                        onClickConfirm(yyyyMMdd)
                    }
                }) {
                Text(text = "확인")
            }
        }
    }
}