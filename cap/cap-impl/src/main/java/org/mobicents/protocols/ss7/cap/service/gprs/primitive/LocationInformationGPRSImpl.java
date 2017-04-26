package org.mobicents.protocols.ss7.cap.service.gprs.primitive;

import java.io.IOException;

import org.mobicents.protocols.asn.AsnException;
import org.mobicents.protocols.asn.AsnInputStream;
import org.mobicents.protocols.asn.AsnOutputStream;
import org.mobicents.protocols.asn.Tag;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.service.gprs.primitive.LocationInformationGPRS;
import org.mobicents.protocols.ss7.cap.primitives.SequenceBase;
import org.mobicents.protocols.ss7.inap.api.INAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.map.api.primitives.CellGlobalIdOrServiceAreaIdOrLAI;
import org.mobicents.protocols.ss7.map.api.primitives.ISDNAddressString;
import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.GeographicalInformation;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.RAIdentity;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.UserCSGInformation;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.LSAIdentity;
import org.mobicents.protocols.ss7.map.primitives.CellGlobalIdOrServiceAreaIdFixedLengthImpl;
import org.mobicents.protocols.ss7.map.primitives.CellGlobalIdOrServiceAreaIdOrLAIImpl;
import org.mobicents.protocols.ss7.map.primitives.ISDNAddressStringImpl;
import org.mobicents.protocols.ss7.map.primitives.LAIFixedLengthImpl;
import org.mobicents.protocols.ss7.map.primitives.MAPExtensionContainerImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberInformation.GeographicalInformationImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberInformation.RAIdentityImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberInformation.UserCSGInformationImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberManagement.LSAIdentityImpl;

import javolution.xml.XMLFormat;
import javolution.xml.stream.XMLStreamException;

public class LocationInformationGPRSImpl extends SequenceBase implements LocationInformationGPRS {

    private static final int _ID_cellGlobalIdOrServiceAreaIdOrLAI = 0;
    private static final int _ID_routeingAreaIdentity = 1;
    private static final int _ID_geographicalInformation = 2;
    private static final int _ID_sgsnNumber = 3;
    private static final int _ID_selectedLSAIdentity = 4;
    private static final int _ID_extensionContainer = 5;
    private static final int _ID_sai_Present = 6;
    private static final int _ID_userCSGInformation = 7;

    private CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI = null;
    private RAIdentity routeingAreaIdentity = null;
    private GeographicalInformation geographicalInformation = null;
    private ISDNAddressString sGSNNumber = null;
    private LSAIdentity selectedLSAIdentity = null;
    private MAPExtensionContainer extensionContainer = null;
    private boolean saiPresent = false;
    private UserCSGInformation userCSGInformation = null;

    private static final String CELLGLOBALID_OR_SERVICEAREAID_OR_LAI = "cellGlobalIdOrServiceAreaIdOrLAI";
    private static final String ROUTEING_AREA_IDENTITY = "routeingAreaIdentity";
    private static final String GEOGRAPHICAL_INFORMATION = "geographicalInformation";
    private static final String SGSN_NUMBER = "sGSNNumber";
    private static final String SELECTED_LSA_IDENTITY = "selectedLSAIdentity";
    private static final String EXTENSION_CONTAINER = "extensionContainer";
    private static final String SAI_PRESENT = "saiPresent";
    private static final String USER_CSG_INFORMATION = "userCSGInformation";

    public LocationInformationGPRSImpl() {
        super("LocationInformationGPRS");
    }

    public LocationInformationGPRSImpl(CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI,
            RAIdentity routeingAreaIdentity, GeographicalInformation geographicalInformation,
            ISDNAddressString sGSNNumber, LSAIdentity selectedLSAIdentity, MAPExtensionContainer extensionContainer,
            boolean saiPresent, UserCSGInformation userCSGInformation) {
        super("LocationInformationGPRS");
        this.cellGlobalIdOrServiceAreaIdOrLAI = cellGlobalIdOrServiceAreaIdOrLAI;
        this.routeingAreaIdentity = routeingAreaIdentity;
        this.geographicalInformation = geographicalInformation;
        this.sGSNNumber = sGSNNumber;
        this.selectedLSAIdentity = selectedLSAIdentity;
        this.extensionContainer = extensionContainer;
        this.saiPresent = saiPresent;
        this.userCSGInformation = userCSGInformation;
    }

