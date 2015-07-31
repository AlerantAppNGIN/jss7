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
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CollectedDigits;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CollectedInfo;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class CollectedInfoImpl implements CollectedInfo, CAPAsnPrimitive {

    private static final String COLLECTED_DIGITS = "collectedDigits";

    public static final int _ID_collectedDigits = 0;

    public static final String _PrimitiveName = "CollectedInfo";

    private CollectedDigits collectedDigits;

    public CollectedInfoImpl() {
    }

    public CollectedInfoImpl(CollectedDigits collectedDigits) {
        this.collectedDigits = collectedDigits;
    }

    @Override
    public CollectedDigits getCollectedDigits() {
        return collectedDigits;
    }

    @Override
    public int getTag() throws CAPException {

        if (this.collectedDigits != null) {
            return _ID_collectedDigits;
        } else {
            throw new CAPException("Error while encoding " + _PrimitiveName
                    + ": no of choices has been definite");
        }
    }

    @Override
    public int getTagClass() {
        return Tag.CLASS_CONTEXT_SPECIFIC;
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

    private void _decode(AsnInputStream ais, int length)
            throws CAPParsingComponentException, IOException, AsnException {

        this.collectedDigits = null;

        if (ais.getTagClass() != Tag.CLASS_CONTEXT_SPECIFIC
                || ais.isTagPrimitive())
            throw new CAPParsingComponentException("Error while decoding "
                    + _PrimitiveName + ": bad tagClass or is primitive",
                    CAPParsingComponentExceptionReason.MistypedParameter);

        switch (ais.getTag()) {
        case _ID_collectedDigits:
            this.collectedDigits = new CollectedDigitsImpl();
            ((CollectedDigitsImpl) this.collectedDigits)
                    .decodeData(ais, length);
            break;
        default:
            throw new CAPParsingComponentException("Error while decoding "
                    + _PrimitiveName + ": bad tag: " + ais.getTag(),
                    CAPParsingComponentExceptionReason.MistypedParameter);
        }
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
    public void encodeData(AsnOutputStream asnOs) throws CAPException {

        int choiceCnt = 0;
        if (this.collectedDigits != null)
            choiceCnt++;
        if (choiceCnt != 1)
            throw new CAPException("Error while encoding " + _PrimitiveName
                    + ": only one choice must be definite, found: " + choiceCnt);

        if (this.collectedDigits != null)
            ((CollectedDigitsImpl) this.collectedDigits).encodeData(asnOs);
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.collectedDigits != null) {
            sb.append("collectedDigits=");
            sb.append(collectedDigits.toString());
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((collectedDigits == null) ? 0 : collectedDigits.hashCode());
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
        CollectedInfoImpl other = (CollectedInfoImpl) obj;
        if (collectedDigits == null) {
            if (other.collectedDigits != null)
                return false;
        } else if (!collectedDigits.equals(other.collectedDigits))
            return false;
        return true;
    }

    protected static final XMLFormat<CollectedInfoImpl> COLLECTED_INFO_XML = new XMLFormat<CollectedInfoImpl>(
            CollectedInfoImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                CollectedInfoImpl collectedInfo) throws XMLStreamException {
            collectedInfo.collectedDigits = xml.get(COLLECTED_DIGITS,
                    CollectedDigitsImpl.class);
        }

        @Override
        public void write(CollectedInfoImpl obj,
                javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            xml.add((CollectedDigitsImpl) obj.getCollectedDigits(),
                    COLLECTED_DIGITS, CollectedDigitsImpl.class);

        }

    };

}
