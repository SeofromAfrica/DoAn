package com.example.doan;

import java.util.Calendar;
import java.util.Date;

public class MarkTodayOnCalendar {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance(); // Lấy đối tượng Calendar cho ngày hiện tại
        calendar.set(Calendar.HOUR_OF_DAY, 0); // Thiết lập giờ bằng 0
        calendar.set(Calendar.MINUTE, 0); // Thiết lập phút bằng 0
        calendar.set(Calendar.SECOND, 0); // Thiết lập giây bằng 0
        calendar.set(Calendar.MILLISECOND, 0); // Thiết lập mili giây bằng 0

        Date today = calendar.getTime(); // Lấy đối tượng Date cho ngày hiện tại

        // In ra ngày hiện tại đã được đánh dấu lên lịch
        System.out.println("Ngày hiện tại đã được đánh dấu lên lịch: " + today);
    }
}
