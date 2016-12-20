package com.ibm.pmo.utils;

public class EmployeePojo
{
    public Rows[] rows;

    public Rows[] getRows ()
    {
        return rows;
    }

    public void setRows (Rows[] rows)
    {
        this.rows = rows;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rows = "+rows+"]";
    }


 public class Rows
{
    public String value;

    public Key key;

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    public Key getKey ()
    {
        return key;
    }

    public void setKey (Key key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [value = "+value+", key = "+key+"]";
    }
}

 public class Key
{
    private String BUILDING;

    private String REMARKS;

    private String WS_MANAGER;

    private String PEM;

    private String LAPTOP;

    private String ELTP;

    private String _id;

    private String RANGE_EXP;

    private String NOTES_ID;

    private String BAND;

    private String SKILL_SET;

    private String UTILIZATION;

    private String END_DATE_GBSTIMESTAMP;

    private String TYPE;

    private String WORK_LOCATION;

    private String NAME;

    private String DOJ_O2;

    private String WORKSTREAM;

    private String ON_OFF_SHORE;

    private String REVISED_EMP_ID;

    private String CURRENT_ROLE;

    private String AGE_TENURE;

    private String TENURE;

    private String _rev;

    private String DOJ_IBM;

    private String EXPIRES;

    private String START_DATE;

    private String MOBILE;

    private String EMP_ID;

    private String GENDER;

    private String OPEN_SEAT_NO;

    public String getBUILDING ()
    {
        return BUILDING;
    }

    public void setBUILDING (String BUILDING)
    {
        this.BUILDING = BUILDING;
    }

    public String getREMARKS ()
    {
        return REMARKS;
    }

    public void setREMARKS (String REMARKS)
    {
        this.REMARKS = REMARKS;
    }

    public String getWS_MANAGER ()
    {
        return WS_MANAGER;
    }

    public void setWS_MANAGER (String WS_MANAGER)
    {
        this.WS_MANAGER = WS_MANAGER;
    }

    public String getPEM ()
    {
        return PEM;
    }

    public void setPEM (String PEM)
    {
        this.PEM = PEM;
    }

    public String getLAPTOP ()
    {
        return LAPTOP;
    }

    public void setLAPTOP (String LAPTOP)
    {
        this.LAPTOP = LAPTOP;
    }

    public String getELTP ()
    {
        return ELTP;
    }

