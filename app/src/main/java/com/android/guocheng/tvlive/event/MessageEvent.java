package com.android.guocheng.tvlive.event;

/**
 */
public class MessageEvent {

    /*发出的广播类型*/
    public static final int EVENT_CUSTOM_ADD = 100;
    public static final int EVENT_CUSTOM_EDIT = 101;
    public static final int EVENT_CUSTOM_DELETE = 102;

    private int eventId;
    private Object obj;
    private Object obj2;
    public MessageEvent() {
    }

    public MessageEvent(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj2() {
        return obj2;
    }

    public void setObj2(Object obj2) {
        this.obj2 = obj2;
    }
}
