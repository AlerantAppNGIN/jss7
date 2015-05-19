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
import org.mobicents.protocols.ss7.cap.api.CAPApplicationContextVersion;
import org.mobicents.protocols.ss7.cap.api.CAPException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentException;
import org.mobicents.protocols.ss7.cap.api.CAPParsingComponentExceptionReason;
import org.mobicents.protocols.ss7.cap.api.isup.CalledPartyNumberCap;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.BearerCapability;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.InitialDPArgExtension;
import org.mobicents.protocols.ss7.cap.api.service.circuitSwitchedCall.primitive.LowLayerCompatibility;
import org.mobicents.protocols.ss7.cap.isup.CalledPartyNumberCapImpl;
import org.mobicents.protocols.ss7.cap.primitives.CAPAsnPrimitive;
import org.mobicents.protocols.ss7.inap.api.INAPException;
import org.mobicents.protocols.ss7.inap.api.INAPParsingComponentException;
import org.mobicents.protocols.ss7.inap.api.isup.HighLayerCompatibilityInap;
import org.mobicents.protocols.ss7.inap.isup.HighLayerCompatibilityInapImpl;
import org.mobicents.protocols.ss7.map.api.MAPException;
import org.mobicents.protocols.ss7.map.api.MAPParsingComponentException;
import org.mobicents.protocols.ss7.map.api.primitives.IMEI;
import org.mobicents.protocols.ss7.map.api.primitives.ISDNAddressString;
import org.mobicents.protocols.ss7.map.api.service.callhandling.UUData;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.MSClassmark2;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.ExtBasicServiceCode;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.OfferedCamel4Functionalities;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.SupportedCamelPhases;
import org.mobicents.protocols.ss7.map.primitives.IMEIImpl;
import org.mobicents.protocols.ss7.map.primitives.ISDNAddressStringImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberInformation.MSClassmark2Impl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberManagement.ExtBasicServiceCodeImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberManagement.OfferedCamel4FunctionalitiesImpl;
import org.mobicents.protocols.ss7.map.service.mobility.subscriberManagement.SupportedCamelPhasesImpl;

/**
 *
 * @author sergey vetyutnev
 * @author alerant appngin
 *
 */
@SuppressWarnings("serial")
public class InitialDPArgExtensionImpl implements InitialDPArgExtension, CAPAsnPrimitive {

    public static final int _ID_gmscAddress = 0;
    public static final int _ID_forwardingDestinationNumber = 1;
    public static final int _ID_ms_Classmark2 = 2;
    public static final int _ID_iMEI = 3;
    public static final int _ID_supportedCamelPhases = 4;
    public static final int _ID_offeredCamel4Functionalities = 5;
    public static final int _ID_bearerCapability2 = 6;
    public static final int _ID_ext_basicServiceCode2 = 7;
    public static final int _ID_highLayerCompatibility2 = 8;
    public static final int _ID_lowLayerCompatibility = 9;
    public static final int _ID_lowLayerCompatibility2 = 10;
    public static final int _ID_enhancedDialledServicesAllowed = 11;
    public static final int _ID_uu_Data = 12;
    public static final int _ID_collectInformationAllowed = 13;
    public static final int _ID_releaseCallArgExtensionAllowed = 14;

    private static final String CAP_VERSION = "capVersion";
    private static final String GMSC_ADDRESS = "gmscAddress";
    private static final String FORWARDING_DESTINATION_NUMBER = "forwardingDestinationNumber";
    private static final String MS_CLASSMARK2 = "msClassmark2";
    private static final String IMEI = "iMEI";
    private static final String SUPPORTED_CAMEL_PHASES = "supportedCamelPhases";
    private static final String OFFERED_CAMEL4_FUNCTIONALITIES = "offeredCamel4Functionalities";
    private static final String BEARER_CAPABILITY2 = "bearerCapability2";
    private static final String EXT_BASIC_SERVICE_CODE2 = "extBasicServiceCode2";
    private static final String HIGH_LAYER_COMPATIBILITY2 = "highLayerCompatibility2";
    private static final String LOW_LAYER_COMPATIBILITY = "lowLayerCompatibility";
    private static final String LOW_LAYER_COMPATIBILITY2 = "lowLayerCompatibility2";
    private static final String ENHANCED_DIALLED_SERVICES_ALLOWED = "enhancedDialledServicesAllowed";
    private static final String UU_DATA = "uuData";
    private static final String COLLECT_INFORMATION_ALLOWED = "collectInformationAllowed";
    private static final String RELEASE_CALL_ARG_EXTENSION_ALLOWED = "releaseCallArgExtensionAllowed";

