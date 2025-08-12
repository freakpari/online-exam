package com.exam.demo.date;

import java.time.LocalDate;

public class JalaliDateConverter {

    private final int[] gregorianMonthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int[] jalaliMonthDays = {31, 31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 29};

    public int[] gregorianToJalali(int gy, int gm, int gd) {
        int gy2 = gy - 1600;
        int gm2 = gm - 1;
        int gd2 = gd - 1;

        int gDayNo = 365 * gy2 + (gy2 + 3) / 4 - (gy2 + 99) / 100 + (gy2 + 399) / 400;
        for (int i = 0; i < gm2; ++i) {
            gDayNo += gregorianMonthDays[i];
        }
        if (gm2 > 1 && ((gy % 4 == 0 && gy % 100 != 0) || (gy % 400 == 0))) {
            gDayNo += 1;
        }
        gDayNo += gd2;

        int jDayNo = gDayNo - 79;

        int jNp = jDayNo / 12053;
        jDayNo %= 12053;

        int jy = 979 + 33 * jNp + 4 * (jDayNo / 1461);
        jDayNo %= 1461;

        if (jDayNo >= 366) {
            jy += (jDayNo - 1) / 365;
            jDayNo = (jDayNo - 1) % 365;
        }

        int jm, jd;
        for (jm = 0; jm < 11 && jDayNo >= jalaliMonthDays[jm]; ++jm) {
            jDayNo -= jalaliMonthDays[jm];
        }
        jd = jDayNo + 1;

        return new int[]{jy, jm + 1, jd};
    }

    public LocalDate jalaliToGregorian(int jy, int jm, int jd) {
        int gy;
        int gm;
        int gd;

        jy -= 979;
        jm -= 1;
        jd -= 1;

        int jDayNo = 365 * jy + (jy / 33) * 8 + (jy % 33 + 3) / 4;
        for (int i = 0; i < jm; ++i) {
            jDayNo += jalaliMonthDays[i];
        }
        jDayNo += jd;

        int gDayNo = jDayNo + 79;

        gy = 1600 + 400 * (gDayNo / 146097);
        gDayNo %= 146097;

        boolean leap = true;
        if (gDayNo >= 36525) {
            gDayNo--;
            gy += 100 * (gDayNo / 36524);
            gDayNo %= 36524;

            if (gDayNo >= 365) {
                gDayNo++;
            } else {
                leap = false;
            }
        }

        gy += 4 * (gDayNo / 1461);
        gDayNo %= 1461;

        if (gDayNo >= 366) {
            leap = false;
            gDayNo--;
            gy += gDayNo / 365;
            gDayNo = gDayNo % 365;
        }

        int i;
        for (i = 0; i < 11 && gDayNo >= gregorianMonthDays[i] + (i == 1 && leap ? 1 : 0); i++) {
            gDayNo -= gregorianMonthDays[i] + (i == 1 && leap ? 1 : 0);
        }
        gm = i + 1;
        gd = gDayNo + 1;

        return LocalDate.of(gy, gm, gd);
    }

    public String convertGregorianToJalaliString(LocalDate date) {
        int[] jalali = gregorianToJalali(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        return String.format("%04d/%02d/%02d", jalali[0], jalali[1], jalali[2]);
    }

    public LocalDate convertJalaliStringToGregorian(String jalaliDate) {
        String[] parts = jalaliDate.split("/");
        int jy = Integer.parseInt(parts[0]);
        int jm = Integer.parseInt(parts[1]);
        int jd = Integer.parseInt(parts[2]);
        return jalaliToGregorian(jy, jm, jd);
    }
}
