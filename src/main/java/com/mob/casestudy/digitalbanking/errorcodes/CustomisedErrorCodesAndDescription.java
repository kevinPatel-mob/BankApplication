package com.mob.casestudy.digitalbanking.errorcodes;




public class CustomisedErrorCodesAndDescription {

    private CustomisedErrorCodesAndDescription() {
    }

    public static final String USER_NOT_FOUND="CUS-UPDATE-NFD-001";
    public static final String USER_NOT_FOUND_DESCRIPTION="Customer should be present in system";

    public static final String INVALID_PHONE_NUMBER="CUS-UPDATE-FIE-002";
    public static final String INVALID_PHONE_NUMBER_DESCRIPTION="Invalid Phone Number";

    public static final String INVALID_EMAIL="CUS-UPDATE-FIE-003";
    public static final String INVALID_EMAIL_DESCRIPTION="Invalid EmailId";

    public static final String INVALID_LANGUAGE_INPUT=" CUS-UPDATE-FIE-004";
    public static final String INVALID_LANGUAGE_INPUT_DESCRIPTION="language should be from list of values";

    public static final String MANDATORY_FIELD="CUS-UPDATE-FIE-001";
    public static final String MANDATORY_FIELD_DESCRIPTION="Field Must be Not null";

    public static final String SECURITY_QUESTION_NOT_IN_TABLE="SQE-GET-FIE-001";
    public static final String SECURITY_QUESTION_NOT_IN_TABLE_DESCRIPTION="Security Question Should Present in Table";






}
