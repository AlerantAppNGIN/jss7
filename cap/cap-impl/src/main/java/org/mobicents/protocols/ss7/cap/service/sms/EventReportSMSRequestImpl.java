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
package org.mobicents.protocols.ss7.cap.service.sms;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPMessageType;
import org.mobicents.protocols.ss7.cap.api.CAPOperationCode;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.cap.api.primitives.CAPExtensions;
import org.mobicents.protocols.ss7.cap.api.service.sms.EventReportSMSRequest;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.EventSpecificInformationSMS;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.EventTypeSMS;
import org.mobicents.protocols.ss7.cap.primitives.CAPExtensionsImpl;
import org.mobicents.protocols.ss7.cap.service.sms.primitive.EventSpecificInformationSMSImpl;
import org.mobicents.protocols.ss7.inap.api.INAPException;
import org.mobicents.protocols.ss7.inap.api.INAPParsingComponentException;
import org.mobicents.protocols.ss7.inap.api.primitives.MiscCallInfo;
import org.mobicents.protocols.ss7.inap.primitives.MiscCallInfoImpl;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class EventReportSMSRequestImpl extends SmsMessageImpl implements EventReportSMSRequest {

    public static final String _PrimitiveName = "EventReportSMSRequest";

    public static final int _ID_eventTypeSMS = 0;
    public static final int _ID_eventSpecificInformationSMS = 1;
    public static final int _ID_miscCallInfo = 2;
    public static final int _ID_extensions = 10;

    private static final String EVENT_TYPE_SMS = "eventTypeSms";
    private static final String EVENT_SPECIFIC_INFORMATION_SMS = "eventSpecificInformationSms";
    private static final String MISC_CALL_INFO = "miscCallInfo";
    private static final String EXTENSIONS = "extensions";

    private EventTypeSMS eventTypeSMS;
    private EventSpecificInformationSMS eventSpecificInformationSMS;
    private MiscCallInfo miscCallInfo;
    private CAPExtensions extensions;

    public EventReportSMSRequestImpl(EventTypeSMS eventTypeSMS, EventSpecificInformationSMS eventSpecificInformationSMS,
            MiscCallInfo miscCallInfo, CAPExtensions extensions) {
        super();
        this.eventTypeSMS = eventTypeSMS;
        this.eventSpecificInformationSMS = eventSpecificInformationSMS;
        this.miscCallInfo = miscCallInfo;
        this.extensions = extensions;
    }

    public EventReportSMSRequestImpl() {
        super();
    }

    @Override
    public EventTypeSMS getEventTypeSMS() {
        return this.eventTypeSMS;
    }

    @Override
    public EventSpecificInformationSMS getEventSpecificInformationSMS() {
        return this.eventSpecificInformationSMS;
    }

    @Override
    public MiscCallInfo getMiscCallInfo() {
        return this.miscCallInfo;
    }

    @Override
    public CAPExtensions getExtensions() {
        return this.extensions;
    }

    @Override
    public CAPMessageType getMessageType() {
        return CAPMessageType.eventReportSMS_Request;
    }

    @Override
    public int getOperationCode() {
        return CAPOperationCode.eventReportSMS;
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
    public void decodeAll(AsnInputStream ansIS) throws CAPParsingComponentException {
        try {
            int length = ansIS.readLength();
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException(
                    "IOException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException(
                    "AsnException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (MAPParsingComponentException e) {
            throw new CAPParsingComponentException(
                    "MAPParsingComponentException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (INAPParsingComponentException e) {
            throw new CAPParsingComponentException(
                    "INAPParsingComponentException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    @Override
    public void decodeData(AsnInputStream ansIS, int length) throws CAPParsingComponentException {
        try {
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException(
                    "IOException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException(
                    "AsnException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (MAPParsingComponentException e) {
            throw new CAPParsingComponentException(
                    "MAPParsingComponentException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (INAPParsingComponentException e) {
            throw new CAPParsingComponentException(
                    "INAPParsingComponentException when decoding " + _PrimitiveName + ": " + e.getMessage(), e,
                    CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    private void _decode(AsnInputStream ansIS, int length) throws CAPParsingComponentException, IOException,
            AsnException, MAPParsingComponentException, INAPParsingComponentException {

        this.eventTypeSMS = null;
        this.eventSpecificInformationSMS = null;
        this.miscCallInfo = null;
        this.extensions = null;

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                case _ID_eventTypeSMS:
                    if (!ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName + ".eventTypeSMS: Parameter is not primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    int i1 = (int) ais.readInteger();
                    this.eventTypeSMS = EventTypeSMS.getInstance(i1);
                    break;
                case _ID_eventSpecificInformationSMS:
                    if (ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + ".eventSpecificInformationSMS: Parameter is primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    this.eventSpecificInformationSMS = new EventSpecificInformationSMSImpl();
                    AsnInputStream ais3 = ais.readSequenceStream();
                    ais3.readTag();
                    ((EventSpecificInformationSMSImpl) this.eventSpecificInformationSMS).decodeAll(ais3);
                    break;
                case _ID_miscCallInfo:
                    if (ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName + ".miscCallInfo: Parameter is primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    this.miscCallInfo = new MiscCallInfoImpl();
                    ((MiscCallInfoImpl) this.miscCallInfo).decodeAll(ais);
                    break;
                case _ID_extensions:
                    if (ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName + ".extensions: Parameter is primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    this.extensions = new CAPExtensionsImpl();
                    ((CAPExtensionsImpl) this.extensions).decodeAll(ais);
                    break;

                default:
                    ais.advanceElement();
                    break;
                }
            } else {
                ais.advanceElement();
            }
        }

        if (this.eventTypeSMS == null) {
            throw new CAPParsingComponentException(
                    "Error while decoding " + _PrimitiveName + ": eventTypeSMS parameter mandatory but not found",
                    CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    @Override
    public void encodeAll(AsnOutputStream asnOs) throws CAPException {
        this.encodeAll(asnOs, this.getTagClass(), this.getTag());
    }

    @Override
    public void encodeAll(AsnOutputStream asnOs, int tagClass, int tag) throws CAPException {
        try {
            asnOs.writeTag(tagClass, this.getIsPrimitive(), tag);
            int pos = asnOs.StartContentDefiniteLength();
            this.encodeData(asnOs);
            asnOs.FinalizeContent(pos);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public void encodeData(AsnOutputStream asnOs) throws CAPException {

        if (this.eventTypeSMS == null)
            throw new CAPException("Error while encoding " + _PrimitiveName + ": eventTypeSMS must not be null");

        try {
            asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_eventTypeSMS, this.eventTypeSMS.getCode());

            if (this.eventSpecificInformationSMS != null) {
                try {
                    asnOs.writeTag(Tag.CLASS_CONTEXT_SPECIFIC, false, _ID_eventSpecificInformationSMS);
                    int pos = asnOs.StartContentDefiniteLength();
                    ((EventSpecificInformationSMSImpl) this.eventSpecificInformationSMS).encodeAll(asnOs);
                    asnOs.FinalizeContent(pos);
                } catch (AsnException e) {
                    throw new CAPException(
                            "AsnException while encoding " + _PrimitiveName + " parameter subscribedQoS");
                }
            }

            if (this.miscCallInfo != null)
                ((MiscCallInfoImpl) this.miscCallInfo).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC, _ID_miscCallInfo);

            if (this.extensions != null)
                ((CAPExtensionsImpl) this.extensions).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC, _ID_extensions);

        } catch (IOException e) {
            throw new CAPException("IOException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (INAPException e) {
            throw new CAPException("INAPException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventSpecificInformationSMS == null) ? 0 : eventSpecificInformationSMS.hashCode());
        result = prime * result + ((eventTypeSMS == null) ? 0 : eventTypeSMS.hashCode());
        result = prime * result + ((extensions == null) ? 0 : extensions.hashCode());
        result = prime * result + ((miscCallInfo == null) ? 0 : miscCallInfo.hashCode());
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
        EventReportSMSRequestImpl other = (EventReportSMSRequestImpl) obj;
        if (eventSpecificInformationSMS == null) {
            if (other.eventSpecificInformationSMS != null)
                return false;
        } else if (!eventSpecificInformationSMS.equals(other.eventSpecificInformationSMS))
            return false;
        if (eventTypeSMS != other.eventTypeSMS)
            return false;
        if (extensions == null) {
            if (other.extensions != null)
                return false;
        } else if (!extensions.equals(other.extensions))
            return false;
        if (miscCallInfo == null) {
            if (other.miscCallInfo != null)
                return false;
        } else if (!miscCallInfo.equals(other.miscCallInfo))
            return false;
        return true;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.eventTypeSMS != null) {
            sb.append("eventTypeSMS=");
            sb.append(eventTypeSMS.toString());
        }
        if (this.eventSpecificInformationSMS != null) {
            sb.append(", eventSpecificInformationSMS=");
            sb.append(eventSpecificInformationSMS.toString());
        }
        if (this.miscCallInfo != null) {
            sb.append(", miscCallInfo=");
            sb.append(miscCallInfo.toString());
        }
        if (this.extensions != null) {
            sb.append(", extensions=");
            sb.append(extensions.toString());
        }

        sb.append("]");

        return sb.toString();
    }

    protected static final XMLFormat<EventReportSMSRequestImpl> EVENT_REPORT_SMS_REQUEST_XML = new XMLFormat<EventReportSMSRequestImpl>(
            EventReportSMSRequestImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, EventReportSMSRequestImpl eventReportSmsRequest)
                throws XMLStreamException {
            CAP_MESSAGE_XML.read(xml, eventReportSmsRequest);

            String str = xml.get(EVENT_TYPE_SMS, String.class);
            if (str != null) {
                eventReportSmsRequest.eventTypeSMS = Enum.valueOf(EventTypeSMS.class, str);
            }
            eventReportSmsRequest.eventSpecificInformationSMS = xml.get(EVENT_SPECIFIC_INFORMATION_SMS,
                    EventSpecificInformationSMSImpl.class);
            eventReportSmsRequest.miscCallInfo = xml.get(MISC_CALL_INFO, MiscCallInfoImpl.class);
            eventReportSmsRequest.extensions = xml.get(EXTENSIONS, CAPExtensionsImpl.class);
        }

        @Override
        public void write(EventReportSMSRequestImpl eventReportSmsRequest, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            CAP_MESSAGE_XML.write(eventReportSmsRequest, xml);

            if (eventReportSmsRequest.getEventTypeSMS() != null) {
                xml.add(eventReportSmsRequest.eventTypeSMS.toString(), EVENT_TYPE_SMS, String.class);
            }
            if (eventReportSmsRequest.getEventSpecificInformationSMS() != null) {
                xml.add((EventSpecificInformationSMSImpl) eventReportSmsRequest.getEventSpecificInformationSMS(),
                        EVENT_SPECIFIC_INFORMATION_SMS, EventSpecificInformationSMSImpl.class);
            }
            if (eventReportSmsRequest.getMiscCallInfo() != null) {
                xml.add((MiscCallInfoImpl) eventReportSmsRequest.getMiscCallInfo(), MISC_CALL_INFO,
                        MiscCallInfoImpl.class);
            }
            if (eventReportSmsRequest.getExtensions() != null) {
                xml.add((CAPExtensionsImpl) eventReportSmsRequest.getExtensions(), EXTENSIONS, CAPExtensionsImpl.class);
            }
        }
    };

}