    public static final String _PrimitiveName = "InitialDPArgExtension";

    private ISDNAddressString gmscAddress;
    private CalledPartyNumberCap forwardingDestinationNumber;
    private MSClassmark2 msClassmark2;
    private IMEI imei;
    private SupportedCamelPhases supportedCamelPhases;
    private OfferedCamel4Functionalities offeredCamel4Functionalities;
    private BearerCapability bearerCapability2;
    private ExtBasicServiceCode extBasicServiceCode2;
    private HighLayerCompatibilityInap highLayerCompatibility2;
    private LowLayerCompatibility lowLayerCompatibility;
    private LowLayerCompatibility lowLayerCompatibility2;
    private boolean enhancedDialledServicesAllowed;
    private UUData uuData;
    private boolean collectInformationAllowed;
    private boolean releaseCallArgExtensionAllowed;

    protected CAPApplicationContextVersion capVersion;

    /**
     * This constructor is for deserializing purposes
     */
    public InitialDPArgExtensionImpl() {
    }

    public InitialDPArgExtensionImpl(CAPApplicationContextVersion capVersion) {
        this.capVersion = capVersion;
    }

    public InitialDPArgExtensionImpl(ISDNAddressString gmscAddress, CalledPartyNumberCap forwardingDestinationNumber,
            MSClassmark2 msClassmark2, IMEI imei, SupportedCamelPhases supportedCamelPhases,
            OfferedCamel4Functionalities offeredCamel4Functionalities, BearerCapability bearerCapability2,
            ExtBasicServiceCode extBasicServiceCode2, HighLayerCompatibilityInap highLayerCompatibility2,
            LowLayerCompatibility lowLayerCompatibility, LowLayerCompatibility lowLayerCompatibility2,
            boolean enhancedDialledServicesAllowed, UUData uuData, boolean collectInformationAllowed,
            boolean releaseCallArgExtensionAllowed, CAPApplicationContextVersion capVersion) {
        this.gmscAddress = gmscAddress;
        this.forwardingDestinationNumber = forwardingDestinationNumber;
        this.msClassmark2 = msClassmark2;
        this.imei = imei;
        this.supportedCamelPhases = supportedCamelPhases;
        this.offeredCamel4Functionalities = offeredCamel4Functionalities;
        this.bearerCapability2 = bearerCapability2;
        this.extBasicServiceCode2 = extBasicServiceCode2;
        this.highLayerCompatibility2 = highLayerCompatibility2;
        this.lowLayerCompatibility = lowLayerCompatibility;
        this.lowLayerCompatibility2 = lowLayerCompatibility2;
        this.enhancedDialledServicesAllowed = enhancedDialledServicesAllowed;
        this.uuData = uuData;
        this.collectInformationAllowed = collectInformationAllowed;
        this.releaseCallArgExtensionAllowed = releaseCallArgExtensionAllowed;
        this.capVersion = capVersion;
    }

    @Override
    public ISDNAddressString getGmscAddress() {
        return gmscAddress;
    }

    @Override
    public CalledPartyNumberCap getForwardingDestinationNumber() {
        return forwardingDestinationNumber;
    }

    @Override
    public MSClassmark2 getMSClassmark2() {
        return msClassmark2;
    }

    @Override
    public IMEI getIMEI() {
        return imei;
    }

    @Override
    public SupportedCamelPhases getSupportedCamelPhases() {
        return supportedCamelPhases;
    }

    @Override
    public OfferedCamel4Functionalities getOfferedCamel4Functionalities() {
        return offeredCamel4Functionalities;
    }

    @Override
    public BearerCapability getBearerCapability2() {
        return bearerCapability2;
    }

    @Override
    public ExtBasicServiceCode getExtBasicServiceCode2() {
        return extBasicServiceCode2;
    }

    @Override
    public HighLayerCompatibilityInap getHighLayerCompatibility2() {
        return highLayerCompatibility2;
    }

    @Override
    public LowLayerCompatibility getLowLayerCompatibility() {
        return lowLayerCompatibility;
    }

    @Override
    public LowLayerCompatibility getLowLayerCompatibility2() {
        return lowLayerCompatibility2;
    }