    @Override
    public CellGlobalIdOrServiceAreaIdOrLAI getCellGlobalIdOrServiceAreaIdOrLAI() {
        return this.cellGlobalIdOrServiceAreaIdOrLAI;
    }

    @Override
    public RAIdentity getRouteingAreaIdentity() {
        return this.routeingAreaIdentity;
    }

    @Override
    public GeographicalInformation getGeographicalInformation() {
        return this.geographicalInformation;
    }

    @Override
    public ISDNAddressString getSGSNNumber() {
        return this.sGSNNumber;
    }

    @Override
    public LSAIdentity getSelectedLSAIdentity() {
        return this.selectedLSAIdentity;
    }

    @Override
    public MAPExtensionContainer getExtensionContainer() {
        return this.extensionContainer;
    }

    @Override
    public boolean isSaiPresent() {
        return this.saiPresent;
    }

    @Override
    public UserCSGInformation getUserCSGInformation() {
        return this.userCSGInformation;
    }

    public static CellGlobalIdOrServiceAreaIdOrLAI decodeCellGlobalIdOrServiceAreaIdOrLAI(AsnInputStream ais,
            String primitiveName) throws MAPParsingComponentException, AsnException, IOException {
        if (ais.isTagPrimitive()) {
            // nonstandard case when there is no external container
            int len = ais.readLength();
            if (len == 7) {
                CellGlobalIdOrServiceAreaIdFixedLengthImpl val = new CellGlobalIdOrServiceAreaIdFixedLengthImpl();
                val.decodeData(ais, len);
                CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI = new CellGlobalIdOrServiceAreaIdOrLAIImpl(
                        val);
                return cellGlobalIdOrServiceAreaIdOrLAI;
            } else if (len == 5) {
                LAIFixedLengthImpl val = new LAIFixedLengthImpl();
                val.decodeData(ais, len);
                CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI = new CellGlobalIdOrServiceAreaIdOrLAIImpl(
                        val);
                return cellGlobalIdOrServiceAreaIdOrLAI;
            } else {
                throw new MAPParsingComponentException(
                        "Error while decoding " + primitiveName
                                + " cellGlobalIdOrServiceAreaIdOrLAI: Parameter length must be 5 or 7",
                        MAPParsingComponentExceptionReason.MistypedParameter);
            }
        } else {
            CellGlobalIdOrServiceAreaIdOrLAI cellGlobalIdOrServiceAreaIdOrLAI = new CellGlobalIdOrServiceAreaIdOrLAIImpl();
            AsnInputStream ais2 = ais.readSequenceStream();
            ais2.readTag();
            ((CellGlobalIdOrServiceAreaIdOrLAIImpl) cellGlobalIdOrServiceAreaIdOrLAI).decodeAll(ais2);
            return cellGlobalIdOrServiceAreaIdOrLAI;
        }
    }

