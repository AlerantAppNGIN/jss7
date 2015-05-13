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
import org.mobicents.protocols.ss7.cap.api.primitives.BurstList;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.AudibleIndicator;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;

/**
 * AudibleIndicator implementation class.
 *
 * @author alerant appngin
 */
@SuppressWarnings("serial")
public class AudibleIndicatorImpl implements AudibleIndicator, CAPAsnPrimitive {

    private static final String TONE = "tone";
    private static final String BURST_LIST = "burstList";

    public static final int _ID_tone = Tag.BOOLEAN; // universal primitive
    public static final int _ID_burstList = 1; // context-specific

    public static final String _PrimitiveName = "AudibleIndicator";

    private Boolean tone;
    private BurstList burstList;

    public AudibleIndicatorImpl() {
    }

    public AudibleIndicatorImpl(Boolean tone) {
        this.tone = tone;
    }

    public AudibleIndicatorImpl(BurstList burstList) {
        this.burstList = burstList;
    }

    public Boolean getTone() {
        return tone;
    }

    public BurstList getBurstList() {
        return burstList;
    }

    public int getTag() throws CAPException {
        if (tone != null)
            return _ID_tone;
        else if (burstList != null)
            return _ID_burstList;
        else
            throw new CAPException("Either tone or burstList must be present");
    }

    public int getTagClass() {
        if (tone != null)
            return Tag.CLASS_UNIVERSAL;
        else if (burstList != null)
            return Tag.CLASS_CONTEXT_SPECIFIC;
        else
            return -1;
    }

    public boolean getIsPrimitive() {
        return false;
    }

    public void decodeAll(AsnInputStream ansIS) throws CAPParsingComponentException {
        try {
            int length = ansIS.readLength();
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException("IOException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException("AsnException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    public void decodeData(AsnInputStream ansIS, int length) throws CAPParsingComponentException {

        try {
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException("IOException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException("AsnException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        }

    }

    private void _decode(AsnInputStream ais, int length) throws CAPParsingComponentException, IOException, AsnException {

        this.tone = null;
        this.burstList = null;

        int tag = ais.readTag();

        if (ais.getTagClass() == Tag.CLASS_UNIVERSAL && tag == _ID_tone) {
            if (!ais.isTagPrimitive())
                throw new CAPParsingComponentException("Error while decoding " + _PrimitiveName
                        + ".tone: Parameter should be primitive", CAPParsingComponentExceptionReason.MistypedParameter);

            this.tone = ais.readBoolean();

        } else if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC && tag == _ID_burstList) {
            if (ais.isTagPrimitive())
                throw new CAPParsingComponentException("Error while decoding " + _PrimitiveName
                        + ".burstList: Parameter should not be primitive",
                        CAPParsingComponentExceptionReason.MistypedParameter);

            this.burstList = new BurstListImpl();
            ((BurstListImpl) this.burstList).decodeAll(ais);
        }

    }

    public void encodeAll(AsnOutputStream asnOs) throws CAPException {
        this.encodeAll(asnOs, this.getTagClass(), this.getTag());
    }

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

    public void encodeData(AsnOutputStream asnOs) throws CAPException {

        if ((this.tone == null) == (this.burstList == null)) {
            throw new CAPException("Error while encoding " + _PrimitiveName
                    + ": One and only one choice must be selected");
        }

        try {
            if (this.tone != null) {
                asnOs.writeBoolean(getTagClass(), getTag(), tone);
            } else if (this.burstList != null) {
                ((CAPAsnPrimitive) this.burstList).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC, _ID_burstList);
            }
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

        if (this.tone != null) {
            sb.append("tone=").append(this.tone);
        } else if (this.burstList != null) {
            sb.append("burstList=").append(this.burstList);
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((burstList == null) ? 0 : burstList.hashCode());
        result = prime * result + ((tone == null) ? 0 : tone.hashCode());
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
        AudibleIndicatorImpl other = (AudibleIndicatorImpl) obj;
        if (burstList == null) {
            if (other.burstList != null)
                return false;
        } else if (!burstList.equals(other.burstList))
            return false;
        if (tone == null) {
            if (other.tone != null)
                return false;
        } else if (!tone.equals(other.tone))
            return false;
        return true;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<AudibleIndicatorImpl> AUDIBLE_INDICATOR_XML = new XMLFormat<AudibleIndicatorImpl>(
            AudibleIndicatorImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, AudibleIndicatorImpl audibleIndicator)
                throws XMLStreamException {

            audibleIndicator.tone = xml.get(TONE, Boolean.class);
            audibleIndicator.burstList = xml.get(BURST_LIST, BurstListImpl.class);
        }

        @Override
        public void write(AudibleIndicatorImpl audibleIndicator, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            if (audibleIndicator.tone != null) {
                xml.add(audibleIndicator.tone, TONE, Boolean.class);
            } else if (audibleIndicator.burstList != null) {
                xml.add((BurstListImpl) audibleIndicator.burstList, BURST_LIST, BurstListImpl.class);
            }
        }
    };

}
