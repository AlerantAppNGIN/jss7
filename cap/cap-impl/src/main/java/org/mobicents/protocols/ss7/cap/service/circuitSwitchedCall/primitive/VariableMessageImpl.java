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
import java.util.ArrayList;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariableMessage;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariablePart;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 */
public class VariableMessageImpl implements VariableMessage, CAPAsnPrimitive {

    private static final String ELEMENTARY_MESSAGE_ID = "elementaryMessageID";

    // variableParts are represented as sequence of variablePart elements
    private static final String VARIABLE_PART = "variablePart";

    public static final int _ID_elementaryMessageID = 0;
    public static final int _ID_variableParts = 1;

    public static final String _PrimitiveName = "VariableMessage";

    private int elementaryMessageID;
    private ArrayList<VariablePart> variableParts;

    public VariableMessageImpl() {
    }

    public VariableMessageImpl(int elementaryMessageID,
            ArrayList<VariablePart> variableParts) {
        this.elementaryMessageID = elementaryMessageID;
        this.variableParts = variableParts;
    }

    @Override
    public int getElementaryMessageID() {
        return elementaryMessageID;
    }

    @Override
    public ArrayList<VariablePart> getVariableParts() {
        return variableParts;
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

        this.elementaryMessageID = 0;
        this.variableParts = null;
        boolean elementaryMessageIDFound = false;

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                case _ID_elementaryMessageID:
                    this.elementaryMessageID = (int) ais.readInteger();
                    elementaryMessageIDFound = true;
                    break;
                case _ID_variableParts:
                    this.variableParts = new ArrayList<VariablePart>();

                    AsnInputStream ais2 = ais.readSequenceStream();
                    while (true) {
                        if (ais2.available() == 0)
                            break;

                        ais2.readTag();
                        VariablePartImpl val = new VariablePartImpl();
                        val.decodeAll(ais2);
                        this.variableParts.add(val);
                    }
                    break;

                default:
                    ais.advanceElement();
                    break;
                }
            } else {
                ais.advanceElement();
            }
        }

        if (this.variableParts == null || !elementaryMessageIDFound)
            throw new CAPParsingComponentException(
                    "Error while decoding "
                            + _PrimitiveName
                            + ": elementaryMessageID and variableParts are mandatory but not found",
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

        if (this.variableParts == null)
            throw new CAPException("Error while encoding " + _PrimitiveName
                    + ": variableParts must not be null");
        if (this.variableParts.size() < 1 || this.variableParts.size() > 5)
            throw new CAPException("Error while encoding " + _PrimitiveName
                    + ": variableParts size must not be from 1 to 5, found: "
                    + this.variableParts.size());

        try {
            aos.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC,
                    _ID_elementaryMessageID, this.elementaryMessageID);

            aos.writeTag(Tag.CLASS_CONTEXT_SPECIFIC, false, _ID_variableParts);
            int pos = aos.StartContentDefiniteLength();
            for (VariablePart val : this.variableParts) {
                if (val == null)
                    throw new CAPException("Error while encoding "
                            + _PrimitiveName
                            + ": the variableParts array has null values");
                ((VariablePartImpl) val).encodeAll(aos);
            }
            aos.FinalizeContent(pos);

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

        sb.append("elementaryMessageID=");
        sb.append(elementaryMessageID);
        if (this.variableParts != null) {
            sb.append(", variableParts=[");
            for (VariablePart val : this.variableParts) {
                if (val != null) {
                    sb.append("variablePart=[");
                    sb.append(val.toString());
                    sb.append("], ");
                }
            }
            sb.append("]");
        }

        sb.append("]");

        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + elementaryMessageID;
        result = prime * result
                + ((variableParts == null) ? 0 : variableParts.hashCode());
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
        VariableMessageImpl other = (VariableMessageImpl) obj;
        if (elementaryMessageID != other.elementaryMessageID)
            return false;
        if (variableParts == null) {
            if (other.variableParts != null)
                return false;
        } else if (!variableParts.equals(other.variableParts))
            return false;
        return true;
    }

    protected static final XMLFormat<VariableMessageImpl> VARIABLE_MESSAGE_XML = new XMLFormat<VariableMessageImpl>(
            VariableMessageImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                VariableMessageImpl variableMessage) throws XMLStreamException {

            variableMessage.elementaryMessageID = xml.get(
                    ELEMENTARY_MESSAGE_ID, Integer.class);
            variableMessage.variableParts = new ArrayList<VariablePart>();
            while (xml.hasNext()) {
                VariablePartImpl vp = xml.get("variablePart",
                        VariablePartImpl.class);
                variableMessage.variableParts.add(vp);
            }

        }

        @Override
        public void write(VariableMessageImpl obj,
                javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            xml.add(obj.getElementaryMessageID(), ELEMENTARY_MESSAGE_ID,
                    Integer.class);

            for (VariablePart vp : obj.getVariableParts()) {
                xml.add((VariablePartImpl) vp, VARIABLE_PART,
                        VariablePartImpl.class);
            }

        }

    };

}
