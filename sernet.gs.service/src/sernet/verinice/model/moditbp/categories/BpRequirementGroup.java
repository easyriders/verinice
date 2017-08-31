/*******************************************************************************
 * Copyright (c) 2017 Sebastian Hagedorn.
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
 * Contributors:
 *     Sebastian Hagedorn sh[at]sernet.de - initial API and implementation
 ******************************************************************************/
package sernet.verinice.model.moditbp.categories;

import sernet.verinice.model.common.CnATreeElement;
import sernet.verinice.model.iso27k.Group;
import sernet.verinice.model.moditbp.IBpGroup;
import sernet.verinice.model.moditbp.elements.BpRequirement;

/**
 * 
 * @author Sebastian Hagedorn sh[at]sernet.de
 */
public class BpRequirementGroup extends Group<BpRequirement> implements IBpGroup {
    
    private static final long serialVersionUID = 7752776589962581995L;
    
    public static final String TYPE_ID = "bp_requirementgroup";
    
    public static final String[] CHILD_TYPES = new String[] {BpRequirement.TYPE_ID};
    
    protected BpRequirementGroup() {}
    
    public BpRequirementGroup(CnATreeElement parent) {
        super(parent);
    }

    @Override
    public String getTypeId() {
        return TYPE_ID;
    }
    
    @Override
    public boolean canContain(Object object) {
        return object instanceof BpRequirement;
    }
    
    @Override
    public String[] getChildTypes() {
        return CHILD_TYPES;
    }

}
