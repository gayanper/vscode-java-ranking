package org.gap.vscode.javaranking.ext.internal.rankers;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ls.core.contentassist.CompletionRanking;
import org.gap.vscode.javaranking.ext.internal.Ranker;

@SuppressWarnings("restriction")
public class JavaCollectionRanker extends Ranker {
    private static final Set<String> collectionTypes = Set.of("Ljava.util.Set;", "Ljava.util.List;", "Ljava.util.Map;");
    private static final Set<String> collectionShortTypes = Set.of("Set", "List", "Map");
    
    @Override
    protected void performRanking(CompletionProposal proposal, CompletionRanking ranking, CompletionContext context,
            ICompilationUnit cu, IProgressMonitor monitor) {
        rankTypes(proposal, ranking, context, cu, monitor);
    }
    
    private void rankTypes(CompletionProposal proposal, CompletionRanking ranking, CompletionContext context,
            ICompilationUnit cu, IProgressMonitor monitor) {
        if (proposal.getKind() != CompletionProposal.TYPE_REF
                || collectionShortTypes.contains(String.valueOf(context.getToken()))) {
            return;
        }
        
        final String signature = String.valueOf(proposal.getSignature());
        if (collectionTypes.contains(signature)) {
            ranking.setScore(90);
        }
    }
}
