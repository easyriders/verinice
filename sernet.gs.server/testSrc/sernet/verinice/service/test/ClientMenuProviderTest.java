/*******************************************************************************
 * Copyright (c) 2018 Alexander Ben Nasrallah <an@sernet.de>.
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public 
 * License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Alexander Ben Nasrallah <an@sernet.de>
 ******************************************************************************/
package sernet.verinice.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sernet.verinice.web.poseidon.ClientMenuProvider;

public class ClientMenuProviderTest {

    private ClientMenuProvider menuProvider;
    
    @Before
    public void setUp() {
        menuProvider = new ClientMenuProvider();
    }

    @Test
    public void getNameForZip() {
        Assert.assertEquals("verinice (Linux, 64 bit)", menuProvider.nameForClient("verinice-linux-x86_64.zip"));
        Assert.assertEquals("verinice (Linux, 32 bit)", menuProvider.nameForClient("verinice-linux-x86.zip"));
        Assert.assertEquals("verinice (macOS)", menuProvider.nameForClient("verinice-macosx.cocoa.x86_64.zip"));
        Assert.assertEquals("verinice (Windows, 64 bit)", menuProvider.nameForClient("verinice-windows-x86_64.zip"));
        Assert.assertEquals("verinice (Windows, 32 bit)", menuProvider.nameForClient("verinice-windows-x86.zip"));
    }

    @Test
    public void getIconForZip() {
        Assert.assertEquals("fa fa-fw fa-linux", menuProvider.iconForClient("verinice-linux-x86_64.zip"));
        Assert.assertEquals("fa fa-fw fa-linux", menuProvider.iconForClient("verinice-linux-x86.zip"));
        Assert.assertEquals("fa fa-fw fa-apple", menuProvider.iconForClient("verinice-macosx.cocoa.x86_64.zip"));
        Assert.assertEquals("fa fa-fw fa-windows", menuProvider.iconForClient("verinice-windows-x86_64.zip"));
        Assert.assertEquals("fa fa-fw fa-windows", menuProvider.iconForClient("verinice-windows-x86.zip"));
    }
}
