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
import org.mobicents.protocols.ss7.cap.api.primitives.MonitorMode;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.EventTypeSMS;
import org.mobicents.protocols.ss7.cap.api.service.sms.primitive.SMSEvent;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

/**
 *
 * @author Lasith Waruna Perera
 *
 */
public class SMSEventImpl extends SequenceBase implements SMSEvent {

    public static final int _ID_eventTypeSMS = 0;
    public static final int _ID_monitorMode = 1;

    private EventTypeSMS eventTypeSMS;
    private MonitorMode monitorMode;

    private static final String EVENT_TYPE_SMS = "eventTypeSMS";
    private static final String MONITOR_MODE = "monitorMode";

    @Override
    public EventTypeSMS getEventTypeSMS() {
        return this.eventTypeSMS;
    }

    @Override
    public MonitorMode getMonitorMode() {
        return this.monitorMode;
    }

    public SMSEventImpl() {
        super("SMSEvent");
    }

    public SMSEventImpl(EventTypeSMS eventTypeSMS, MonitorMode monitorMode) {
        super("SMSEvent");
        this.eventTypeSMS = eventTypeSMS;
        this.monitorMode = monitorMode;
    }

    @Override
    protected void _decode(AsnInputStream asnIS, int length)
            throws CAPParsingComponentException, IOException, AsnException, MAPParsingComponentException {

        this.eventTypeSMS = null;
        this.monitorMode = null;

        AsnInputStream ais = asnIS.readSequenceStreamData(length);
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
                case _ID_monitorMode:
                    if (!ais.isTagPrimitive())
                        throw new CAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName + ".monitorMode: Parameter is not primitive",
                                CAPParsingComponentExceptionReason.MistypedParameter);
                    int i2 = (int) ais.readInteger();
                    this.monitorMode = MonitorMode.getInstance(i2);
                    break;
                default:
                    ais.advanceElement();
                    break;
                }
            } else {
                ais.advanceElement();
            }
        }

        if (this.eventTypeSMS == null)
            throw new CAPParsingComponentException(
                    "Error while decoding " + _PrimitiveName + ": parameter eventTypeSMS is mandatory but not found",
                    CAPParsingComponentExceptionReason.MistypedParameter);

        if (this.monitorMode == null)
            throw new CAPParsingComponentException(
                    "Error while decoding " + _PrimitiveName + ": parameter monitorMode is mandatory but not found",
                    CAPParsingComponentExceptionReason.MistypedParameter);

    }

    @Override
    public void encodeData(AsnOutputStream asnOs) throws CAPException {

        try {
            if (this.eventTypeSMS == null)
                throw new CAPException("Error while encoding " + _PrimitiveName + ": eventTypeSMS must not be null");

            if (this.monitorMode == null)
                throw new CAPException("Error while encoding " + _PrimitiveName + ": monitorMode must not be null");

            asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_eventTypeSMS, this.eventTypeSMS.getCode());

            asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_monitorMode, this.monitorMode.getCode());

        } catch (IOException e) {
            throw new CAPException("IOException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName + " [");

        if (this.eventTypeSMS != null) {
            sb.append("eventTypeSMS=");
            sb.append(this.eventTypeSMS.toString());
        }

        if (this.monitorMode != null) {
            sb.append(", monitorMode=");
            sb.append(this.monitorMode.toString());
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventTypeSMS == null) ? 0 : eventTypeSMS.hashCode());
        result = prime * result + ((monitorMode == null) ? 0 : monitorMode.hashCode());
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
        SMSEventImpl other = (SMSEventImpl) obj;
        if (eventTypeSMS != other.eventTypeSMS)
            return false;
        if (monitorMode != other.monitorMode)
            return false;
        return true;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<SMSEventImpl> SMS_EVENT_XML = new XMLFormat<SMSEventImpl>(SMSEventImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, SMSEventImpl smsEventImpl)
                throws XMLStreamException {
            String str = xml.get(EVENT_TYPE_SMS, String.class);
            if (str != null) {
                smsEventImpl.eventTypeSMS = Enum.valueOf(EventTypeSMS.class, str);
            }
            str = xml.get(MONITOR_MODE, String.class);
            if (str != null) {
                smsEventImpl.monitorMode = Enum.valueOf(MonitorMode.class, str);
            }
        }

        @Override
        public void write(SMSEventImpl smsEventImpl, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            if (smsEventImpl.getEventTypeSMS() != null) {
                xml.add((String) smsEventImpl.getEventTypeSMS().toString(), EVENT_TYPE_SMS, String.class);
            }
            if (smsEventImpl.getMonitorMode() != null) {
                xml.add((String) smsEventImpl.getMonitorMode().toString(), MONITOR_MODE, String.class);
            }
        }

    };

}