    public void setELTP (String ELTP)
    {
        this.ELTP = ELTP;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getRANGE_EXP ()
    {
        return RANGE_EXP;
    }

    public void setRANGE_EXP (String RANGE_EXP)
    {
        this.RANGE_EXP = RANGE_EXP;
    }

    public String getNOTES_ID ()
    {
        return NOTES_ID;
    }

    public void setNOTES_ID (String NOTES_ID)
    {
        this.NOTES_ID = NOTES_ID;
    }

    public String getBAND ()
    {
        return BAND;
    }

    public void setBAND (String BAND)
    {
        this.BAND = BAND;
    }

    public String getSKILL_SET ()
    {
        return SKILL_SET;
    }

    public void setSKILL_SET (String SKILL_SET)
    {
        this.SKILL_SET = SKILL_SET;
    }

    public String getUTILIZATION ()
    {
        return UTILIZATION;
    }

    public void setUTILIZATION (String UTILIZATION)
    {
        this.UTILIZATION = UTILIZATION;
    }

    public String getEND_DATE_GBSTIMESTAMP ()
    {
        return END_DATE_GBSTIMESTAMP;
    }

    public void setEND_DATE_GBSTIMESTAMP (String END_DATE_GBSTIMESTAMP)
    {
        this.END_DATE_GBSTIMESTAMP = END_DATE_GBSTIMESTAMP;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    public String getWORK_LOCATION ()
    {
        return WORK_LOCATION;
    }

    public void setWORK_LOCATION (String WORK_LOCATION)
    {
        this.WORK_LOCATION = WORK_LOCATION;
    }

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    public String getDOJ_O2 ()
    {
        return DOJ_O2;
    }

    public void setDOJ_O2 (String DOJ_O2)
    {
        this.DOJ_O2 = DOJ_O2;
    }

    public String getWORKSTREAM ()
    {
        return WORKSTREAM;
    }

    public void setWORKSTREAM (String WORKSTREAM)
    {
        this.WORKSTREAM = WORKSTREAM;
    }

    public String getON_OFF_SHORE ()
    {
        return ON_OFF_SHORE;
    }

    public void setON_OFF_SHORE (String ON_OFF_SHORE)
    {
        this.ON_OFF_SHORE = ON_OFF_SHORE;
    }

    public String getREVISED_EMP_ID ()
    {
        return REVISED_EMP_ID;
    }

    public void setREVISED_EMP_ID (String REVISED_EMP_ID)
    {
        this.REVISED_EMP_ID = REVISED_EMP_ID;
    }

    public String getCURRENT_ROLE ()
    {
        return CURRENT_ROLE;
    }

    public void setCURRENT_ROLE (String CURRENT_ROLE)
    {
        this.CURRENT_ROLE = CURRENT_ROLE;
    }

    public String getAGE_TENURE ()
    {
        return AGE_TENURE;
    }

    public void setAGE_TENURE (String AGE_TENURE)
    {
        this.AGE_TENURE = AGE_TENURE;
    }

    public String getTENURE ()
    {
        return TENURE;
    }

    public void setTENURE (String TENURE)
    {
        this.TENURE = TENURE;
    }

    public String get_rev ()
    {
        return _rev;
    }

    public void set_rev (String _rev)
    {
        this._rev = _rev;
    }

    public String getDOJ_IBM ()
    {
        return DOJ_IBM;
    }

    public void setDOJ_IBM (String DOJ_IBM)
    {
        this.DOJ_IBM = DOJ_IBM;
    }

    public String getEXPIRES ()
    {
        return EXPIRES;
    }

    public void setEXPIRES (String EXPIRES)
    {
        this.EXPIRES = EXPIRES;
    }

    public String getSTART_DATE ()
    {
        return START_DATE;
    }

    public void setSTART_DATE (String START_DATE)
    {
        this.START_DATE = START_DATE;
    }

    public String getMOBILE ()
    {
        return MOBILE;
    }

    public void setMOBILE (String MOBILE)
    {
        this.MOBILE = MOBILE;
    }

    public String getEMP_ID ()
    {
        return EMP_ID;
    }

    public void setEMP_ID (String EMP_ID)
    {
        this.EMP_ID = EMP_ID;
    }

    public String getGENDER ()
    {
        return GENDER;
    }

    public void setGENDER (String GENDER)
    {
        this.GENDER = GENDER;
    }

    public String getOPEN_SEAT_NO ()
    {
        return OPEN_SEAT_NO;
    }

    public void setOPEN_SEAT_NO (String OPEN_SEAT_NO)
    {
        this.OPEN_SEAT_NO = OPEN_SEAT_NO;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BUILDING = "+BUILDING+", REMARKS = "+REMARKS+", WS_MANAGER = "+WS_MANAGER+", PEM = "+PEM+", LAPTOP = "+LAPTOP+", ELTP = "+ELTP+", _id = "+_id+", RANGE_EXP = "+RANGE_EXP+", NOTES_ID = "+NOTES_ID+", BAND = "+BAND+", SKILL_SET = "+SKILL_SET+", UTILIZATION = "+UTILIZATION+", END_DATE_GBSTIMESTAMP = "+END_DATE_GBSTIMESTAMP+", TYPE = "+TYPE+", WORK_LOCATION = "+WORK_LOCATION+", NAME = "+NAME+", DOJ_O2 = "+DOJ_O2+", WORKSTREAM = "+WORKSTREAM+", ON_OFF_SHORE = "+ON_OFF_SHORE+", REVISED_EMP_ID = "+REVISED_EMP_ID+", CURRENT_ROLE = "+CURRENT_ROLE+", AGE_TENURE = "+AGE_TENURE+", TENURE = "+TENURE+", _rev = "+_rev+", DOJ_IBM = "+DOJ_IBM+", EXPIRES = "+EXPIRES+", START_DATE = "+START_DATE+", MOBILE = "+MOBILE+", EMP_ID = "+EMP_ID+", GENDER = "+GENDER+", OPEN_SEAT_NO = "+OPEN_SEAT_NO+"]";
    }
}
  }