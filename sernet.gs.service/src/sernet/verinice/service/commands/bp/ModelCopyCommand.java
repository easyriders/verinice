/*******************************************************************************
 * Copyright (c) 2018 <Vorname> <Nachname>.
 *
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 ******************************************************************************/
package sernet.verinice.service.commands.bp;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import sernet.gs.service.RuntimeCommandException;
import sernet.verinice.interfaces.ChangeLoggingCommand;
import sernet.verinice.interfaces.CommandException;
import sernet.verinice.interfaces.IBaseDao;
import sernet.verinice.model.common.ChangeLogEntry;
import sernet.verinice.model.common.CnATreeElement;
import sernet.verinice.service.commands.CopyCommand;

/**
 * Abstract base class for modeling modules and safeguard groups. The modules
 * and safeguard groups are copied and pasted as childs of the elements.
 */
public abstract class ModelCopyCommand extends ChangeLoggingCommand {

    private static final long serialVersionUID = 5344935376238004516L;

    private static final Logger LOG = Logger.getLogger(ModelCopyCommand.class);

    private String stationId;
    private transient ModelingMetaDao metaDao;

    protected Set<CnATreeElement> targetElements;
    protected Set<String> newModuleUuids = Collections.emptySet();

    public ModelCopyCommand() {
        super();
        this.stationId = ChangeLogEntry.STATION_ID;
    }

    @Override
    public String getStationId() {
        return stationId;
    }

    @Override
    public int getChangeType() {
        return ChangeLogEntry.TYPE_INSERT;
    }

    @Override
    public void execute() {
        try {
            handleElement();
        } catch (CommandException e) {
            LOG.error("Error while modeling.", e);
            throw new RuntimeCommandException("Error while modeling.", e);
        }
    }

    protected abstract Set<CnATreeElement> getElementsFromCompendium();

    protected abstract boolean isEqual(CnATreeElement e1, CnATreeElement e2);

    private void handleElement() throws CommandException {
        for (CnATreeElement target : targetElements) {
            List<String> missingUuids = createListOfMissingUuids(target);
            if (!missingUuids.isEmpty()) {
                CopyCommand copyCommand = new CopyCommand(target.getUuid(), missingUuids);
                copyCommand = getCommandService().executeCommand(copyCommand);
                newModuleUuids = new HashSet<>(copyCommand.getNewElements());
            }
        }
    }

    private List<String> createListOfMissingUuids(CnATreeElement targetWithChildren) {
        List<String> uuids = new LinkedList<>();
        Set<CnATreeElement> targetChildren = targetWithChildren.getChildren();
        for (CnATreeElement module : getElementsFromCompendium()) {
            if (!elementExists(targetChildren, module)) {
                uuids.add(module.getUuid());
            }
        }
        return uuids;
    }

    protected boolean elementExists(Set<CnATreeElement> targetChildren, CnATreeElement element) {
        for (CnATreeElement child : targetChildren) {
            if (isEqual(element, child)) {
                return true;
            }
        }
        return false;
    }

    public ModelingMetaDao getMetaDao() {
        if (metaDao == null) {
            metaDao = new ModelingMetaDao(getDao());
        }
        return metaDao;
    }

    private IBaseDao<CnATreeElement, Serializable> getDao() {
        return getDaoFactory().getDAO(CnATreeElement.class);
    }

    public Set<CnATreeElement> getTargetElements() {
        return targetElements;
    }

    public Set<String> getNewModuleUuids() {
        return newModuleUuids;
    }

    protected void logElements(Collection<?> collection) {
        for (Object element : collection) {
            LOG.debug(element);
        }

    }

}
