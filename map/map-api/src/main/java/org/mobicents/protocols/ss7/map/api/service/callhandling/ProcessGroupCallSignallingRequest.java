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

package org.mobicents.protocols.ss7.map.api.service.callhandling;

import org.mobicents.protocols.ss7.map.api.primitives.AccessNetworkSignalInfo;
import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.AdditionalInfo;

/**
 *
 MAP V3:
 *
 * processGroupCallSignalling OPERATION ::= { --Timer s ARGUMENT ProcessGroupCallSignallingArg CODE local:41 }
 *
 * ProcessGroupCallSignallingArg ::= SEQUENCE { uplinkRequest [0] NULL OPTIONAL, uplinkReleaseIndication [1] NULL OPTIONAL,
 * releaseGroupCall [2] NULL OPTIONAL, extensionContainer ExtensionContainer OPTIONAL, ..., talkerPriority [3] TalkerPriority
 * OPTIONAL, additionalInfo [4] AdditionalInfo OPTIONAL, emergencyModeResetCommandFlag [5] NULL OPTIONAL, an-APDU [6]
 * AccessNetworkSignalInfo OPTIONAL }
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface ProcessGroupCallSignallingRequest extends CallHandlingMessage {

     boolean getUplinkRequest();

     boolean getUplinkReleaseIndication();

     boolean getReleaseGroupCall();

     MAPExtensionContainer getExtensionContainer();

     TalkerPriority getTalkerPriority();

     AdditionalInfo getAdditionalInfo();

     boolean getEmergencyModeResetCommandFlag();

     AccessNetworkSignalInfo getAnAPDU();

}
