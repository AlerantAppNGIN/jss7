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

package org.mobicents.protocols.ss7.map.api.service.mobility.subscriberInformation;

import java.io.Serializable;

import org.mobicents.protocols.ss7.map.api.primitives.MAPExtensionContainer;
import org.mobicents.protocols.ss7.map.api.service.mobility.subscriberManagement.ExtSSStatus;
import org.mobicents.protocols.ss7.map.api.service.supplementary.OverrideCategory;

/**
 *
 ModificationRequestFor-CLIP-Info ::= SEQUENCE { ss-Status [0] Ext-SS-Status OPTIONAL, overrideCategory [1] OverrideCategory
 * OPTIONAL, modifyNotificationToCSE [2] ModificationInstruction OPTIONAL, extensionContainer [3] ExtensionContainer OPTIONAL,
 * ...}
 *
 *
 * @author sergey vetyutnev
 *
 */
public interface ModificationRequestForCLIPInfo extends Serializable {

    ExtSSStatus getSsStatus();

    OverrideCategory getOverrideCategory();

    ModificationInstruction getModifyNotificationToCSE();

    MAPExtensionContainer getExtensionContainer();

}
