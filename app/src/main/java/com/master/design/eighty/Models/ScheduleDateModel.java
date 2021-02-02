package com.master.design.eighty.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class ScheduleDateModel implements Parcelable {
    Date date;
    String day;
    Boolean Morning;
    Boolean Evening;
    Boolean MidDay;
    Boolean MorningAssigned;
    Boolean EveningAssigned;
    Boolean MidDayAssigned;

    public Boolean getMorningAssigned() {
        return MorningAssigned;
    }

    public void setMorningAssigned(Boolean morningAssigned) {
        MorningAssigned = morningAssigned;
    }

    public Boolean getEveningAssigned() {
        return EveningAssigned;
    }

    public void setEveningAssigned(Boolean eveningAssigned) {
        EveningAssigned = eveningAssigned;
    }

    public Boolean getMidDayAssigned() {
        return MidDayAssigned;
    }

    public void setMidDayAssigned(Boolean midDayAssigned) {
        MidDayAssigned = midDayAssigned;
    }

    public ScheduleDateModel(Date date1, String day1, Boolean M, Boolean E, Boolean MD )
    {
        date=date1;
        day=day1;
        Morning=M;
        Evening=E;
        MidDay=MD;
        MorningAssigned=false;
        MidDayAssigned=false;
        EveningAssigned=false;

    }
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getMorning() {
        return Morning;
    }

    public void setMorning(Boolean morning) {
        Morning = morning;
    }

    public Boolean getEvening() {
        return Evening;
    }

    public void setEvening(Boolean evening) {
        Evening = evening;
    }

    public Boolean getMidDay() {
        return MidDay;
    }

    public void setMidDay(Boolean midDay) {
        MidDay = midDay;
    }

    protected ScheduleDateModel(Parcel in) {
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        day = in.readString();
        byte MorningVal = in.readByte();
        Morning = MorningVal == 0x02 ? null : MorningVal != 0x00;
        byte EveningVal = in.readByte();
        Evening = EveningVal == 0x02 ? null : EveningVal != 0x00;
        byte MidDayVal = in.readByte();
        MidDay = MidDayVal == 0x02 ? null : MidDayVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeString(day);
        if (Morning == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (Morning ? 0x01 : 0x00));
        }
        if (Evening == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (Evening ? 0x01 : 0x00));
        }
        if (MidDay == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (MidDay ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<ScheduleDateModel> CREATOR = new Creator<ScheduleDateModel>() {
        @Override
        public ScheduleDateModel createFromParcel(Parcel in) {
            return new ScheduleDateModel(in);
        }

        @Override
        public ScheduleDateModel[] newArray(int size) {
            return new ScheduleDateModel[size];
        }
    };
}