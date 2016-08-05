package com.lyl.calendar.widget.calendar;

import java.util.Date;

public interface CalendarCellDecorator {
    void decorate(CalendarCellView cellView, Date date);
}
