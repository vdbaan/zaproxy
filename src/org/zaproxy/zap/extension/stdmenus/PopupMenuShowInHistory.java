/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
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

import org.parosproxy.paros.control.Control;
import org.parosproxy.paros.extension.history.ExtensionHistory;
import org.parosproxy.paros.model.HistoryReference;
import org.zaproxy.zap.view.messagecontainer.http.HttpMessageContainer;
import org.zaproxy.zap.view.popup.PopupMenuItemHistoryReferenceContainer;


public class PopupMenuShowInHistory extends PopupMenuItemHistoryReferenceContainer {

	private static final long serialVersionUID = 1L;
    private ExtensionHistory extension = null;

    /**
     * @param label
     */
    public PopupMenuShowInHistory(String label) {
        super(label);
    }
    
    private ExtensionHistory getExtensionHistory() {
    	if (extension == null) {
    		extension = (ExtensionHistory) Control.getSingleton().getExtensionLoader().getExtension(ExtensionHistory.NAME);
    	}
    	return extension;
    }
	
	@Override
	public void performAction(HistoryReference href) {
		getExtensionHistory().showInHistory(href);
	}

	@Override
	public boolean isEnableForInvoker(Invoker invoker, HttpMessageContainer httpMessageContainer) {
		if (getExtensionHistory() == null) {
			return false;
		}
		switch (invoker) {
		case ACTIVE_SCANNER_PANEL:
		case FORCED_BROWSE_PANEL:
		case FUZZER_PANEL:
		case HISTORY_PANEL:
			return false;
		case ALERTS_PANEL:
		case SITES_PANEL:
		case SEARCH_PANEL:
		default:
			return true;
		}
	}
	
    @Override
    public boolean isSafe() {
    	return true;
    }
}
