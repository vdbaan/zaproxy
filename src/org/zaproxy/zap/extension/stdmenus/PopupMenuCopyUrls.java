/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Copyright 2013 The ZAP Development Team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension.stdmenus;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.parosproxy.paros.Constant;
import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.view.View;
import org.zaproxy.zap.view.popup.PopupMenuItemHistoryReferenceContainer;

public class PopupMenuCopyUrls extends PopupMenuItemHistoryReferenceContainer implements ClipboardOwner {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(PopupMenuCopyUrls.class);

    /**
     * @param label
     */
    public PopupMenuCopyUrls(String label) {
        super(label, true);
    }
    
	@Override
	public void performAction(HistoryReference href) {
	}
	
	@Override
    protected void performHistoryReferenceActions (List<HistoryReference> hrefs) {
	    boolean error = false;
		StringBuilder sb = new StringBuilder();
    	for (HistoryReference href : hrefs) {
    	    try {
    	        sb.append(href.getURI().toString());
    	        sb.append("\n");
    	    } catch (HttpMalformedHeaderException | SQLException e) {
    	        if (!error) {
    	            logger.error("Failed to copy at least one URI to clipboard: " + e.getMessage(), e);
    	            error = true;
    	        }
    	    }
    	}
    	
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents( new StringSelection(sb.toString()), this );

        if (error) {
            View.getSingleton().showWarningDialog(Constant.messages.getString("stdexts.copyurls.popup.error.copying"));
        }
    }

    @Override
    public boolean isSafe() {
    	return true;
    }

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// Ignore
	}
}
