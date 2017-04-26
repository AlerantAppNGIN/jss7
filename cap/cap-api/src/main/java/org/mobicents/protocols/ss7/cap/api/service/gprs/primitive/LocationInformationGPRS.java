package org.mobicents.protocols.ss7.cap.api.service.gprs.primitive;

import java.io.Serializable;

import org.mobicents.protocols.ss7.map.api.primitives.CellGlobalIdOrServiceAreaIdOrLAI;
import org.mobicents.protocols.ss7.map.api.primitives.ISDNAddressString;
import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.GeographicalInformation;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.RAIdentity;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation.UserCSGInformation;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.LSAIdentity;

/**
 * LocationInformationGPRS ::= SEQUENCE { cellGlobalIdOrServiceAreaIdOrLAI [0]
 * OCTET STRING (SIZE(5..7)) OPTIONAL, routeingAreaIdentity [1] RAIdentity
 * OPTIONAL, geographicalInformation [2] GeographicalInformation OPTIONAL,
 * sgsn-Number [3] ISDN-AddressString OPTIONAL, selectedLSAIdentity [4]
 * LSAIdentity OPTIONAL, extensionContainer [5] ExtensionContainer OPTIONAL,
 * ..., sai-Present [6] NULL OPTIONAL, userCSGInformation [7] UserCSGInformation
 * OPTIONAL } -- cellGlobalIdOrServiceAreaIdOrLAI shall contain the value part
 * of the -- CellGlobalIdOrServiceAreaIdFixedLength type or the LAIFixedLength
 * type (i.e. excluding tags -- and lengths) as defined in 3GPP TS 29.002 [13].
 * -- sai-Present indicates that the cellGlobalIdOrServiceAreaIdOrLAI parameter
 * contains -- a Service Area Identity. -- UserCSGInformation contains the CSG
 * ID, Access mode, and the CSG Membership Indication in the -- case the Access
 * mode is Hybrid Mode, as defined in 3GPP TS 23.060 [93].
 *
 * From: 3GPP TS 29.078 V11.2.0 (2012-12)
 *
 * @author alerant appngin
 */

public interface LocationInformationGPRS extends Serializable {

    CellGlobalIdOrServiceAreaIdOrLAI getCellGlobalIdOrServiceAreaIdOrLAI();

    RAIdentity getRouteingAreaIdentity();

    GeographicalInformation getGeographicalInformation();

    ISDNAddressString getSGSNNumber();

    LSAIdentity getSelectedLSAIdentity();

    MAPExtensionContainer getExtensionContainer();

    boolean isSaiPresent();

    UserCSGInformation getUserCSGInformation();
}
