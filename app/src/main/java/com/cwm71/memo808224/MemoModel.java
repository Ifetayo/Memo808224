package com.cwm71.memo808224;

import java.util.Date;

/**
 * Created by Ifetayo Agunbiade 808224 on 11/04/2015.
 *
 * This class serves as the Memo model.
 */
public class MemoModel {

    private String memo;
    public String getMemo(){
        return memo;
    }
    public void setMemo(String value){
        this.memo = value;
    }

    private Date date;
    public Date getDate(){
        return date;
    }

    private int mode;
    public int getMode(){
        return mode;
    }


}
