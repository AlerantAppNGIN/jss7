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

package org.mobicents.protocols.ss7.cap.service.sms.primitive;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.cap.api.primitives.AppendFreeFormatData;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.FCIBCCCAMELsequence1SMS;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.FreeFormatDataSMS;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;
import org.mobicents.protocols.ss7.inap.api.INAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author Lasith Waruna Perera
 * @author alerant appngin
 */
@SuppressWarnings("serial")
public class FCIBCCCAMELsequence1SMSImpl extends SequenceBase implements FCIBCCCAMELsequence1SMS {

    private static final String FREE_FORMAT_DATA = "freeFormatData";
    private static final String APPEND_FREE_FORMAT_DATA = "appendFreeFormatData";

    public static final int _ID_freeFormatData = 0;
    public static final int _ID_appendFreeFormatData = 1;

    public static final int _ID_FCIBCCCAMELsequence1 = 0;

    private FreeFormatDataSMS freeFormatData;
    private AppendFreeFormatData appendFreeFormatData;

    public FCIBCCCAMELsequence1SMSImpl() {
        super("FCIBCCCAMELsequence1SMS");
    }

    public FCIBCCCAMELsequence1SMSImpl(FreeFormatDataSMS freeFormatData, AppendFreeFormatData appendFreeFormatData) {
        super("FCIBCCCAMELsequence1SMS");
        this.freeFormatData = freeFormatData;
        this.appendFreeFormatData = appendFreeFormatData;
    }

    @Override
    public FreeFormatDataSMS getFreeFormatData() {
        return this.freeFormatData;
    }

    @Override
    public AppendFreeFormatData getAppendFreeFormatData() {
        return this.appendFreeFormatData;
    }

    @Override
    public int getTag() throws CAPException {
        return _ID_FCIBCCCAMELsequence1;
    }

    @Override
    public int getTagClass() {
        return Tag.CLASS_CONTEXT_SPECIFIC;
    }

    @Override
    protected void _decode(AsnInputStream asnIS, int length) throws CAPParsingComponentException, IOException,
            AsnException, MAPParsingComponentException, INAPParsingComponentException {

        this.freeFormatData = null;
        this.appendFreeFormatData = AppendFreeFormatData.overwrite;

        AsnInputStream ais = asnIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                case _ID_freeFormatData:
                    if (!ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + ".freeFormatData: Parameter is not primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    this.freeFormatData = new FreeFormatDataSMSImpl();
                    ((FreeFormatDataSMSImpl) this.freeFormatData).decodeAll(ais);
                    break;
                case _ID_appendFreeFormatData:
                    if (!ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + ".appendFreeFormatData: Parameter is not primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    int i1 = (int) ais.readInteger();
                    this.appendFreeFormatData = AppendFreeFormatData.getInstance(i1);
                    break;
                default:
                    ais.advanceElement();
                    break;
                }
            } else {
                ais.advanceElement();
            }
        }

        if (this.freeFormatData == null)
            throw new CAPParsingComponentException(
                    "Error while decoding " + _PrimitiveName + ": parameter freeFormatData is mandatory but not found",
                    CAPParsingComponentExceptionReason.MistypedParameter);

    }

    @Override
    public void encodeData(AsnOutputStream asnOs) throws CAPException {

        if (this.freeFormatData == null)
            throw new CAPException("Error while encoding " + _PrimitiveName + ": freeFormatData must not be null");

        try {

            ((FreeFormatDataSMSImpl) this.freeFormatData).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC,
                    _ID_freeFormatData);

            if (this.appendFreeFormatData != null) {
                asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_appendFreeFormatData,
                        this.appendFreeFormatData.getCode());
            }

        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (IOException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.freeFormatData != null) {
            sb.append("freeFormatData=");
            sb.append(freeFormatData.toString());
        }
        if (this.appendFreeFormatData != null) {
            sb.append(", appendFreeFormatData=");
            sb.append(appendFreeFormatData.toString());
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appendFreeFormatData == null) ? 0 : appendFreeFormatData.hashCode());
        result = prime * result + ((freeFormatData == null) ? 0 : freeFormatData.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FCIBCCCAMELsequence1SMSImpl other = (FCIBCCCAMELsequence1SMSImpl) obj;
        if (appendFreeFormatData != other.appendFreeFormatData)
            return false;
        if (freeFormatData == null) {
            if (other.freeFormatData != null)
                return false;
        } else if (!freeFormatData.equals(other.freeFormatData))
            return false;
        return true;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<FCIBCCCAMELsequence1SMSImpl> FCI_BCC_CAMEL_SEQUENCE1_SMS_XML = new XMLFormat<FCIBCCCAMELsequence1SMSImpl>(
            FCIBCCCAMELsequence1SMSImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, FCIBCCCAMELsequence1SMSImpl fcibccSms)
                throws XMLStreamException {

            // default value is overwrite
            fcibccSms.appendFreeFormatData = AppendFreeFormatData
                    .valueOf(xml.getAttribute(APPEND_FREE_FORMAT_DATA, "overwrite"));

            fcibccSms.freeFormatData = xml.get(FREE_FORMAT_DATA, FreeFormatDataSMSImpl.class);
        }

        @Override
        public void write(FCIBCCCAMELsequence1SMSImpl fcibccSms, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            // write non-default value only
            if (fcibccSms.appendFreeFormatData != AppendFreeFormatData.overwrite) {
                xml.setAttribute(APPEND_FREE_FORMAT_DATA, fcibccSms.appendFreeFormatData.name());
            }

            xml.add((FreeFormatDataSMSImpl) fcibccSms.freeFormatData, FREE_FORMAT_DATA, FreeFormatDataSMSImpl.class);

        }
    };
}