    @Override
    public boolean getEnhancedDialledServicesAllowed() {
        return enhancedDialledServicesAllowed;
    }

    @Override
    public UUData getUUData() {
        return uuData;
    }

    public boolean getCollectInformationAllowed() {
        return collectInformationAllowed;
    }

    public boolean getReleaseCallArgExtensionAllowed() {
        return releaseCallArgExtensionAllowed;
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
            throw new CAPParsingComponentException("IOException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException("AsnException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (MAPParsingComponentException e) {
            throw new CAPParsingComponentException("MAPParsingComponentException when decoding " + _PrimitiveName
                    + ": " + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (INAPParsingComponentException e) {
            throw new CAPParsingComponentException("INAPParsingComponentException when decoding " + _PrimitiveName
                    + ": " + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    @Override
    public void decodeData(AsnInputStream ansIS, int length) throws CAPParsingComponentException {

        try {
            this._decode(ansIS, length);
        } catch (IOException e) {
            throw new CAPParsingComponentException("IOException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (AsnException e) {
            throw new CAPParsingComponentException("AsnException when decoding " + _PrimitiveName + ": "
                    + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (MAPParsingComponentException e) {
            throw new CAPParsingComponentException("MAPParsingComponentException when decoding " + _PrimitiveName
                    + ": " + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        } catch (INAPParsingComponentException e) {
            throw new CAPParsingComponentException("INAPParsingComponentException when decoding " + _PrimitiveName
                    + ": " + e.getMessage(), e, CAPParsingComponentExceptionReason.MistypedParameter);
        }
    }

    private void _decode(AsnInputStream ansIS, int length) throws CAPParsingComponentException,
            MAPParsingComponentException, IOException, AsnException, INAPParsingComponentException {

        this.gmscAddress = null;
        this.forwardingDestinationNumber = null;
        this.msClassmark2 = null;
        this.imei = null;
        this.supportedCamelPhases = null;
        this.offeredCamel4Functionalities = null;
        this.bearerCapability2 = null;
        this.extBasicServiceCode2 = null;
        this.highLayerCompatibility2 = null;
        this.lowLayerCompatibility = null;
        this.lowLayerCompatibility2 = null;
        this.enhancedDialledServicesAllowed = false;
        this.uuData = null;
        this.collectInformationAllowed = false;
        this.releaseCallArgExtensionAllowed = false;

        AsnInputStream ais = ansIS.readSequenceStreamData(length);
        while (true) {
            if (ais.available() == 0)
                break;

            int tag = ais.readTag();

            if (ais.getTagClass() == Tag.CLASS_CONTEXT_SPECIFIC) {
                switch (tag) {
                    case _ID_gmscAddress:
                        if (capVersion.getVersion() >= 3) {
                            this.gmscAddress = new ISDNAddressStringImpl();
                            ((ISDNAddressStringImpl) this.gmscAddress).decodeAll(ais);
                        } else {
                            // in CAP V2 naCarrierInformation parameter - we do
                            // not implement it
                            ais.advanceElement();
                        }
                        break;
                    case _ID_forwardingDestinationNumber:
                        if (capVersion.getVersion() >= 3) {
                            this.forwardingDestinationNumber = new CalledPartyNumberCapImpl();
                            ((CalledPartyNumberCapImpl) this.forwardingDestinationNumber).decodeAll(ais);
                        } else {
                            // in CAP V2 gmscAddress parameter
                            this.gmscAddress = new ISDNAddressStringImpl();
                            ((ISDNAddressStringImpl) this.gmscAddress).decodeAll(ais);
                        }
                        break;
                    case _ID_ms_Classmark2:
                        this.msClassmark2 = new MSClassmark2Impl();
                        ((MSClassmark2Impl) this.msClassmark2).decodeAll(ais);
                        break;
                    case _ID_iMEI:
                        this.imei = new IMEIImpl();
                        ((IMEIImpl) this.imei).decodeAll(ais);
                        break;
                    case _ID_supportedCamelPhases:
                        this.supportedCamelPhases = new SupportedCamelPhasesImpl();
                        ((SupportedCamelPhasesImpl) this.supportedCamelPhases).decodeAll(ais);
                        break;
                    case _ID_offeredCamel4Functionalities:
                        this.offeredCamel4Functionalities = new OfferedCamel4FunctionalitiesImpl();
                        ((OfferedCamel4FunctionalitiesImpl) this.offeredCamel4Functionalities).decodeAll(ais);
                        break;
                    case _ID_bearerCapability2:
                        this.bearerCapability2 = new BearerCapabilityImpl();
                        ((BearerCapabilityImpl) this.bearerCapability2).decodeAll(ais);
                        break;
                    case _ID_ext_basicServiceCode2:
                        this.extBasicServiceCode2 = new ExtBasicServiceCodeImpl();
                        ((ExtBasicServiceCodeImpl) this.extBasicServiceCode2).decodeAll(ais);
                        break;
                    case _ID_highLayerCompatibility2:
                        this.highLayerCompatibility2 = new HighLayerCompatibilityInapImpl();
                        ((HighLayerCompatibilityInapImpl) this.highLayerCompatibility2).decodeAll(ais);
                        break;
                    case _ID_lowLayerCompatibility:
                        ais.advanceElement(); // TODO: implement it
                        break;
                    case _ID_lowLayerCompatibility2:
                        ais.advanceElement(); // TODO: implement it
                        break;
                    case _ID_enhancedDialledServicesAllowed:
                        this.enhancedDialledServicesAllowed = true;
                        ais.readNull();
                        break;
                    case _ID_uu_Data:
                        ais.advanceElement(); // TODO: implement it
                        break;
                    case _ID_collectInformationAllowed:
                        this.collectInformationAllowed = true;
                        ais.readNull();
                        break;
                    case _ID_releaseCallArgExtensionAllowed:
                        this.releaseCallArgExtensionAllowed = true;
                        ais.readNull();
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
    public void encodeData(AsnOutputStream aos) throws CAPException {

        try {
            if (capVersion.getVersion() >= 3) {
                if (this.gmscAddress != null)
                    ((ISDNAddressStringImpl) this.gmscAddress).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC,
                            _ID_gmscAddress);
                if (this.forwardingDestinationNumber != null)
                    ((CalledPartyNumberCapImpl) this.forwardingDestinationNumber).encodeAll(aos,
                            Tag.CLASS_CONTEXT_SPECIFIC, _ID_forwardingDestinationNumber);
            } else {
                if (this.gmscAddress != null)
                    ((ISDNAddressStringImpl) this.gmscAddress).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC,
                            _ID_forwardingDestinationNumber);
            }

            if (msClassmark2 != null) {
                ((MSClassmark2Impl) msClassmark2).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC, _ID_ms_Classmark2);
            }
            if (imei != null) {
                ((IMEIImpl) imei).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC, _ID_iMEI);
            }
            if (supportedCamelPhases != null) {
                ((SupportedCamelPhasesImpl) supportedCamelPhases).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_supportedCamelPhases);
            }
            if (offeredCamel4Functionalities != null) {
                ((OfferedCamel4FunctionalitiesImpl) offeredCamel4Functionalities).encodeAll(aos,
                        Tag.CLASS_CONTEXT_SPECIFIC, _ID_offeredCamel4Functionalities);
            }
            if (bearerCapability2 != null) {
                ((BearerCapabilityImpl) bearerCapability2).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_bearerCapability2);
            }
            if (extBasicServiceCode2 != null) {
                ((ExtBasicServiceCodeImpl) extBasicServiceCode2).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_ext_basicServiceCode2);
            }
            if (highLayerCompatibility2 != null) {
                ((HighLayerCompatibilityInapImpl) highLayerCompatibility2).encodeAll(aos, Tag.CLASS_CONTEXT_SPECIFIC,
                        _ID_highLayerCompatibility2);
            }
            if (lowLayerCompatibility != null) {
                // TODO: implement it
            }
            if (lowLayerCompatibility2 != null) {
                // TODO: implement it
            }
            if (enhancedDialledServicesAllowed) {
                aos.writeNull(Tag.CLASS_CONTEXT_SPECIFIC, _ID_enhancedDialledServicesAllowed);
            }
            if (uuData != null) {
                // TODO: implement it
            }
            if (collectInformationAllowed) {
                aos.writeNull(Tag.CLASS_CONTEXT_SPECIFIC, _ID_collectInformationAllowed);
            }
            if (releaseCallArgExtensionAllowed) {
                aos.writeNull(Tag.CLASS_CONTEXT_SPECIFIC, _ID_releaseCallArgExtensionAllowed);
            }
        } catch (MAPException e) {
            throw new CAPException("MAPException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (AsnException e) {
            throw new CAPException("AsnException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (IOException e) {
            throw new CAPException("IOException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        } catch (INAPException e) {
            throw new CAPException("INAPException when encoding " + _PrimitiveName + ": " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(_PrimitiveName);
        sb.append(" [");

        if (this.gmscAddress != null) {
            sb.append(", gmscAddress=");
            sb.append(gmscAddress);
        }
        if (this.forwardingDestinationNumber != null) {
            sb.append(", forwardingDestinationNumber=");
            sb.append(forwardingDestinationNumber);
        }
        if (this.msClassmark2 != null) {
            sb.append(", msClassmark2=");
            sb.append(msClassmark2.toString());
        }
        if (this.imei != null) {
            sb.append(", imei=");
            sb.append(imei.toString());
        }
        if (this.supportedCamelPhases != null) {
            sb.append(", supportedCamelPhases=");
            sb.append(supportedCamelPhases.toString());
        }
        if (this.offeredCamel4Functionalities != null) {
            sb.append(", offeredCamel4Functionalities=");
            sb.append(offeredCamel4Functionalities.toString());
        }
        if (this.bearerCapability2 != null) {
            sb.append(", bearerCapability2=");
            sb.append(bearerCapability2.toString());
        }
        if (this.extBasicServiceCode2 != null) {
            sb.append(", extBasicServiceCode2=");
            sb.append(extBasicServiceCode2.toString());
        }
        if (this.highLayerCompatibility2 != null) {
            sb.append(", highLayerCompatibility2=");
            sb.append(highLayerCompatibility2.toString());
        }
        if (this.lowLayerCompatibility != null) {
            sb.append(", lowLayerCompatibility=");
            sb.append(lowLayerCompatibility.toString());
        }
        if (this.lowLayerCompatibility2 != null) {
            sb.append(", lowLayerCompatibility2=");
            sb.append(lowLayerCompatibility2.toString());
        }
        if (this.enhancedDialledServicesAllowed) {
            sb.append(", enhancedDialledServicesAllowed");
        }
        if (this.uuData != null) {
            sb.append(", uuData=");
            sb.append(uuData.toString());
        }
        if (this.collectInformationAllowed) {
            sb.append(", collectInformationAllowed");
        }
        if (this.releaseCallArgExtensionAllowed) {
            sb.append(", releaseCallArgExtensionAllowed");
        }
        sb.append("]");

        return sb.toString();
    }

    /**
     * XML Serialization/Deserialization
     */
    protected static final XMLFormat<InitialDPArgExtensionImpl> INITIAL_DP_ARG_EXTENSION_XML = new XMLFormat<InitialDPArgExtensionImpl>(
            InitialDPArgExtensionImpl.class) {

        @Override
        public void read(javolution.xml.XMLFormat.InputElement xml, InitialDPArgExtensionImpl initialDPArgExtension)
                throws XMLStreamException {
            initialDPArgExtension.capVersion = CAPApplicationContextVersion.valueOf(xml.getAttribute(CAP_VERSION, ""));

            initialDPArgExtension.gmscAddress = xml.get(GMSC_ADDRESS, ISDNAddressStringImpl.class);
            initialDPArgExtension.forwardingDestinationNumber = xml.get(FORWARDING_DESTINATION_NUMBER,
                    CalledPartyNumberCapImpl.class);

            initialDPArgExtension.msClassmark2 = xml.get(MS_CLASSMARK2, MSClassmark2Impl.class);
            initialDPArgExtension.imei = xml.get(IMEI, IMEIImpl.class);
            initialDPArgExtension.supportedCamelPhases = xml
                    .get(SUPPORTED_CAMEL_PHASES, SupportedCamelPhasesImpl.class);
            initialDPArgExtension.offeredCamel4Functionalities = xml.get(OFFERED_CAMEL4_FUNCTIONALITIES,
                    OfferedCamel4FunctionalitiesImpl.class);
            initialDPArgExtension.bearerCapability2 = xml.get(BEARER_CAPABILITY2, BearerCapabilityImpl.class);
            initialDPArgExtension.extBasicServiceCode2 = xml
                    .get(EXT_BASIC_SERVICE_CODE2, ExtBasicServiceCodeImpl.class);
            initialDPArgExtension.highLayerCompatibility2 = xml.get(HIGH_LAYER_COMPATIBILITY2,
                    HighLayerCompatibilityInapImpl.class);
            // TODO lowlayercomp
            // TODO lowlayercomp2

            Boolean nullAsBoolean = xml.get(ENHANCED_DIALLED_SERVICES_ALLOWED, Boolean.class);
            if (nullAsBoolean != null) {
                initialDPArgExtension.enhancedDialledServicesAllowed = nullAsBoolean;
            }

            // TODO uuData

            nullAsBoolean = xml.get(COLLECT_INFORMATION_ALLOWED, Boolean.class);
            if (nullAsBoolean != null) {
                initialDPArgExtension.collectInformationAllowed = nullAsBoolean;
            }
            nullAsBoolean = xml.get(RELEASE_CALL_ARG_EXTENSION_ALLOWED, Boolean.class);
            if (nullAsBoolean != null) {
                initialDPArgExtension.releaseCallArgExtensionAllowed = nullAsBoolean;
            }
        }

        @Override
        public void write(InitialDPArgExtensionImpl initialDPArgExtension, javolution.xml.XMLFormat.OutputElement xml)
                throws XMLStreamException {
            xml.setAttribute(CAP_VERSION, initialDPArgExtension.capVersion.name());

            if (initialDPArgExtension.getGmscAddress() != null)
                xml.add((ISDNAddressStringImpl) initialDPArgExtension.getGmscAddress(), GMSC_ADDRESS,
                        ISDNAddressStringImpl.class);
            if (initialDPArgExtension.getForwardingDestinationNumber() != null)
                xml.add((CalledPartyNumberCapImpl) initialDPArgExtension.getForwardingDestinationNumber(),
                        FORWARDING_DESTINATION_NUMBER, CalledPartyNumberCapImpl.class);

            if (initialDPArgExtension.msClassmark2 != null)
                xml.add((MSClassmark2Impl) initialDPArgExtension.msClassmark2, MS_CLASSMARK2, MSClassmark2Impl.class);
            if (initialDPArgExtension.imei != null)
                xml.add((IMEIImpl) initialDPArgExtension.imei, IMEI, IMEIImpl.class);
            if (initialDPArgExtension.supportedCamelPhases != null)
                xml.add((SupportedCamelPhasesImpl) initialDPArgExtension.supportedCamelPhases, SUPPORTED_CAMEL_PHASES,
                        SupportedCamelPhasesImpl.class);
            if (initialDPArgExtension.offeredCamel4Functionalities != null)
                xml.add((OfferedCamel4FunctionalitiesImpl) initialDPArgExtension.offeredCamel4Functionalities,
                        OFFERED_CAMEL4_FUNCTIONALITIES, OfferedCamel4FunctionalitiesImpl.class);
            if (initialDPArgExtension.bearerCapability2 != null)
                xml.add((BearerCapabilityImpl) initialDPArgExtension.bearerCapability2, BEARER_CAPABILITY2,
                        BearerCapabilityImpl.class);
            if (initialDPArgExtension.extBasicServiceCode2 != null)
                xml.add((ExtBasicServiceCodeImpl) initialDPArgExtension.extBasicServiceCode2, EXT_BASIC_SERVICE_CODE2,
                        ExtBasicServiceCodeImpl.class);
            if (initialDPArgExtension.highLayerCompatibility2 != null)
                xml.add((HighLayerCompatibilityInapImpl) initialDPArgExtension.highLayerCompatibility2,
                        HIGH_LAYER_COMPATIBILITY2, HighLayerCompatibilityInapImpl.class);
            // TODO lowlayercomp
            // TODO lowlayercomp2

            if (initialDPArgExtension.enhancedDialledServicesAllowed)
                xml.add(initialDPArgExtension.enhancedDialledServicesAllowed, ENHANCED_DIALLED_SERVICES_ALLOWED,
                        Boolean.class);

            // TODO uuData

            if (initialDPArgExtension.collectInformationAllowed)
                xml.add(initialDPArgExtension.collectInformationAllowed, COLLECT_INFORMATION_ALLOWED, Boolean.class);
            if (initialDPArgExtension.releaseCallArgExtensionAllowed)
                xml.add(initialDPArgExtension.releaseCallArgExtensionAllowed, RELEASE_CALL_ARG_EXTENSION_ALLOWED,
                        Boolean.class);
        }
    };
}
