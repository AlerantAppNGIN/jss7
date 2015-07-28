package org.mobicents.protocols.ss7.cap.service.circuitSwitchedCall.primitive;

import java.io.IOException;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.BackwardServiceInteractionInd;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.CallCompletionTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.ConferenceTreatmentIndicator;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;

/**
 * Implementation class for {@link BackwardServiceInteractionInd}.
 *
 * @author tamas gyorgyey
 */
@SuppressWarnings("serial")
public class BackwardServiceInteractionIndImpl extends SequenceBase implements BackwardServiceInteractionInd,
        CAPAsnPrimitive {

    public static final int _ID_conferenceTreatmentIndicator = 1;
    public static final int _ID_callCompletionTreatmentIndicator = 2;

    private static final String CONFERENCE_TREATMENT_INDICATOR = "conferenceTreatmentIndicator";
    private static final String CALL_COMPLETION_TREATMENT_INDICATOR = "callCompletionTreatmentIndicator";

    public static final String _PrimitiveName = "BackwardServiceInteractionInd";

    private ConferenceTreatmentIndicator conferenceTreatmentIndicator;
    private CallCompletionTreatmentIndicator callCompletionTreatmentIndicator;

    public BackwardServiceInteractionIndImpl() {
        super(_PrimitiveName);
    }

    public BackwardServiceInteractionIndImpl(ConferenceTreatmentIndicator conferenceTreatmentIndicator,
            CallCompletionTreatmentIndicator callCompletionTreatmentIndicator) {
        super(_PrimitiveName);
        this.conferenceTreatmentIndicator = conferenceTreatmentIndicator;
        this.callCompletionTreatmentIndicator = callCompletionTreatmentIndicator;
    }

    @Override
    public ConferenceTreatmentIndicator getConferenceTreatmentIndicator() {
        return conferenceTreatmentIndicator;
    }

    @Override
    public CallCompletionTreatmentIndicator getCallCompletionTreatmentIndicator() {
        return callCompletionTreatmentIndicator;
    }

    @Override
    public void _decode(AsnInputStream ansIS, int length) throws AsnException, IOException {

        this.conferenceTreatmentIndicator = null;
        this.callCompletionTreatmentIndicator = null;

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {

                // these members are defined as OCTET STRING(SIZE(1)) and all values are contained in the 2 least
                // significant bits of the single octet. The resulting encoding is equivalent to a one octet integer,
                // where the getInstance() methods take care of discarding the higher bits (if present).

                switch (tag) {
                    case _ID_conferenceTreatmentIndicator:
                        this.conferenceTreatmentIndicator = ConferenceTreatmentIndicator.getInstance((int) ais
                                .readInteger());
                        break;
                    case _ID_callCompletionTreatmentIndicator:
                        this.callCompletionTreatmentIndicator = CallCompletionTreatmentIndicator.getInstance((int) ais
                                .readInteger());
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
    public void encodeData(AsnOutputStream asnOs) throws CAPException {
        try {
            if (conferenceTreatmentIndicator != null) {
                asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_conferenceTreatmentIndicator,
                        conferenceTreatmentIndicator.getCode());
            }
            if (callCompletionTreatmentIndicator != null) {
                asnOs.writeInteger(Tag.CLASS_CONTEXT_SPECIFIC, _ID_callCompletionTreatmentIndicator,
                        callCompletionTreatmentIndicator.getCode());
            }
        } catch (Exception e) {
            throw new CAPException("Error while encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    protected static final XMLFormat<BackwardServiceInteractionIndImpl> XML = new XMLFormat<BackwardServiceInteractionIndImpl>(
            BackwardServiceInteractionIndImpl.class) {
        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, BackwardServiceInteractionIndImpl obj)
                throws XMLStreamException {
            obj.conferenceTreatmentIndicator = xml.get(CONFERENCE_TREATMENT_INDICATOR,
                    ConferenceTreatmentIndicator.class);
            obj.callCompletionTreatmentIndicator = xml.get(CALL_COMPLETION_TREATMENT_INDICATOR,
                    CallCompletionTreatmentIndicator.class);
        }

        @Override
        public void write(BackwardServiceInteractionIndImpl obj, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.add(obj.conferenceTreatmentIndicator, CONFERENCE_TREATMENT_INDICATOR,
                    ConferenceTreatmentIndicator.class);
            xml.add(obj.callCompletionTreatmentIndicator, CALL_COMPLETION_TREATMENT_INDICATOR,
                    CallCompletionTreatmentIndicator.class);
        }
    };

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(_PrimitiveName).append(" [");
        if (conferenceTreatmentIndicator != null) {
            builder.append("conferenceTreatmentIndicator=");
            builder.append(conferenceTreatmentIndicator);
            builder.append(", ");
        }
        if (callCompletionTreatmentIndicator != null) {
            builder.append("callCompletionTreatmentIndicator=");
            builder.append(callCompletionTreatmentIndicator);
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((callCompletionTreatmentIndicator == null) ? 0 : callCompletionTreatmentIndicator.hashCode());
        result = prime * result
                + ((conferenceTreatmentIndicator == null) ? 0 : conferenceTreatmentIndicator.hashCode());
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
        BackwardServiceInteractionIndImpl other = (BackwardServiceInteractionIndImpl) obj;
        if (callCompletionTreatmentIndicator != other.callCompletionTreatmentIndicator)
            return false;
        if (conferenceTreatmentIndicator != other.conferenceTreatmentIndicator)
            return false;
        return true;
    }

}
