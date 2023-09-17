package com.master.design.therapist.DataModel;

public class CommonReasonDetail {
    private String id ;
    private String reason_eg ;
    private String reason_arb ;

    public CommonReasonDetail(String id, String reason_eg, String reason_arb) {
        this.id = id;
        this.reason_eg = reason_eg;
        this.reason_arb = reason_arb;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason_eg() {
        return reason_eg;
    }

    public void setReason_eg(String reason_eg) {
        this.reason_eg = reason_eg;
    }

    public String getReason_arb() {
        return reason_arb;
    }

    public void setReason_arb(String reason_arb) {
        this.reason_arb = reason_arb;
    }
}
