package org.gap.vscode.javaranking.ext.internal.rankers;

import java.util.Optional;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.CompletionContext;
import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.ls.core.contentassist.CompletionRanking;
import org.gap.vscode.javaranking.ext.internal.ProjectHelper;
import org.gap.vscode.javaranking.ext.internal.Ranker;
import org.gap.vscode.javaranking.ext.internal.model.TypeModel;

@SuppressWarnings("restriction")
public class TypeBaseMethodRanker extends Ranker {
    @Override
    protected void performRanking(CompletionProposal proposal, CompletionRanking ranking, CompletionContext context,
            ICompilationUnit cu, IProgressMonitor monitor) {
        if (proposal.getKind() != CompletionProposal.TYPE_REF) {
            return;
        }
        
        final String scope = ProjectHelper.isTestSource(cu) ? "T" : "S";
        final String signature = String.valueOf(proposal.getSignature());
        final String token = Optional.ofNullable(context.getToken()).map(t -> String.valueOf(t)).orElse("");
        TypeModel.getModel().findScore(signature, scope, token).ifPresent(ranking::setScore);
    }
    
}
