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
package org.mobicents.protocols.ss7.cap.EsiBcsm;

import java.io.IOException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.EsiBcsm.OTermSeizedSpecificInfo;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.LocationInformation;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberInformation.LocationInformationImpl;

/**
 * @author alerant appngin
 */
@SuppressWarnings("serial")
public class OTermSeizedSpecificInfoImpl extends SequenceBase implements OTermSeizedSpecificInfo {

    private static final String _PrimitiveName = "oTermSeizedSpecificInfo";

    private static final String LOCATION_INFORMATION = "locationInformation";

    public static final int _ID_locationInformation = 50;

    private LocationInformationImpl locationInformation;

    public OTermSeizedSpecificInfoImpl() {
        super(_PrimitiveName);
    }

    public OTermSeizedSpecificInfoImpl(LocationInformationImpl locationInformation) {
        super(_PrimitiveName);
        this.locationInformation = locationInformation;
    }

    @Override
    public LocationInformation getLocationInformation() {
        return locationInformation;
    }

    @Override
    public void encodeData(AsnOutputStream asnOs) throws CAPException {
        if (this.locationInformation != null) {
            try {
                this.locationInformation.encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC, _ID_locationInformation);
            } catch (MAPException e) {
                throw new CAPException("Failed to encode " + _PrimitiveName + "." + LOCATION_INFORMATION, e);
            }
        }
    }

    @Override
    protected void _decode(AsnInputStream asnIS, int length) throws IOException, AsnException,
            MAPParsingComponentException {

        this.locationInformation = null;

        AsnInputStream ais = asnIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                    case _ID_locationInformation:
                        this.locationInformation = new LocationInformationImpl();
                        this.locationInformation.decodeAll(ais);
                        break;

                    default:
                        ais.advanceElement();
                        break;
                }
            } else {
                ais.advanceElement();
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.locationInformation != null) {
            sb.append("locationInformation= [");
            sb.append(locationInformation.toString());
            sb.append("]");
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<OTermSeizedSpecificInfoImpl> O_TERM_SEIZED_SPECIFIC_INFO_XML = new XMLFormat<OTermSeizedSpecificInfoImpl>(
            OTermSeizedSpecificInfoImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, OTermSeizedSpecificInfoImpl oTermSeizedSpecificInfo)
                throws XMLStreamException {
            oTermSeizedSpecificInfo.locationInformation = xml.get(LOCATION_INFORMATION, LocationInformationImpl.class);
        }

        @Override
        public void write(OTermSeizedSpecificInfoImpl oTermSeizedSpecificInfo,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {

            if (oTermSeizedSpecificInfo.locationInformation != null) {
                xml.add(oTermSeizedSpecificInfo.locationInformation, LOCATION_INFORMATION,
                        LocationInformationImpl.class);
            }

        }
    };
}
