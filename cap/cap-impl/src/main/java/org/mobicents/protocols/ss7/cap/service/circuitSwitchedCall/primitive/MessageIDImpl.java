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
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.MessageID;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.MessageIDText;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.VariableMessage;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;

/**
 *
 * @author sergey vetyutnev
 * @author kiss.balazs@alerant.hu
 *
 */
public class MessageIDImpl implements MessageID, CAPAsnPrimitive {

    private static final String ELEMENTARY_MESSAGE_ID = "elementaryMessageID";
    private static final String TEXT = "text";
    private static final String ELEMENTARY_MESSAGE_IDS = "elementaryMessageIDs";
    private static final String VARIABLE_MESSAGE = "variableMessage";

    public static final int _ID_elementaryMessageID = 0;
    public static final int _ID_text = 1;
    public static final int _ID_elementaryMessageIDs = 29;
    public static final int _ID_variableMessage = 30;

    public static final String _PrimitiveName = "MessageID";

    private Integer elementaryMessageID;
    private MessageIDText text;
    private ArrayList<Integer> elementaryMessageIDs;
    private VariableMessage variableMessage;

    public MessageIDImpl() {
    }

    public MessageIDImpl(Integer elementaryMessageID) {
        this.elementaryMessageID = elementaryMessageID;
    }

    public MessageIDImpl(MessageIDText text) {
        this.text = text;
    }

    public MessageIDImpl(ArrayList<Integer> elementaryMessageIDs) {
        this.elementaryMessageIDs = elementaryMessageIDs;
    }

    public MessageIDImpl(VariableMessage variableMessage) {
        this.variableMessage = variableMessage;
    }

    @Override
    public Integer getElementaryMessageID() {
        return elementaryMessageID;
    }

    @Override
    public MessageIDText getText() {
        return text;
    }

    @Override
    public ArrayList<Integer> getElementaryMessageIDs() {
        return elementaryMessageIDs;
    }

    @Override
    public VariableMessage getVariableMessage() {
        return variableMessage;
    }

