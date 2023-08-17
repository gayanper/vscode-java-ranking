package org.gap.vscode.javaranking.ext.internal;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ls.core.contentassist.CompletionRanking;
import org.eclipse.jdt.ls.core.contentassist.ICompletionRankingProvider;
import org.eclipse.lsp4j.CompletionItem;
import org.gap.vscode.javaranking.ext.internal.rankers.ModelBaseMethodRanker;
import org.gap.vscode.javaranking.ext.internal.rankers.TypeBaseMethodRanker;

@SuppressWarnings("restriction")
public class RankingProvider implements ICompletionRankingProvider {
    private Ranker ranker;
    
    public RankingProvider() {
        ranker = new ModelBaseMethodRanker().add(new TypeBaseMethodRanker());
    }
    
    @Override
    public CompletionRanking[] rank(List<CompletionProposal> proposals, CompletionContext context,
            ICompilationUnit unit, IProgressMonitor monitor) {
        CompletionRanking[] rankings = new CompletionRanking[proposals.size()];
        ranker.init(context, unit, monitor);
        if (monitor.isCanceled())
            return rankings;
        
        for (int i = 0; i < proposals.size(); i++) {
            if (monitor.isCanceled()) {
                break;
            }
            rankings[i] = new CompletionRanking();
            ranker.rank(proposals.get(i), rankings[i], context, unit, monitor);
        }
        ranker.cleanup(context, unit, monitor);
        return rankings;
    }
    
    @Override
    public void onDidCompletionItemSelect(CompletionItem item) {
    }
    
}
