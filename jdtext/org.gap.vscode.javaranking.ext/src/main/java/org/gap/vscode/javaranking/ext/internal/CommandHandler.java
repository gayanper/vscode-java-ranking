package org.gap.vscode.javaranking.ext.internal;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ls.core.internal.IDelegateCommandHandler;
import org.eclipse.jdt.ls.core.internal.JavaLanguageServerPlugin;
import org.gap.vscode.javaranking.ext.internal.model.MethodModel;
import org.gap.vscode.javaranking.ext.internal.model.TypeModel;

@SuppressWarnings("restriction")
public class CommandHandler implements IDelegateCommandHandler {
    private RankingProvider provider;
    
    @Override
    public Object executeCommand(String commandId, List<Object> arguments, IProgressMonitor monitor) throws Exception {
        switch (commandId) {
        case "java.ranking.register": {
            try {
                MethodModel.getModel().load(List.of(Ranker.class.getResource("/methods.model").toURI().toURL()));
                TypeModel.getModel().load(List.of(Ranker.class.getResource("/types.model").toURI().toURL()));
                provider = new RankingProvider();
                JavaLanguageServerPlugin.getCompletionContributionService().registerRankingProvider(provider);
            } catch (MalformedURLException | URISyntaxException e) {
                ExtensionActivator.logError("Error initializing methods model", e);
            }
            break;
        }
        case "java.ranking.unregister": {
            if (provider != null) {
                JavaLanguageServerPlugin.getCompletionContributionService().unregisterRankingProvider(provider);
                provider = null;
            }
        }
        }
        return null;
    }
    
}
