package org.gap.vscode.javaranking.ext.internal;

import org.eclipse.jdt.ls.core.internal.JavaLanguageServerPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

@SuppressWarnings("restriction")
public class ExtensionActivator implements BundleActivator {
    
    public static final String PLUGIN_ID = "jdtext.ext";
    
    private static ExtensionActivator plugin;
    
    public ExtensionActivator() {
    }
    
    @SuppressWarnings("static")
    public void start(BundleContext context) throws Exception {
        plugin = this;
    }
    
    @SuppressWarnings("static")
    public void stop(BundleContext context) throws Exception {
        plugin = null;
    }
    
    public static ExtensionActivator getDefault() {
        return plugin;
    }
    
    public static void logError(String message, Throwable e) {
        JavaLanguageServerPlugin.logException(message, e);
    }
}