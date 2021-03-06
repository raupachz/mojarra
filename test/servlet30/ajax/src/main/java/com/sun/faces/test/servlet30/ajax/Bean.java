/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.test.servlet30.ajax;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "bean")
@SessionScoped
public class Bean {

    private final Collection<SelectItem> items;
    private String radioValue = "blue";
    private String status = null;

    public Bean() {
        Set<SelectItem> initialItems = new LinkedHashSet<SelectItem>();
        initialItems.add(new SelectItem("red"));
        initialItems.add(new SelectItem("blue"));
        initialItems.add(new SelectItem("white"));
        items = Collections.unmodifiableSet(initialItems);
    }

    public Collection<SelectItem> getItems() {
        return items;
    }

    public void setRadioValue(String radioValue) {
        this.radioValue = radioValue;
    }

    public String getRadioValue() {
        return radioValue;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void processLink(ActionEvent ae) {
        status = "LINK ACTION";
    }

    public void processRadio(ValueChangeEvent vce) {
        status = "RADIO:"+(String)vce.getNewValue();
    }

    public void processIt(AjaxBehaviorEvent event) {
        setRadioValue("red");
    }
    
    public String getThrowExceptionOnAjax() {
        FacesContext context = FacesContext.getCurrentInstance();
        PartialViewContext partialContext = context.getPartialViewContext();
        if (null != partialContext) {
            if (partialContext.isAjaxRequest()) {
                throw new RuntimeException("Intentionally throwing exception on ajax request");
            }
        }
        
        String result = "not an ajax request";
        
        return result;
    }
}