    @Override
    protected void _decode(AsnInputStream asnIS, int length) throws CAPParsingComponentException, IOException,
            AsnException, MAPParsingComponentException, INAPParsingComponentException {

        this.cellGlobalIdOrServiceAreaIdOrLAI = null;
        this.routeingAreaIdentity = null;
        this.geographicalInformation = null;
        this.sGSNNumber = null;
        this.selectedLSAIdentity = null;
        this.extensionContainer = null;
        this.saiPresent = false;
        this.userCSGInformation = null;

        AsnInputStream ais = asnIS.readSequenceStreamData(length);

        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            // optional parameters
            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {

                switch (tag) {
                case _ID_cellGlobalIdOrServiceAreaIdOrLAI:
                    this.cellGlobalIdOrServiceAreaIdOrLAI = decodeCellGlobalIdOrServiceAreaIdOrLAI(ais, _PrimitiveName);
                    break;
                case _ID_routeingAreaIdentity:
                    if (!ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + " routeingAreaIdentity: Parameter is not primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    this.routeingAreaIdentity = new RAIdentityImpl();
                    ((RAIdentityImpl) this.routeingAreaIdentity).decodeAll(ais);
                    break;
                case _ID_geographicalInformation:
                    if (!ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + " geographicalInformation: Parameter is not primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    this.geographicalInformation = new GeographicalInformationImpl();
                    ((GeographicalInformationImpl) this.geographicalInformation).decodeAll(ais);
                    break;
                case _ID_sgsnNumber:
                    if (!ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName + " sgsnNumber: Parameter is not primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    this.sGSNNumber = new ISDNAddressStringImpl();
                    ((ISDNAddressStringImpl) this.sGSNNumber).decodeAll(ais);
                    break;

                case _ID_selectedLSAIdentity:
                    if (!ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + " selectedLSAIdentity: Parameter is not primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    this.selectedLSAIdentity = new LSAIdentityImpl();
                    ((LSAIdentityImpl) this.selectedLSAIdentity).decodeAll(ais);
                    break;

                case _ID_extensionContainer:
                    if (ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + " extensionContainer: Parameter is primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    this.extensionContainer = new MAPExtensionContainerImpl();
                    ((MAPExtensionContainerImpl) this.extensionContainer).decodeAll(ais);
                    break;
                case _ID_sai_Present:
                    if (!ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName + " saiPresent: Parameter is not primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    ais.readNull();
                    this.saiPresent = true;
                    break;
                case _ID_userCSGInformation:
                    if (ais.isTagPrimitive())
                        throw new MAPParsingComponentException(
                                "Error while decoding " + _PrimitiveName
                                        + " userCSGInformation: Parameter is primitive",
                                MAPParsingComponentExceptionReason.MistypedParameter);
                    this.userCSGInformation = new UserCSGInformationImpl();
                    ((UserCSGInformationImpl) this.userCSGInformation).decodeAll(ais);
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

            if (this.cellGlobalIdOrServiceAreaIdOrLAI != null) {
                asnOs.writeTag(Tag.CLASS_CONTEXT_SPECIFIC, false, _ID_cellGlobalIdOrServiceAreaIdOrLAI);
                int pos = asnOs.StartContentDefiniteLength();
                ((CellGlobalIdOrServiceAreaIdOrLAIImpl) this.cellGlobalIdOrServiceAreaIdOrLAI).encodeAll(asnOs);
                asnOs.FinalizeContent(pos);
            }

            if (this.routeingAreaIdentity != null) {
                ((RAIdentityImpl) this.routeingAreaIdentity).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_routeingAreaIdentity);
            }

            if (this.geographicalInformation != null)
                ((GeographicalInformationImpl) this.geographicalInformation).encodeAll(asnOs,
                        Tag.CLASS_CONTEXT_SPECIFIC, _ID_geographicalInformation);

            if (this.sGSNNumber != null)
                ((ISDNAddressStringImpl) this.sGSNNumber).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC, _ID_sgsnNumber);

            if (this.selectedLSAIdentity != null)
                ((LSAIdentityImpl) this.selectedLSAIdentity).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_selectedLSAIdentity);

            if (this.extensionContainer != null)
                ((MAPExtensionContainerImpl) this.extensionContainer).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_extensionContainer);

            if (this.saiPresent) {
                try {
                    asnOs.writeNull(Tag.CLASS_CONTEXT_SPECIFIC, _ID_sai_Present);
                } catch (IOException e) {
                    throw new MAPException(
                            "Error while encoding LocationInformation the optional parameter sai-Present encoding failed ",
                            e);
                } catch (AsnException e) {
                    throw new MAPException(
                            "Error while encoding LocationInformation the optional parameter sai-Present encoding failed ",
                            e);
                }
            }

            if (this.userCSGInformation != null) {
                ((UserCSGInformationImpl) this.userCSGInformation).encodeAll(asnOs, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_userCSGInformation);
            }

        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (MAPException e) {
            throw new CAPException("MAPException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.cellGlobalIdOrServiceAreaIdOrLAI != null) {
            sb.append("cellGlobalIdOrServiceAreaIdOrLAI=");
            sb.append(this.cellGlobalIdOrServiceAreaIdOrLAI);
        }

        if (this.routeingAreaIdentity != null) {
            sb.append(", routeingAreaIdentity=");
            sb.append(this.routeingAreaIdentity);
        }

        if (this.geographicalInformation != null) {
            sb.append(", geographicalInformation=");
            sb.append(this.geographicalInformation);
        }

        if (this.sGSNNumber != null) {
            sb.append(", sGSNNumber=");
            sb.append(this.sGSNNumber);
        }

        if (this.selectedLSAIdentity != null) {
            sb.append(", selectedLSAIdentity=");
            sb.append(this.selectedLSAIdentity);
        }

        if (this.extensionContainer != null) {
            sb.append(", extensionContainer=");
            sb.append(this.extensionContainer);
        }

        if (this.saiPresent) {
            sb.append(", saiPresent");
        }

        if (this.userCSGInformation != null) {
            sb.append(", userCSGInformation=");
            sb.append(this.userCSGInformation);
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((cellGlobalIdOrServiceAreaIdOrLAI == null) ? 0 : cellGlobalIdOrServiceAreaIdOrLAI.hashCode());
        result = prime * result + ((extensionContainer == null) ? 0 : extensionContainer.hashCode());
        result = prime * result + ((geographicalInformation == null) ? 0 : geographicalInformation.hashCode());
        result = prime * result + ((routeingAreaIdentity == null) ? 0 : routeingAreaIdentity.hashCode());
        result = prime * result + ((sGSNNumber == null) ? 0 : sGSNNumber.hashCode());
        result = prime * result + (saiPresent ? 1231 : 1237);
        result = prime * result + ((selectedLSAIdentity == null) ? 0 : selectedLSAIdentity.hashCode());
        result = prime * result + ((userCSGInformation == null) ? 0 : userCSGInformation.hashCode());
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
        LocationInformationGPRSImpl other = (LocationInformationGPRSImpl) obj;
        if (cellGlobalIdOrServiceAreaIdOrLAI == null) {
            if (other.cellGlobalIdOrServiceAreaIdOrLAI != null)
                return false;
        } else if (!cellGlobalIdOrServiceAreaIdOrLAI.equals(other.cellGlobalIdOrServiceAreaIdOrLAI))
            return false;
        if (extensionContainer == null) {
            if (other.extensionContainer != null)
                return false;
        } else if (!extensionContainer.equals(other.extensionContainer))
            return false;
        if (geographicalInformation == null) {
            if (other.geographicalInformation != null)
                return false;
        } else if (!geographicalInformation.equals(other.geographicalInformation))
            return false;
        if (routeingAreaIdentity == null) {
            if (other.routeingAreaIdentity != null)
                return false;
        } else if (!routeingAreaIdentity.equals(other.routeingAreaIdentity))
            return false;
        if (sGSNNumber == null) {
            if (other.sGSNNumber != null)
                return false;
        } else if (!sGSNNumber.equals(other.sGSNNumber))
            return false;
        if (saiPresent != other.saiPresent)
            return false;
        if (selectedLSAIdentity == null) {
            if (other.selectedLSAIdentity != null)
                return false;
        } else if (!selectedLSAIdentity.equals(other.selectedLSAIdentity))
            return false;
        if (userCSGInformation == null) {
            if (other.userCSGInformation != null)
                return false;
        } else if (!userCSGInformation.equals(other.userCSGInformation))
            return false;
        return true;
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<LocationInformationGPRSImpl> LOCATION_INFORMATION_GPRS_XML = new XMLFormat<LocationInformationGPRSImpl>(
            LocationInformationGPRSImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, LocationInformationGPRSImpl locationInformationGPRS)
                throws XMLStreamException {
            locationInformationGPRS.cellGlobalIdOrServiceAreaIdOrLAI = xml.get(CELLGLOBALID_OR_SERVICEAREAID_OR_LAI,
                    CellGlobalIdOrServiceAreaIdOrLAIImpl.class);
            locationInformationGPRS.routeingAreaIdentity = xml.get(ROUTEING_AREA_IDENTITY, RAIdentityImpl.class);
            locationInformationGPRS.geographicalInformation = xml.get(GEOGRAPHICAL_INFORMATION,
                    GeographicalInformationImpl.class);
            locationInformationGPRS.sGSNNumber = xml.get(SGSN_NUMBER, ISDNAddressStringImpl.class);
            locationInformationGPRS.selectedLSAIdentity = xml.get(SELECTED_LSA_IDENTITY, LSAIdentityImpl.class);
            locationInformationGPRS.extensionContainer = xml.get(EXTENSION_CONTAINER, MAPExtensionContainerImpl.class);
            Boolean saiPresentB = xml.get(SAI_PRESENT, Boolean.class);
            if (saiPresentB != null) {
                locationInformationGPRS.saiPresent = saiPresentB;
            }
            locationInformationGPRS.userCSGInformation = xml.get(USER_CSG_INFORMATION, UserCSGInformationImpl.class);
        }

        @Override
        public void write(LocationInformationGPRSImpl locationInformationGPRS,
                javolution.xml.XMLFormat.OutputElement xml) throws XMLStreamException {
            if (locationInformationGPRS.getCellGlobalIdOrServiceAreaIdOrLAI() != null) {
                xml.add((CellGlobalIdOrServiceAreaIdOrLAIImpl) locationInformationGPRS
                        .getCellGlobalIdOrServiceAreaIdOrLAI(), CELLGLOBALID_OR_SERVICEAREAID_OR_LAI,
                        CellGlobalIdOrServiceAreaIdOrLAIImpl.class);
            }
            if (locationInformationGPRS.getRouteingAreaIdentity() != null) {
                xml.add((RAIdentityImpl) locationInformationGPRS.getRouteingAreaIdentity(), ROUTEING_AREA_IDENTITY,
                        RAIdentityImpl.class);
            }
            if (locationInformationGPRS.getGeographicalInformation() != null) {
                xml.add((GeographicalInformationImpl) locationInformationGPRS.getGeographicalInformation(),
                        GEOGRAPHICAL_INFORMATION, GeographicalInformationImpl.class);
            }
            if (locationInformationGPRS.getSGSNNumber() != null) {
                xml.add((ISDNAddressStringImpl) locationInformationGPRS.getSGSNNumber(), SGSN_NUMBER,
                        ISDNAddressStringImpl.class);
            }
            if (locationInformationGPRS.getSelectedLSAIdentity() != null) {
                xml.add((LSAIdentityImpl) locationInformationGPRS.getSelectedLSAIdentity(), SELECTED_LSA_IDENTITY,
                        LSAIdentityImpl.class);
            }
            if (locationInformationGPRS.getExtensionContainer() != null) {
                xml.add((MAPExtensionContainerImpl) locationInformationGPRS.getExtensionContainer(),
                        EXTENSION_CONTAINER, MAPExtensionContainerImpl.class);
            }
            if (locationInformationGPRS.isSaiPresent()) {
                xml.add((Boolean) locationInformationGPRS.isSaiPresent(), SAI_PRESENT, Boolean.class);
            }
            if (locationInformationGPRS.getUserCSGInformation() != null) {
                xml.add((UserCSGInformationImpl) locationInformationGPRS.getUserCSGInformation(), USER_CSG_INFORMATION,
                        UserCSGInformationImpl.class);
            }
        }

    };

}