    @Override
    public int getTag() throws CAPException {

        if (this.elementaryMessageID != null) {
            return _ID_elementaryMessageID;
        } else if (this.text != null) {
            return _ID_text;
        } else if (this.elementaryMessageIDs != null) {
            return _ID_elementaryMessageIDs;
        } else if (this.variableMessage != null) {
            return _ID_variableMessage;
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
        if (this.elementaryMessageID != null)
            return true;
        else
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

        this.elementaryMessageID = null;
        this.text = null;
        this.elementaryMessageIDs = null;
        this.variableMessage = null;

        if (ais.getTagClass() != Tag.CLASS_CONTEXT_SPECIFIC)
            throw new CAPParsingComponentException("Error while decoding "
                    + _PrimitiveName + ": bad tagClass",
                    CAPParsingComponentExceptionReason.MistypedParameter);

        switch (ais.getTag()) {
        case _ID_elementaryMessageID:
            this.elementaryMessageID = (int) ais.readIntegerData(length);
            break;
        case _ID_text:
            this.text = new MessageIDTextImpl();
            ((MessageIDTextImpl) this.text).decodeData(ais, length);
            break;
        case _ID_elementaryMessageIDs:
            this.elementaryMessageIDs = new ArrayList<Integer>();

            AsnInputStream ais2 = ais.readSequenceStreamData(length);
            while (true) {
                if (ais2.available() == 0)
                    break;

                int tag2 = ais2.readTag();
                if (ais2.getTagClass() != Tag.CLASS_UNIVERSAL
                        || tag2 != Tag.INTEGER || !ais2.isTagPrimitive())
                    throw new CAPParsingComponentException(
                            "Error while decoding "
                                    + _PrimitiveName
                                    + ": bad tagClass or tag or is not primitive when decoding an elementaryMessageIDs SEQUENCE",
                            CAPParsingComponentExceptionReason.MistypedParameter);

                Integer val = (int) ais2.readInteger();
                this.elementaryMessageIDs.add(val);
            }
            break;
        case _ID_variableMessage:
            this.variableMessage = new VariableMessageImpl();
            ((VariableMessageImpl) this.variableMessage)
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
        if (this.elementaryMessageID != null)
            choiceCnt++;
        if (this.text != null)
            choiceCnt++;
        if (this.elementaryMessageIDs != null)
            choiceCnt++;
        if (this.variableMessage != null)
            choiceCnt++;

        if (choiceCnt != 1)
            throw new CAPException("Error while encoding " + _PrimitiveName
                    + ": only one choice must be definite, found: " + choiceCnt);

        try {
            if (this.elementaryMessageID != null)
                asnOs.writeIntegerData(this.elementaryMessageID);
            if (this.text != null)
                ((MessageIDTextImpl) this.text).encodeData(asnOs);
            if (this.elementaryMessageIDs != null) {
                if (this.elementaryMessageIDs.size() < 1
                        || this.elementaryMessageIDs.size() > 16)
                    throw new CAPException(
                            "Error while encoding "
                                    + _PrimitiveName
                                    + ": elementaryMessageIDs count must be from 1 to 16, found: "
                                    + this.elementaryMessageIDs.size());

                for (Integer val : this.elementaryMessageIDs) {
                    if (val == null)
                        throw new CAPException(
                                "Error while encoding "
                                        + _PrimitiveName
                                        + ": the elementaryMessageIDs array has null values");
                    asnOs.writeInteger(val);
                }
            }
            if (this.variableMessage != null)
                ((VariableMessageImpl) this.variableMessage).encodeData(asnOs);
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

        if (this.elementaryMessageID != null) {
            sb.append("elementaryMessageID=");
            sb.append(elementaryMessageID);
        }
        if (this.text != null) {
            sb.append(" text=");
            sb.append(text.toString());
        }
        if (this.elementaryMessageIDs != null) {
            sb.append(" elementaryMessageIDs=[");
            for (Integer val : this.elementaryMessageIDs) {
                if (val != null) {
                    sb.append(val);
                    sb.append(", ");
                }
            }
            sb.append("]");
        }
        if (this.variableMessage != null) {
            sb.append(" variableMessage=");
            sb.append(variableMessage.toString());
        }

        sb.append("]");

        return sb.toString();
    }

    protected static final XMLFormat<MessageIDImpl> MESSAGE_ID_XML = new XMLFormat<MessageIDImpl>(
            MessageIDImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml,
                MessageIDImpl messageID) throws XMLStreamException {

            Integer elementaryMessageID = xml.get(ELEMENTARY_MESSAGE_ID,
                    Integer.class);
            if (elementaryMessageID != null) {
                if (xml.hasNext()) {
                    // we have more than one messageId - so it's messageIDs...
                    messageID.elementaryMessageIDs = new ArrayList<Integer>();
                    messageID.elementaryMessageIDs.add(elementaryMessageID);
                    while (xml.hasNext()) {
                        messageID.elementaryMessageIDs.add(xml.get(
                                ELEMENTARY_MESSAGE_ID, Integer.class));
                    }
                } else {
                    messageID.elementaryMessageID = elementaryMessageID;
                }
                return;
            }

            messageID.text = xml.get(TEXT, MessageIDTextImpl.class);
            if (messageID.text != null) {
                return;
            }

            messageID.variableMessage = xml.get(VARIABLE_MESSAGE,
                    VariableMessageImpl.class);
            if (messageID.variableMessage != null) {
                return;
            }
        }

        @Override
        public void write(MessageIDImpl obj,
                javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {

            if (obj.getElementaryMessageID() != null) {
                xml.add(obj.getElementaryMessageID(), ELEMENTARY_MESSAGE_ID,
                        Integer.class);
            }
            if (obj.getElementaryMessageIDs() != null) {
                for (Integer i : obj.getElementaryMessageIDs()) {
                    xml.add(i, ELEMENTARY_MESSAGE_ID, Integer.class);
                }
            }
            if (obj.getText() != null) {
                xml.add((MessageIDTextImpl) obj.getText(), TEXT,
                        MessageIDTextImpl.class);
            }
            if (obj.getVariableMessage() != null) {
                xml.add((VariableMessageImpl) obj.getVariableMessage(),
                        VARIABLE_MESSAGE, VariableMessageImpl.class);
            }

        }

    };

    /*
     * TODO: move this code into the appropriate test class
    public static void main(String[] args) throws UnsupportedEncodingException,
            XMLStreamException {
        XMLObjectWriter x = new XMLObjectWriter().setBinding(new XMLBinding())
                .setOutput(System.out).setIndentation(" ");
        MessageID messageID_1 = new MessageIDImpl(new Integer(134));

        x.write(messageID_1, "messageID");
        x.flush();

        String xml_1 = "<messageID>" + "<elementaryMessageID value=\"134\"/>"
                + "</messageID>";

        System.out.println("");
        XMLObjectReader r_1 = new XMLObjectReader().setInput(
                new ByteArrayInputStream(xml_1.getBytes(StandardCharsets.UTF_8
                        .name()))).setBinding(new XMLBinding());
        MessageIDImpl readHere_1 = null;
        if (r_1.hasNext()) {
            readHere_1 = r_1.read("messageID", MessageIDImpl.class);
        }
        System.out.println("");
        System.out.println(readHere_1.toString());

        // /////////////////////////////////////////////////////////////////

        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(new Integer(23));
        idList.add(new Integer(25));

        MessageID messageID_2 = new MessageIDImpl(idList);

        x.write(messageID_2, "messageID");
        x.flush();

        String xml_2 = "<messageID>" + "<elementaryMessageID value=\"23\"/>"
                + "<elementaryMessageID value=\"25\"/>" + "</messageID>";

        System.out.println("");
        XMLObjectReader r_2 = new XMLObjectReader().setInput(
                new ByteArrayInputStream(xml_2.getBytes(StandardCharsets.UTF_8
                        .name()))).setBinding(new XMLBinding());
        MessageIDImpl readHere_2 = null;
        if (r_2.hasNext()) {
            readHere_2 = r_2.read("messageID", MessageIDImpl.class);
        }
        System.out.println("");
        System.out.println(readHere_2.toString());

        ArrayList<VariablePart> vp = new ArrayList<VariablePart>();
        vp.add(new VariablePartImpl(new VariablePartDateImpl(2015, 6, 27)));
        vp.add(new VariablePartImpl(new VariablePartDateImpl(2015, 6, 28)));
        vp.add(new VariablePartImpl(new VariablePartPriceImpl(25000)));
        vp.add(new VariablePartImpl(new VariablePartTimeImpl(11, 30)));
        VariableMessageImpl vm = new VariableMessageImpl(256, vp);

        MessageID messageID_3 = new MessageIDImpl(vm);

        x.write(messageID_3, "messageID");
        x.flush();

        String xml_3 = "<messageID>" + "<variableMessage>"
                + "<elementaryMessageID value=\"256\"/>" + "<variablePart>"
                + "<date data=\"02516072\"/>" + "</variablePart>"
                + "<variablePart>" + "<date data=\"02516082\"/>"
                + "</variablePart>" + "<variablePart>"
                + "<price data=\"20050000\"/>" + "</variablePart>"
                + "<variablePart>" + "<time data=\"1103\"/>"
                + "</variablePart>" + "</variableMessage>" + "</messageID>";

        System.out.println("");
        XMLObjectReader r_3 = new XMLObjectReader().setInput(
                new ByteArrayInputStream(xml_3.getBytes(StandardCharsets.UTF_8
                        .name()))).setBinding(new XMLBinding());
        MessageIDImpl readHere_3 = null;
        if (r_3.hasNext()) {
            readHere_3 = r_3.read("messageID", MessageIDImpl.class);
        }
        System.out.println("");
        System.out.println(readHere_3.toString());
    }
    */
}
