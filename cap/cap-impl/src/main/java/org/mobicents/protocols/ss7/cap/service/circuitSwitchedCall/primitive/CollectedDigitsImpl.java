/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2013, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive;

import java.io.IOException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.cap.api.primitives.ErrorTreatment;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CollectedDigits;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.cap.primitives.OctetStringBase;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class CollectedDigitsImpl implements CollectedDigits, CAPAsnPrimitive {

    public static final String MINIMUM_NB_OF_DIGITS = "minimumNbOfDigits";
    public static final String MAXIMUM_NB_OF_DIGITS = "maximumNbOfDigits";
    public static final String END_OF_REPLY_DIGIT = "endOfReplyDigit";
    public static final String CANCEL_DIGIT = "cancelDigit";
    public static final String START_DIGIT = "startDigit";
    public static final String FIRST_DIGIT_TIMEOUT = "firstDigitTimeOut";
    public static final String INTER_DIGIT_TIMEOUT = "interDigitTimeOut";
    public static final String ERROR_TREATMENT = "errorTreatment";
    public static final String INTERRUPTABLE_ANN_IND = "interruptableAnnInd";
    public static final String VOICE_INFORMATION = "voiceInformation";
    public static final String VOICE_BACK = "voiceBack";

    public static final int _ID_minimumNbOfDigits = 0;
    public static final int _ID_maximumNbOfDigits = 1;
    public static final int _ID_endOfReplyDigit = 2;
    public static final int _ID_cancelDigit = 3;
    public static final int _ID_startDigit = 4;
    public static final int _ID_firstDigitTimeOut = 5;
    public static final int _ID_interDigitTimeOut = 6;
    public static final int _ID_errorTreatment = 7;
    public static final int _ID_interruptableAnnInd = 8;
    public static final int _ID_voiceInformation = 9;
    public static final int _ID_voiceBack = 10;

    private static final String _PrimitiveName = "CollectedDigits";

    private Integer minimumNbOfDigits;
    private int maximumNbOfDigits;
    private byte[] endOfReplyDigit;
    private byte[] cancelDigit;
    private byte[] startDigit;
    private Integer firstDigitTimeOut;
    private Integer interDigitTimeOut;
    private ErrorTreatment errorTreatment;
    private Boolean interruptableAnnInd;
    private Boolean voiceInformation;
    private Boolean voiceBack;

    public CollectedDigitsImpl() {
    }

    public CollectedDigitsImpl(Integer minimumNbOfDigits,
            int maximumNbOfDigits, byte[] endOfReplyDigit, byte[] cancelDigit,
            byte[] startDigit, Integer firstDigitTimeOut,
            Integer interDigitTimeOut, ErrorTreatment errorTreatment,
            Boolean interruptableAnnInd, Boolean voiceInformation,
            Boolean voiceBack) {
        this.minimumNbOfDigits = minimumNbOfDigits;
        this.maximumNbOfDigits = maximumNbOfDigits;
        this.endOfReplyDigit = endOfReplyDigit;
        this.cancelDigit = cancelDigit;
        this.startDigit = startDigit;
        this.firstDigitTimeOut = firstDigitTimeOut;
        this.interDigitTimeOut = interDigitTimeOut;
        this.errorTreatment = errorTreatment;
        this.interruptableAnnInd = interruptableAnnInd;
        this.voiceInformation = voiceInformation;
        this.voiceBack = voiceBack;
    }

    @Override
    public Integer getMinimumNbOfDigits() {
        return minimumNbOfDigits;
    }

    @Override
    public int getMaximumNbOfDigits() {
        return maximumNbOfDigits;
    }

    @Override
    public byte[] getEndOfReplyDigit() {
        return endOfReplyDigit;
    }

    @Override
    public byte[] getCancelDigit() {
        return cancelDigit;
    }

    @Override
    public byte[] getStartDigit() {
        return startDigit;
    }

    @Override
    public Integer getFirstDigitTimeOut() {
        return firstDigitTimeOut;
    }

    @Override
    public Integer getInterDigitTimeOut() {
        return interDigitTimeOut;
    }

    @Override
    public ErrorTreatment getErrorTreatment() {
        return errorTreatment;
    }

    @Override
    public Boolean getInterruptableAnnInd() {
        return interruptableAnnInd;
    }

    @Override
    public Boolean getVoiceInformation() {
        return voiceInformation;
    }

    @Override
    public Boolean getVoiceBack() {
        return voiceBack;
    }

    @Override
    public int getTag() throws CAPException {
        return Tag.SEQUENCE;
    }

    @Override
    public int getTagClass() {
        return Tag.CLASS_UNIVERSAL;
    }

    @Override
    public boolean getIsPrimitive() {
        return false;
    }

    @Override
    public void decodeAll(AsnInputStream ansIS)
            throws CAPParsingComponentException {

        try {
            int length = ansIS.readLength();
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException("IOException when decoding "
                    + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException(
                    "AsnException when decoding " + _PrimitiveName + ": "
                            + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    @Override
    public void decodeData(AsnInputStream ansIS, int length)
            throws CAPParsingComponentException {

        try {
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException("IOException when decoding "
                    + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException(
                    "AsnException when decoding " + _PrimitiveName + ": "
                            + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    private void _decode(AsnInputStream ansIS, int length)
            throws CAPParsingComponentException, IOException, AsnException {

        this.minimumNbOfDigits = null;
        this.maximumNbOfDigits = -1;
        this.endOfReplyDigit = null;
        this.cancelDigit = null;
        this.startDigit = null;
        this.firstDigitTimeOut = null;
        this.interDigitTimeOut = null;
        this.errorTreatment = null;
        this.interruptableAnnInd = null;
        this.voiceInformation = null;
        this.voiceBack = null;
        // minimumNbOfDigits [0] INTEGER (1..30) DEFAULT 1,
        // maximumNbOfDigits [1] INTEGER (1..30),
        // endOfReplyDigit [2] OCTET STRING (SIZE (1..2)) OPTIONAL,
        // cancelDigit [3] OCTET STRING (SIZE (1..2)) OPTIONAL,
        // startDigit [4] OCTET STRING (SIZE (1..2)) OPTIONAL,
        // firstDigitTimeOut [5] INTEGER (1..127) OPTIONAL,
        // interDigitTimeOut [6] INTEGER (1..127) OPTIONAL,
        // errorTreatment [7] ErrorTreatment DEFAULT stdErrorAndInfo,
        // interruptableAnnInd [8] BOOLEAN DEFAULT TRUE,
        // voiceInformation [9] BOOLEAN DEFAULT FALSE,
        // voiceBack [10] BOOLEAN DEFAULT FALSE

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                case _ID_minimumNbOfDigits:
                    this.minimumNbOfDigits = (int) ais.readInteger();
                    break;
                case _ID_maximumNbOfDigits:
                    this.maximumNbOfDigits = (int) ais.readInteger();
                    break;
                case _ID_endOfReplyDigit:
                    this.endOfReplyDigit = ais.readOctetString();
                    break;
                case _ID_cancelDigit:
                    this.cancelDigit = ais.readOctetString();
                    break;
                case _ID_startDigit:
                    this.startDigit = ais.readOctetString();
                    break;
                case _ID_firstDigitTimeOut:
                    this.firstDigitTimeOut = (int) ais.readInteger();
                    break;
                case _ID_interDigitTimeOut:
                    this.interDigitTimeOut = (int) ais.readInteger();
                    break;
                case _ID_errorTreatment:
                    int i1 = (int) ais.readInteger();
                    this.errorTreatment = ErrorTreatment.getInstance(i1);
                    break;
                case _ID_interruptableAnnInd:
                    this.interruptableAnnInd = ais.readBoolean();
                    break;
                case _ID_voiceInformation:
                    this.voiceInformation = ais.readBoolean();
                    break;
                case _ID_voiceBack:
                    this.voiceBack = ais.readBoolean();
                    break;

                default:
                    ais.advanceElement();
                    break;
                }
            } else {
                ais.advanceElement();
            }
        }

        if (maximumNbOfDigits == -1)
            throw new CAPParsingComponentException("Error while decoding "
                    + _PrimitiveName
                    + ": maximumNbOfDigits is mandatory but not found",
                    CAPParsingComponentExceptionReason.MistypedParameter);
    }

    @Override
    public void encodeAll(AsnOutputStream asnOs) throws CAPException {
        this.encodeAll(asnOs, this.getTagClass(), this.getTag());
    }

    @Override
    public void encodeAll(AsnOutputStream asnOs, int tagClass, int tag)
            throws CAPException {

        try {
            asnOs.writeTag(tagClass, this.getIsPrimitive(), tag);
            int pos = asnOs.StartContentDefiniteLength();
            this.encodeData(asnOs);
            asnOs.FinalizeContent(pos);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding "
                    + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void encodeData(AsnOutputStream aos) throws CAPException {

        try {
            if (this.minimumNbOfDigits != null) {
                if (this.minimumNbOfDigits < 1 || this.minimumNbOfDigits > 30)
                    throw new CAPException(
                            "Error while encoding "
                                    + _PrimitiveName
                                    + ": minimumNbOfDigits must have value from 1 to 30");
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_minimumNbOfDigits, this.minimumNbOfDigits);
            }

            if (this.maximumNbOfDigits < 1 || this.maximumNbOfDigits > 30)
                throw new CAPException("Error while encoding " + _PrimitiveName
                        + ": maximumNbOfDigits must have value from 1 to 30");
            aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_maximumNbOfDigits,
                    this.maximumNbOfDigits);

            if (this.endOfReplyDigit != null) {
                if (this.endOfReplyDigit.length < 1
                        || this.endOfReplyDigit.length > 2)
                    throw new CAPException("Error while encoding "
                            + _PrimitiveName
                            + ": endOfReplyDigit length must be from 1 to 2");
                aos.writeOctetString(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_endOfReplyDigit, this.endOfReplyDigit);
            }
            if (this.cancelDigit != null) {
                if (this.cancelDigit.length < 1 || this.cancelDigit.length > 2)
                    throw new CAPException("Error while encoding "
                            + _PrimitiveName
                            + ": cancelDigit length must be from 1 to 2");
                aos.writeOctetString(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_cancelDigit, this.cancelDigit);
            }
            if (this.startDigit != null) {
                if (this.startDigit.length < 1 || this.startDigit.length > 2)
                    throw new CAPException("Error while encoding "
                            + _PrimitiveName
                            + ": startDigit length must be from 1 to 2");
                aos.writeOctetString(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_startDigit, this.startDigit);
            }
            if (this.firstDigitTimeOut != null) {
                if (this.firstDigitTimeOut < 1 || this.firstDigitTimeOut > 127)
                    throw new CAPException(
                            "Error while encoding "
                                    + _PrimitiveName
                                    + ": firstDigitTimeOut must have value from 1 to 127");
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_firstDigitTimeOut, this.firstDigitTimeOut);
            }
            if (this.interDigitTimeOut != null) {
                if (this.interDigitTimeOut < 1 || this.interDigitTimeOut > 127)
                    throw new CAPException(
                            "Error while encoding "
                                    + _PrimitiveName
                                    + ": interDigitTimeOut must have value from 1 to 127");
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_interDigitTimeOut, this.interDigitTimeOut);
            }
            if (this.errorTreatment != null)
                aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_errorTreatment, this.errorTreatment.getCode());
            if (this.interruptableAnnInd != null)
                aos.writeBoolean(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_interruptableAnnInd, this.interruptableAnnInd);
            if (this.voiceInformation != null)
                aos.writeBoolean(Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_voiceInformation, this.voiceInformation);
            if (this.voiceBack != null)
                aos.writeBoolean(Tag.CLASS_CONTEXT_SPECIFIC, _ID_voiceBack,
                        this.voiceBack);

        } catch (IOException e) {
            throw new CAPException("IOException when encoding "
                    + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding "
                    + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.minimumNbOfDigits != null) {
            sb.append("minimumNbOfDigits=");
            sb.append(this.minimumNbOfDigits);
        }
        sb.append(", maximumNbOfDigits=");
        sb.append(this.maximumNbOfDigits);
        if (this.endOfReplyDigit != null) {
            sb.append(", endOfReplyDigit=[");
            sb.append(printDataArr(this.endOfReplyDigit));
            sb.append("]");
        }
        if (this.cancelDigit != null) {
            sb.append(", cancelDigit=[");
            sb.append(printDataArr(this.cancelDigit));
            sb.append("]");
        }
        if (this.startDigit != null) {
            sb.append(", startDigit=[");
            sb.append(printDataArr(this.startDigit));
            sb.append("]");
        }
        if (this.firstDigitTimeOut != null) {
            sb.append(", firstDigitTimeOut=");
            sb.append(this.firstDigitTimeOut);
        }
        if (this.interDigitTimeOut != null) {
            sb.append(", interDigitTimeOut=");
            sb.append(this.interDigitTimeOut);
        }
        if (this.errorTreatment != null) {
            sb.append(", errorTreatment=");
            sb.append(this.errorTreatment.toString());
        }
        if (this.interruptableAnnInd != null && this.interruptableAnnInd) {
            sb.append(", interruptableAnnInd");
        }
        if (this.voiceInformation != null && this.voiceInformation) {
            sb.append(", voiceInformation");
        }
        if (this.voiceBack != null && this.voiceBack) {
            sb.append(", voiceBack");
        }

        sb.append("]");

        return sb.toString();
    }

    private String printDataArr(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int b : arr) {
            sb.append(b);
            sb.append(", ");
        }

        return sb.toString();
    }

    protected static final XMLFormat<CollectedDigitsImpl> COLLECTED_DIGITS_XML = new XMLFormat<CollectedDigitsImpl>(
            CollectedDigitsImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                CollectedDigitsImpl collectedDigits) throws XMLStreamException {

            collectedDigits.minimumNbOfDigits = xml.get(MINIMUM_NB_OF_DIGITS,
                    Integer.class);
            collectedDigits.maximumNbOfDigits = xml.get(MAXIMUM_NB_OF_DIGITS,
                    Integer.class);

            String endOFReplyDigit = xml.get(END_OF_REPLY_DIGIT, String.class);
            collectedDigits.endOfReplyDigit = endOFReplyDigit != null ? OctetStringBase
                    .hexToBytes(endOFReplyDigit) : null;

            String cancelDigit = xml.get(CANCEL_DIGIT, String.class);
            collectedDigits.cancelDigit = cancelDigit != null ? OctetStringBase
                    .hexToBytes(cancelDigit) : null;

            String startDigit = xml.get(START_DIGIT, String.class);
            collectedDigits.startDigit = startDigit != null ? OctetStringBase
                    .hexToBytes(startDigit) : null;

            collectedDigits.firstDigitTimeOut = xml.get(FIRST_DIGIT_TIMEOUT,
                    Integer.class);
            collectedDigits.interDigitTimeOut = xml.get(INTER_DIGIT_TIMEOUT,
                    Integer.class);

            String errorTreatment = xml.get(ERROR_TREATMENT, String.class);
            collectedDigits.errorTreatment = errorTreatment != null ? ErrorTreatment
                    .valueOf(errorTreatment) : ErrorTreatment.stdErrorAndInfo;
            collectedDigits.interruptableAnnInd = xml.get(
                    INTERRUPTABLE_ANN_IND, Boolean.class);
            collectedDigits.voiceInformation = xml.get(VOICE_INFORMATION,
                    Boolean.class);
            collectedDigits.voiceBack = xml.get(VOICE_BACK, Boolean.class);
        }

        @Override
        public void write(CollectedDigitsImpl obj,
                javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            // it has a default value, 1
            if (obj.getMinimumNbOfDigits() != null
                    && !obj.getMinimumNbOfDigits().equals(1)) {
                xml.add(obj.getMinimumNbOfDigits(), MINIMUM_NB_OF_DIGITS,
                        Integer.class);
            }
            xml.add(obj.getMaximumNbOfDigits(), MAXIMUM_NB_OF_DIGITS,
                    Integer.class);
            if (obj.getEndOfReplyDigit() != null) {
                xml.add(OctetStringBase.bytesToHex(obj.getEndOfReplyDigit()),
                        END_OF_REPLY_DIGIT, String.class);
            }
            if (obj.getCancelDigit() != null) {
                xml.add(OctetStringBase.bytesToHex(obj.getCancelDigit()),
                        CANCEL_DIGIT, String.class);
            }
            if (obj.getStartDigit() != null) {
                xml.add(OctetStringBase.bytesToHex(obj.getStartDigit()),
                        START_DIGIT, String.class);
            }

            if (obj.getFirstDigitTimeOut() != null) {
                xml.add(obj.getFirstDigitTimeOut(), FIRST_DIGIT_TIMEOUT,
                        Integer.class);
            }

            if (obj.getInterDigitTimeOut() != null) {
                xml.add(obj.getInterDigitTimeOut(), INTER_DIGIT_TIMEOUT,
                        Integer.class);
            }
            // it has a default value, stdErrorAndInfo
            if (obj.getErrorTreatment() != null
                    && obj.getErrorTreatment() != ErrorTreatment.stdErrorAndInfo) {
                xml.add(obj.getErrorTreatment().toString(), ERROR_TREATMENT,
                        String.class);
            }

            // it has a default value, true
            if (obj.getInterruptableAnnInd() != null
                    && !obj.getInterruptableAnnInd()) {
                xml.add(obj.getInterruptableAnnInd(), INTERRUPTABLE_ANN_IND,
                        Boolean.class);
            }

            // it has a default value, false
            if (obj.getVoiceInformation() != null && obj.getVoiceInformation()) {
                xml.add(obj.getVoiceInformation(), VOICE_INFORMATION,
                        Boolean.class);
            }

            // it has a default value, false
            if (obj.getVoiceBack() != null && obj.getVoiceBack()) {
                xml.add(obj.getVoiceBack(), VOICE_BACK, Boolean.class);
            }
        }

    };

    /*
     * TODO: move this code into the appropriate test class
    public static void main(String[] args) throws UnsupportedEncodingException,
            XMLStreamException {
        XMLObjectWriter x = new XMLObjectWriter().setBinding(new XMLBinding())
                .setOutput(System.out).setIndentation(" ");

        x.write(new CollectedDigitsImpl(null, 20, "#".getBytes(), "*"
                .getBytes(), "1".getBytes(), 5, 10,
                ErrorTreatment.stdErrorAndInfo, false, false, false),
                "collectedDigits");
        x.flush();

        String xml_1 = "<collectedDigits>"
                + "<minimumNbOfDigits value=\"10\"/>"
                + "<maximumNbOfDigits value=\"20\"/>"
                + "<endOfReplyDigit value=\"23\"/>"
                + "<cancelDigit value=\"2A\"/>" + "<startDigit value=\"31\"/>"
                + "<firstDigitTimeOut value=\"5\"/>"
                + "<interDigitTimeOut value=\"10\"/>"
                + "<errorTreatment value=\"stdErrorAndInfo\"/>"
                + "<interruptableAnnInd value=\"false\"/>"
                + "<voiceInformation value=\"false\"/>"
                + "<voiceBack value=\"false\"/>" + "</collectedDigits>";

        System.out.println("");
        XMLObjectReader r_1 = new XMLObjectReader().setInput(
                new ByteArrayInputStream(xml_1.getBytes(StandardCharsets.UTF_8
                        .name()))).setBinding(new XMLBinding());
        CollectedDigitsImpl readHere_1 = null;
        if (r_1.hasNext()) {
            readHere_1 = r_1.read("collectedDigits", CollectedDigitsImpl.class);
        }
        System.out.println("");
        System.out.println(readHere_1.toString());
    }
    */

}
